package de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.UDT;
import com.google.common.base.CaseFormat;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;

/**
 * This class can be used to create a rough schema for certain (Datastax) annotated POJOs
 * @author bjeutter
 *
 */
public class CassandraSchemaGenerator {
	public enum StructureType {
		TABLE,
		TYPE
	}
	
	private Map<Class<?>, String> classToUdtName = new HashMap<>();
	
	public String createTableQuery(Class<?> c) throws Exception {
		String requiredTypeQueries = createRequiredTypes(c);
		
		return requiredTypeQueries + "\n" + createStructure(StructureType.TABLE, c);
	}
	
	private String createRequiredTypes(Class<?> c) throws Exception {
		Field[] fields = c.getFields();
		
		StringBuilder stringBuilder = new StringBuilder();
		
		for(Field f : fields) {
			Class<?> type = f.getType();
			
			if(classToUdtName.containsKey(type)) {
				continue;
			}
			
			if(isCustomType(type)) {
				stringBuilder.append(createTypeQuery(type));
				stringBuilder.append("\n");
				
				classToUdtName.put(type, extractSchemaName(type));
			}
		}
		
		return stringBuilder.toString();
	}
	
	public String createTypeQuery(Class<?> c) throws Exception {
		return createStructure(StructureType.TYPE, c);
	}
	
	private String createStructure(StructureType structureType, Class<?> c) throws Exception {
		String schemaName = extractSchemaName(c);
		
		if(schemaName == null) {
			throw new Exception("No table name defined for type / table: " + c.getName());
		}
		
		Field[] fields = c.getFields();
		
		String structureName = structureType.name();
		
		StringBuilder stringBuilder = new StringBuilder("CREATE " + structureName + " ");
		stringBuilder.append(schemaName);
		stringBuilder.append(" (\n");
		
		int i = 0;
		for(Field f : fields) {
			// Prepare the field name
			Column nameAnnotation = f.getAnnotation(Column.class);
			String fieldName = getNameOfField(f);
			
			// Append field name
			stringBuilder.append("\t" + fieldName);
			
			// Append Field Type
			String fieldType = mapJavaToCassandraType(fieldName, f.getType());
			stringBuilder.append(" " + fieldType);
			
			// Append comma for next line if not last field
			if(i != fields.length - 1 || structureType.equals(StructureType.TABLE)) {
				stringBuilder.append(",\n");
			}
			
			i++;
		}
		
		// Append Primary Key description
		if(structureType.equals(StructureType.TABLE)) {
			stringBuilder.append("\t" + generatePrimaryKey(c));
		}
		
		// Close create statement
		stringBuilder.append("\n);");
		
		return stringBuilder.toString();
	}
	
	private static String getNameOfField(Field f) {
		Column nameAnnotation = f.getAnnotation(Column.class);
		String fieldName = null;
		
		if(nameAnnotation == null) {
			fieldName = f.getName();
		} else {
			fieldName = (nameAnnotation).name();
		}
		
		return fieldName;
	}
	
	private static String generatePrimaryKey(Class<?> c) throws Exception {
		Field[] fields = c.getFields();
		
		List<PartitionKey> partitionKeys = new LinkedList<>();
		
		Map<PartitionKey, String> partitionKeyToFieldName = new HashMap<>();
		
		List<String> clusterColumns = new LinkedList<>();
		
		for(Field f : fields) {
			PartitionKey partitionKey = f.getAnnotation(PartitionKey.class);
			ClusteringColumn clusterColumn = f.getAnnotation(ClusteringColumn.class);
			
			if(partitionKey != null) {
				partitionKeys.add(partitionKey);
				partitionKeyToFieldName.put(partitionKey, getNameOfField(f));
				//partitonKeys.
			}
			
			if(clusterColumn != null) {
				clusterColumns.add(getNameOfField(f));
			}
		}
		
		if(partitionKeys.size() == 0) {
			throw new Exception("Partition key annotations are required!");
		}
		
		String partitonKey = partitionKeys.stream()
		.sorted((p1, p2) -> Integer.compare(p1.value(), p2.value()))
		.map((p) -> partitionKeyToFieldName.get(p))
		.reduce((p1, p2) -> p1 + ", " + p2).get();

		boolean multiPartitionKey = partitionKeys.size() > 1;
		
		String result = "PRIMARY KEY (";
	
		if(multiPartitionKey) {
			result += "(";
		}
		
		result += partitonKey;
		
		if(multiPartitionKey) {
			result += ")";
		}
		
		if(clusterColumns != null) {
			result += ", ";
			result += clusterColumns.stream().reduce((c1, c2) -> c1 + ", " + c2).get();
		}
		
		result += ")";
		
		return result;
	}
	
	private static String extractSchemaName(Class<?> c) {
		Table tableAnnotation = c.getAnnotation(Table.class);
		UDT udtAnnotation = c.getAnnotation(UDT.class);
		
		if(tableAnnotation != null) {
			return tableAnnotation.name();
		} else if(udtAnnotation != null) {
			return udtAnnotation.name();
		}
		
		return null;
	}
	
	private String mapJavaToCassandraType(String fieldName, Class<?> type) {
		String simpleTypeName = type.getSimpleName();
		
		if(fieldName.equals("uuid")) {
			return "uuid";
		}
		
		switch(simpleTypeName) {
			case "String":
				return "text";
			case "long":
				// TODO: Just supports signed longs here, could be quite confusing!
				return "bigint";
			case "Long":
				return "bigint";
			case "Integer":
				return "int";
			case "List<String>":
				return "list<text>";
			case "Set<String>":
				return "set<text>";
			case "Date":
				return "timestamp";
		}
		
		if(classToUdtName.containsKey(type)) {
			return "frozen<" + classToUdtName.get(type) + ">";
		}
		
		return javaToSqlNotation(simpleTypeName);
	}
	
	private static boolean isCustomType(Class<?> type) {
		String typeName = type.getName();
		boolean isCustomType = typeName.indexOf(".") != -1 && typeName.indexOf("java.") == -1;
		
		return isCustomType;
	}
	
	private static String javaToSqlNotation(String input) {
		input = input.replace("UUID", "Uuid");
		return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input);
	}
	
	public static void main(String[] args) {
		try {
			CassandraSchemaGenerator schemaGenerator = new CassandraSchemaGenerator();
			
			Set<Class<? extends SensorData>> sensorDataClasses = new Reflections("de.tudarmstadt.informatik.tk.assistanceplatform.data").getSubTypesOf(SensorData.class);
			
			for(Class<? extends SensorData> c : sensorDataClasses) {
				System.out.println(schemaGenerator.createTableQuery(c));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
