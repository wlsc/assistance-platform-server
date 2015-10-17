package sensorhandling;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.typemapping.NameToTypeMapper;

/**
 * This class is responsible for converting a sensor reading in JSON representation to the respective Java Sensor Event.
 */
public class JsonToSensorEventConversion {
	private NameToTypeMapper typeMapper = new NameToTypeMapper();
	
	private ObjectMapper mapper;
	
	public JsonToSensorEventConversion() {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public <T extends SensorData> T convertJsonToEventObject(String type, JsonNode reading) throws JsonProcessingException {
		Class<T> targetClass = mapTypeToClass(type);
		
		if(targetClass != null) {
			return mapJson(reading, targetClass);
		}
		
		return null;
	}
	
	public <T extends SensorData> T mapJson(JsonNode reading, Class<T> targetClass) throws JsonProcessingException, DateTimeParseException {
		T mappedObject = mapper.treeToValue(reading, targetClass);
		
		JsonNode createdNode = reading.get("created");
		if(createdNode == null) {
			return null;
		}
		
		try {
			OffsetDateTime offsetDateTime = OffsetDateTime.parse(createdNode.asText());
			
			mappedObject.timestamp = Date.from(offsetDateTime.toInstant());
			
			return mappedObject;
		} catch(DateTimeParseException ex) {
			throw(ex);
		}
	}

	
	public <T extends SensorData> Class<T> mapTypeToClass(String type) {
		return typeMapper.mapTypeToClass(type);
	}
}