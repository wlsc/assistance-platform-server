package models;

import token.TokenSerializer;
import token.TokenSerializerImpl;

public class Token {
	public Long associatedId;
	
	public String token;
	
	public Token(String token, Long associatedId) {
		this.token = token;
		this.associatedId = associatedId;
	}
	
	public static Token buildToken(Long id) {
		TokenSerializer gen = new TokenSerializerImpl("supersicher");
		return new Token(gen.sign(id.toString()), id);
	}
}