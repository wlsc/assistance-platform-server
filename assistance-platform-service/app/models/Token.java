package models;

import java.util.HashMap;
import java.util.Map;

import play.Logger;
import play.api.Configuration;
import play.api.Play;
import play.libs.Json;
import token.TokenDeserializer;
import token.TokenDeserializerImpl;
import token.TokenSerializer;
import token.TokenSerializerImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.ConfigFactory;

public class Token {
	private static final String SECRET() {
		return ConfigFactory.defaultApplication().getString("jwttoken.secret");
	}
	
	public String associatedId;
	
	public String token;
	
	public Long expirationTimestamp;
	
	public Token(String token, String associatedId, Long timestamp) {
		this.token = token;
		this.associatedId = associatedId;
		this.expirationTimestamp = timestamp;
	}
	
	public Token(String token, Long associatedId, Long timestamp) {
		this(token, associatedId.toString(), timestamp);
	}
	
	public static Token buildToken(Long id, int validityInHours) {
		TokenSerializer gen = new TokenSerializerImpl(SECRET());
		
		long timestamp = System.currentTimeMillis() + validityInHours * 60 * 60 * 1000;
		
		Map<String, Object> tokenData = new HashMap<>();
		tokenData.put("id", id);
		tokenData.put("expirationTimestamp", timestamp);
		
		JsonNode json = Json.toJson(tokenData);
		String jsonString = Json.stringify(json);
		
		return new Token(gen.sign(jsonString), id, timestamp);
	}
	
	public static Token unpackToken(String token) {
		TokenDeserializer deserializer = new TokenDeserializerImpl(SECRET());
		
		String payload = deserializer.deserialize(token);
		
		if(payload != null) {
			JsonNode json = Json.parse(payload);
		
			String id = json.findPath("id").asText();
			Long timestamp = json.findPath("expirationTimestamp").asLong();
			
			return new Token(token, id, timestamp);
		}
		
		return null;
	}
	
	public boolean stillValid() {
		return System.currentTimeMillis() < expirationTimestamp;
	}
}