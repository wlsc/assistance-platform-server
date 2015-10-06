package de.tudarmstadt.informatik.tk.assistanceplatform.data.typemapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;

public class NameToTypeMapper {
	private Map<String, Class> typeMapping = new HashMap<>();
	
	public NameToTypeMapper() {
		this("de.tudarmstadt.informatik.tk.assistanceplatform.data");
	}
	
	public NameToTypeMapper(String packUri) {
		setupTypeMapping(packUri);
	}
	
	
	private void setupTypeMapping(String packUri) {
		Set<Class<? extends SensorData>> sensorDataClasses = new Reflections(packUri).getSubTypesOf(SensorData.class);
		
		for(Class<? extends SensorData> c : sensorDataClasses) {
			typeMapping.put(getNameForClass(c), c);
		}
	}
	
	private String getNameForClass(Class<? extends SensorData> c) {
		TypeNameForAssistance annotation = c.getAnnotation(TypeNameForAssistance.class);
		
		if(annotation != null) {
			return annotation.name();
		}
		
		String simpleName = c.getSimpleName();
		simpleName = simpleName.toLowerCase();
		
		return simpleName;
	}
	
	public Class mapTypeToClass(String type) {
		return typeMapping.get(type);
	}
}
