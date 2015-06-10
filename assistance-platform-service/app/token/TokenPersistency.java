package token;

import models.User;

public interface TokenPersistency {
	public void createTokenForUser(User user);
	
	public boolean tokenIsValid(String token);
	
	public Long userIdForToken(String token);
	
	public User userForToken(String token);
}