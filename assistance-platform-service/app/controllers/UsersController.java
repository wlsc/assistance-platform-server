package controllers;

import java.util.HashMap;
import java.util.Map;

import models.AssistanceAPIErrors;
import models.Token;
import models.User;
import persistency.UserPersistency;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public class UsersController extends RestController {
	public Result login() {
		return performActionIfEmailAndPasswordAvailable(this::tryToAuthUser);
	}
	
	private Result tryToAuthUser(String mail, String password) {
		if(!User.authenticate(mail, password)) {
			return badRequestJson(AssistanceAPIErrors.badAuthenciationData);
		}
		
		User u = UserPersistency.findUserByEmail(mail);
		
		String token = Token.buildToken(u.id, 24).token;
		
		Map<String, Object> result = new HashMap<>();
		result.put("token", token);

		return ok(result);
	}
	
	public Result register() {
		return performActionIfEmailAndPasswordAvailable(this::tryToRegisterUser);
	}
	
	private Result tryToRegisterUser(String mail, String password) {
		// TODO: Check for length of password
		// TODO: Check for correctnes of email
		
		if(UserPersistency.doesUserWithEmailExist(mail)) {
			return badRequestJson(AssistanceAPIErrors.userAlreadyExists);
		}
		
		User newUser = new User(mail);
		UserPersistency.createAndUpdateIdOnSuccess(newUser, password);
		
		if(newUser.id != 0) {
			Map<String, Object> result = new HashMap<>();
			result.put("user_id", newUser.id);

			return ok(result);
		}
		
		return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
	}
	
	private Result performActionIfEmailAndPasswordAvailable(TwoArgFunction<String, String, Result> action) {
		JsonNode postData = request().body()
				.asJson();
		
		if(areAllRequiredParametersPosted(postData)) {
			String mail = getEmailNode(postData).textValue();
			String password = getPasswordNode(postData).textValue();
			
			return action.apply(mail, password);
		} else {
			return badRequestJson(AssistanceAPIErrors.missingUserParameters);
		}
	}
	
	public interface TwoArgFunction<A, B, C> {
		C apply(A a, B b);
	}
	
	private boolean areAllRequiredParametersPosted(JsonNode postData) {
		return postData != null && getEmailNode(postData) != null && getPasswordNode(postData) != null;
	}

	
	private JsonNode getEmailNode(JsonNode postData) {
		return postData.findPath("email");
	}
	
	private JsonNode getPasswordNode(JsonNode postData) {
		return postData.findPath("password");
	}
}