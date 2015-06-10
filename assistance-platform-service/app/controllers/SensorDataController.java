package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import token.TokenDeserializerImpl;

public class SensorDataController extends Controller {
	public Result insert() {
		// TODO: Hier muss man sich nun überlegen, wie man das alles generisch verarbeitet - bei KrakenMe reinschauen
		return ok(new TokenDeserializerImpl("supersicher").deserialize(request().body().asJson().findPath("token").asText()));
		
		//return TODO;
	}
}