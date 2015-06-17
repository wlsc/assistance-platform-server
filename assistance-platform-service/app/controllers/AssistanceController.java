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
						"http://xyz.de/lgo123.png",
						"Quantifies you in every way",
						"Really quantifies out of every angle",
						new String[] { "GPS" }, new String[] { "HUMIDITY" },
						"TK Department TU Darmstadt"),
				new AvailableAssistanceModule(
						"Hot Places",
						"de.tudarmstadt.informatik.tk.assistanceplatform.modules.hotplaces",
						"http://blabla.de/hotzone.png",
						"Finds the hottest places",
						"Finds the hottest places while you are moving",
						new String[] { "GPS" }, 
						null,
						"TK Department TU Darmstadt")
		};

		JsonNode json = Json.toJson(assiModules);
		return ok(json);
	}
}
