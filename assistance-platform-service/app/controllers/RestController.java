package controllers;

import java.util.Map;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

import errors.APIError;

public abstract class RestController extends Controller {
	protected Long getUserIdForRequest() {
		UserAuthenticator authenticator = new UserAuthenticator();
		return authenticator.getUserId(ctx());
	}
	
	public Result ok(Map<String, Object> map) {
		return ok(mappedJson(map));
	}
	
	public static JsonNode mappedJson(Map<String, Object> map) {
		return Json.toJson(map);
	}
	
	public Result badRequestJson(APIError error) {
		return badRequest(errorInJson(error));
	}
	
	public Result internalServerErrorJson(APIError error) {
		return internalServerError(errorInJson(error));
	}
	
	public static JsonNode errorInJson(APIError error) {
		return Json.toJson(error);
	}
}