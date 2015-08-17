package sensorhandling;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Position;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Accelerometer;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.ConnectionStatus;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Gyroscope;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Loudness;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.MagneticField;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.MobileDataConnection;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.MotionActivity;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.WifiConnection;

public class JsonToSensorEventConversion {
	public <T> T convertJsonToEventObject(String type, JsonNode reading) throws JsonProcessingException {
		Class<T> targetClass = mapTypeToClass(type);
		
		if(targetClass != null) {
			return mapJson(reading, targetClass);
		}
		
		return null;
	}
	
	public <T> T mapJson(JsonNode reading, Class<T> targetClass) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		T mappedObject = mapper.treeToValue(reading, targetClass);
		
		
		JsonNode createdNode = reading.get("created");
		if(createdNode == null) {
			
		}
		
		return mappedObject;
		
		// TODO: Convert "created" to "timestamp"
		
		//distributeSensorReading(mappedObject, targetClass);
	}
	
	/**
	 * Extracts the timestamp by converting the iso 8601 
	 * @param reading
	 * @return
	 */
	private long extractTimestamp(JsonNode reading) {
		return -1;
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