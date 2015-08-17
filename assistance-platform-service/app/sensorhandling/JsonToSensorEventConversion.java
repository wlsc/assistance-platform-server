package sensorhandling;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Accelerometer;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.ConnectionStatus;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Gyroscope;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Loudness;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.MagneticField;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.MobileDataConnection;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.MotionActivity;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Position;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.WifiConnection;

public class JsonToSensorEventConversion {
	public <T extends SensorData> T convertJsonToEventObject(String type, JsonNode reading) throws JsonProcessingException {
		Class<T> targetClass = mapTypeToClass(type);
		
		if(targetClass != null) {
			return mapJson(reading, targetClass);
		}
		
		return null;
	}
	
	public <T extends SensorData> T mapJson(JsonNode reading, Class<T> targetClass) throws JsonProcessingException, DateTimeParseException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		T mappedObject = mapper.treeToValue(reading, targetClass);
		
		JsonNode createdNode = reading.get("created");
		if(createdNode == null) {
			return null;
		}
		
		try {
			Instant parsedInstance = Instant.parse(createdNode.asText());
			
			mappedObject.timestamp = parsedInstance.getEpochSecond();
			
			return mappedObject;
		} catch(DateTimeParseException ex) {
			throw(ex);
		}
	}

	
	public static Class mapTypeToClass(String type) {
		Map<String, Class> typeMapping = new HashMap<>();
		typeMapping.put("position", Position.class);
		typeMapping.put("gyroscope", Gyroscope.class);
		typeMapping.put("accelerometer", Accelerometer.class);
		typeMapping.put("magneticfield", MagneticField.class);
		typeMapping.put("motionactivity", MotionActivity.class);
		typeMapping.put("connection", ConnectionStatus.class);
		typeMapping.put("wificonnection", WifiConnection.class);
		typeMapping.put("mobileconnection", MobileDataConnection.class);
		typeMapping.put("loudness", Loudness.class);
		
		return typeMapping.get(type);
	}
}