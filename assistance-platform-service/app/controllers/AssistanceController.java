package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import models.AvailableAssistanceModule;
import play.cache.Cached;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;

public class AssistanceController extends RestController {
	@Security.Authenticated(UserAuthenticator.class)
	@Cached(key = "moduleList")
	public Result list() {
		// Dummy data
		// TODO: Mit echten Daten ersetzen
		AvailableAssistanceModule[] assiModules = new AvailableAssistanceModule[] {
				new AvailableAssistanceModule(
						"Quantified self",
						"de.tudarmstadt.informatik.tk.assistanceplatform.modules.quantifiedself",
						new String[] { "GPS" }, new String[] { "HUMIDITY" }),
				new AvailableAssistanceModule(
						"Hot Places",
						"de.tudarmstadt.informatik.tk.assistanceplatform.modules.hotplaces",
						new String[] { "GPS" }, null) };

		JsonNode json = Json.toJson(assiModules);
		return ok(json);
	}
}
