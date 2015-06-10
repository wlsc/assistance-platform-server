package token;

import java.util.HashMap;
import java.util.Map;

import models.User;

public class TokenPersistencyLocal implements TokenPersistency {
	private TokenCache tokens = new TokenCache();
	
	@Override
	public void createTokenForUser(User user) {
		
	}

	@Override
	public boolean tokenIsValid(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long userIdForToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User userForToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	class TokenCache {
		private Map<Long, String> userToToken = new HashMap<>();
		private Map<String, Long> tokensToUser = new HashMap<>();
		
		public void add(Long userId, String token) {
			userToToken.put(userId, token);
			tokensToUser.put(token, userId);
		}
		
		public Long getIdByToken(String token) {
			return tokensToUser.get(token);
		}
		
		public String getTokenById(Long id) {
			return userToToken.get(id);
		}
	}
}
