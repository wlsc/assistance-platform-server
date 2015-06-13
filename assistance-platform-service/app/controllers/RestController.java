package controllers;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public abstract class RestController extends Controller {
	public Result ok(Map<String, Object> map) {
		return ok(mappedJson(map));
	}
	
	public Result badRequestJson(String error) {
		return badRequest(errorInJson(error));
	}
	
	public Result internalServerErrorJson(String error) {
		return internalServerError(errorInJson(error));
	}
	
	
	private JsonNode errorInJson(String message) {
		Map<String, Object> map = new HashMap<>();
		map.put("error", message);
		return mappedJson(map);
	}
	
	private JsonNode mappedJson(Map<String, Object> map) {
		return Json.toJson(map);
	}
}