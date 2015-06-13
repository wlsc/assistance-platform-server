package models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import token.TokenDeserializer;
import token.TokenDeserializerImpl;
import token.TokenSerializer;
import token.TokenSerializerImpl;

public class Token {
	public String associatedId;
	
	public String token;
	
	public Long generationTimestamp;
	
	public Token(String token, String associatedId, Long timestamp) {
		this.token = token;
		this.associatedId = associatedId;
		this.generationTimestamp = timestamp;
	}
	
	public Token(String token, Long associatedId, Long timestamp) {
		this(token, associatedId.toString(), timestamp);
	}
	
	public static Token buildToken(Long id) {
		TokenSerializer gen = new TokenSerializerImpl("supersicher");
		
		long timestamp = System.currentTimeMillis();
		
		Map<String, Object> tokenData = new HashMap<>();
		tokenData.put("id", id);
		tokenData.put("generationTimestamp", timestamp);
		
		JsonNode json = Json.toJson(tokenData);
		String jsonString = Json.stringify(json);
		
		return new Token(gen.sign(jsonString), id, timestamp);
	}
	
	public static Token unpackToken(String token) {
		TokenDeserializer deserializer = new TokenDeserializerImpl("supersicher");
		
		String payload = deserializer.deserialize(token);
		
		JsonNode json = Json.parse(payload);
		
		String id = json.findPath("id").asText();
		Long generationTimestamp = json.findPath("generationTimestamp").asLong();
		
		return new Token(token, id, generationTimestamp);
	}
}