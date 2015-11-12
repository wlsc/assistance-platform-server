package controllers;

import models.Token;
import models.User;
import persistency.UserPersistency;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import errors.APIError;

public class UserAuthenticator extends Security.Authenticator {
	@Override
	public String getUsername(Http.Context ctx) {
		User user = getUser(ctx);

		if (user != null) {
			return user.email;
		}

		return null;
	}

	public long getUserId(Http.Context ctx) {
		String token = getTokenFromHeader(ctx);
		
		return getUserIdFromToken(token);
	}
	
	public static long getUserIdFromToken(String token) {
		if (token != null) {
			Token unpackedToken = Token.unpackToken(token);

			if (unpackedToken != null && unpackedToken.stillValid()) {
				return Long.parseLong(unpackedToken.associatedId);
			}
		}

		return -1;
	}

	private User getUser(Http.Context ctx) {
		long userId = getUserId(ctx);

		if (userId != -1) {
			User user = UserPersistency.findUserById(userId, false);

			if (user != null) {
				return user;
			}
		}
		
		return null;
	}

	@Override
	public Result onUnauthorized(Http.Context context) {
		return unauthorized(RestController.errorInJson(new APIError(1, "Unauthorized")));
	}

	private String getTokenFromHeader(Http.Context ctx) {
		String[] authTokenHeaderValues = ctx.request().headers()
				.get("X-AUTH-TOKEN");
		if ((authTokenHeaderValues != null)
				&& (authTokenHeaderValues.length == 1)
				&& (authTokenHeaderValues[0] != null)) {
			return authTokenHeaderValues[0];
		}
		return null;
	}
}