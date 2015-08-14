package controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import models.AssistanceAPIErrors;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.WebSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.Position;
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
		JsonNode sensorreadings = json.get("sensorreadings");
		
		if(sensorreadings != null && sensorreadings.isArray()) {
			Iterator<JsonNode> elementIterator = sensorreadings.elements();
			
			while(elementIterator.hasNext()) {
				JsonNode sensorReading = elementIterator.next();
				
				String type = sensorReading.path("type").asText();
				
				try {
					processSensorReading(type, sensorReading);
				} catch (JsonProcessingException e) {
					Logger.error("Error processing json", e);
				}
			}
		}
	}
	
	private void processSensorReading(String type, JsonNode reading) throws JsonProcessingException {
		Class targetClass = null;
		
		switch(type) {
			case "position": {
				targetClass = Position.class;
				break;
			}
			// TODO: weitere Typen einbinden
		}
		
		if(targetClass != null) {
			mapJsonAndDistribute(reading, targetClass);
		}
	}
	
	private <T> void mapJsonAndDistribute(JsonNode reading, Class<T> targetClass) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		T mappedObject = mapper.treeToValue(reading, targetClass);
		
		distributeSensorReading(mappedObject, targetClass);
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