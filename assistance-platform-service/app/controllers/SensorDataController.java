package controllers;

import java.time.format.DateTimeParseException;
import java.util.Iterator;

import models.AssistanceAPIErrors;
import persistency.DevicePersistency;
import play.Logger;
import play.libs.Json;
import play.mvc.WebSocket;
import sensorhandling.JsonToSensorEventConversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

public class SensorDataController extends RestController {
	private MessagingService ms = new JmsMessagingService();
	
	public WebSocket<JsonNode> socket() {
		return WebSocket.whenReady((in, out) -> {
	        // For each event received on the socket,
	        in.onMessage((j) -> handleSocketFrame(j, out));

	        // When the socket is closed.
	        in.onClose(() -> Logger.info("Client disconnected from websocket"));
	    });
	}
	
	private void handleSocketFrame(JsonNode json, WebSocket.Out<JsonNode> out) {
		String token = extractToken(json);
		
		Long userId = UserAuthenticator.getUserIdFromToken(token);
		
		if(userId == null) {
			handleInvalidToken(out);
			return;
		}
		
		handleData(json, out);
	}
	
	private void handleData(JsonNode json, WebSocket.Out<JsonNode> out) {
		long deviceID = processDeviceID(json, out);
		
		if(deviceID == -1) {
			return;
		}
		
		JsonNode sensorreadings = json.get("sensorreadings");
		
		if(sensorreadings != null && sensorreadings.isArray()) {
			Iterator<JsonNode> elementIterator = sensorreadings.elements();
			
			while(elementIterator.hasNext()) {
				JsonNode sensorReading = elementIterator.next();
				
				String type = sensorReading.path("type").asText();
				
				try {
					processSensorReading(type, deviceID, sensorReading);
				} catch (JsonProcessingException e) {
					Logger.warn("Error processing json", e);
					out.write(Json.toJson(AssistanceAPIErrors.invalidParametersGeneral));
					out.close();
				} catch(DateTimeParseException e) {
					Logger.warn("Failure on processing created timestamp", e);
					out.write(Json.toJson(AssistanceAPIErrors.invalidParametersGeneral));
					out.close();
				}
			}
		}
	}
	
	private long processDeviceID(JsonNode json, WebSocket.Out<JsonNode> out) {
		JsonNode deviceIdNode = json.get("device_id");
		long deviceID = -1;
		
		if(deviceIdNode == null) {
			out.write(Json.toJson(AssistanceAPIErrors.missingParametersGeneral));
			out.close();
		} else {
			deviceID = deviceIdNode.asLong();
			
			if(!DevicePersistency.doesExist(deviceID)) {
				out.write(Json.toJson(AssistanceAPIErrors.deviceIdNotKnown));
				out.close();
			}
		}
		
		return deviceID;
	}
	
	private <T extends SensorData> void processSensorReading(String type, long deviceID, JsonNode reading) throws JsonProcessingException, DateTimeParseException {
		JsonToSensorEventConversion sensorConversion = new JsonToSensorEventConversion();
		
		Class<T> classType = JsonToSensorEventConversion.mapTypeToClass(type);
		
		try {
			T eventObject = sensorConversion.mapJson(reading, classType);
		
			setDeviceIdForSensorReading(deviceID, eventObject);
		
			distributeSensorReading(eventObject, classType);
		} catch(DateTimeParseException e) {
			throw(e);
		}
	}
	
	private <T extends SensorData> void setDeviceIdForSensorReading(long deviceID, T data) {
		data.deviceId = deviceID;
	}
	
	private <T> void distributeSensorReading(T reading, Class<T> targetClass) {
		ms.channel(targetClass).publish(reading);
	}
	
	private String extractToken(JsonNode json) {
		JsonNode tokenNode = json.get("token");
		
		if(tokenNode == null) {
			return null;
		}
		
		return tokenNode.asText();
	}
	
	private void handleInvalidToken(WebSocket.Out<JsonNode> out) {
		JsonNode result = Json.toJson(AssistanceAPIErrors.invalidOrExpiredToken);
		
		out.write(result);
		out.close();
	}
	

}