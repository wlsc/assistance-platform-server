package controllers;

import play.mvc.Result;
import play.mvc.Security;

public class SensorDataController extends RestController {
	@Security.Authenticated(UserAuthenticator.class)
	public Result insert() {
		// TODO: Hier muss man sich nun überlegen, wie man das alles generisch verarbeitet - bei KrakenMe reinschauen
		//return ok(new TokenDeserializerImpl("supersicher").deserialize(request().body().asJson().findPath("token").asText()));
		
		return TODO;
	}
}