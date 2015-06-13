package controllers;

import models.Token;
import models.User;
import persistency.UserPersistency;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class UserAuthenticator extends Security.Authenticator {  
    @Override
    public String getUsername(Http.Context ctx) {
        String token = getTokenFromHeader(ctx);
        
        if (token != null) {
        	Token unpackedToken = Token.unpackToken(token);
        	
        	if(unpackedToken != null && unpackedToken.stillValid()) {
	        	User user = UserPersistency.findUserById(Long.valueOf(unpackedToken.associatedId));
	        	
	            if (user != null) {
	                return user.email;
	            }
        	}
        }
        return null;
    }
 
    @Override
    public Result onUnauthorized(Http.Context context) {
        return super.onUnauthorized(context);
    }
 
    private String getTokenFromHeader(Http.Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get("X-AUTH-TOKEN");
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            return authTokenHeaderValues[0];
        }
        return null;
    }
}