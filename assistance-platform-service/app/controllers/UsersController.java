package controllers;

import java.util.HashMap;
import java.util.Map;

import models.AssistanceAPIErrors;
import models.Token;
import models.User;
import persistency.UserPersistency;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import utility.DateTimeHelper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UsersController extends RestController {
	public Result login() {
		return performActionIfEmailAndPasswordAvailable(this::tryToAuthUser);
	}
	
	private Result tryToAuthUser(String mail, String password) {
		if(!User.authenticate(mail, password)) {
			return badRequestJson(AssistanceAPIErrors.badAuthenciationData);
		}
		
		User u = UserPersistency.findUserByEmail(mail, false);
		UserPersistency.updateLastLogin(u.id);
		
		String token = Token.buildToken(u.id, 24).token;
		
		Map<String, Object> result = new HashMap<>();
		result.put("token", token);

		return ok(result);
	}
	
	public Result register() {
		UserAuthenticator authenticator = new UserAuthenticator();
		if(authenticator.getUserId(ctx()) != null) {
			return badRequestJson(AssistanceAPIErrors.alreadyLoggedIn);
		}
		
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
		return postData != null && postData.has("email") && postData.has("password");
	}

	
	private JsonNode getEmailNode(JsonNode postData) {
		return postData.findPath("email");
	}
	
	private JsonNode getPasswordNode(JsonNode postData) {
		return postData.findPath("password");
	}
	
	@Security.Authenticated(UserAuthenticator.class)
	public Result myProfile(String type) {
		Long id = getUserIdForRequest();
		
		String requestedType = type;

		boolean putServices = false;
		switch(requestedType) {
		case "short":
			 putServices = false;
			break;
		case "long":
			 putServices = true;
			break;
		default:
			return badRequestJson(AssistanceAPIErrors.invalidParametersGeneral);
		}
		
		User profile = UserPersistency.findUserById(id, true);
		
		if(profile == null) {
			return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
		}
		
		JsonNode result = Json.toJson(profile);
		ObjectNode modifiableResult = (ObjectNode)result;
		modifiableResult.put("joinedSince", DateTimeHelper.localDateTimeToTimestamp(profile.joinedSince));
		modifiableResult.put("lastLogin", DateTimeHelper.localDateTimeToTimestamp(profile.lastLogin));
		
		if(putServices) {
			modifiableResult.putArray("services");
		}
		
		return ok(result);
	}
	
	@Security.Authenticated(UserAuthenticator.class)
	public Result updateProfile() {
		Long id = getUserIdForRequest();
		
		JsonNode postData = request().body()
				.asJson();
		
		if(!postData.has("firstname") && !postData.has("lastname")) {
			return badRequestJson(AssistanceAPIErrors.missingParametersGeneral);
		}
		
		JsonNode firstnameNode = postData.findPath("firstname");
		JsonNode lastnameNode = postData.findPath("lastname");
		
		if(!firstnameNode.isTextual() && !lastnameNode.isTextual()) {
			return badRequestJson(AssistanceAPIErrors.invalidParametersGeneral);
		}
		
		User u = UserPersistency.findUserById(id, false);
		
		if(u == null) {
			return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
		}
		
		u.firstName = firstnameNode.asText();
		u.lastName = lastnameNode.asText();
		
		UserPersistency.updateProfile(u);
		
		return ok();
	}
}