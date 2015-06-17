package controllers;

import java.util.Map;

import models.APIError;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class RestController extends Controller {
	public Result ok(Map<String, Object> map) {
		return ok(mappedJson(map));
	}
	
	private JsonNode mappedJson(Map<String, Object> map) {
		return Json.toJson(map);
	}
	
	public Result badRequestJson(APIError error) {
		return badRequest(errorInJson(error));
	}
	
	public Result internalServerErrorJson(APIError error) {
		return internalServerError(errorInJson(error));
	}
	
	
	private JsonNode errorInJson(APIError error) {
		return Json.toJson(error);
	}
}