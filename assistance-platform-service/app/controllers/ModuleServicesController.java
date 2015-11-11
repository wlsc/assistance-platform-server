package controllers;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.ServiceConfigResponse;
import errors.AssistanceAPIErrors;
import persistency.ActiveAssistanceModulePersistency;
import persistency.config.CassandraConfig;
import play.libs.Json;
import play.mvc.Result;

public class ModuleServicesController extends RestController {
	public Result database(String moduleId) {
		if (!ActiveAssistanceModulePersistency.doesModuleWithIdExist(moduleId)) {
			return badRequestJson(AssistanceAPIErrors.moduleDoesNotExist);
		}

		ServiceConfigResponse response = new ServiceConfigResponse(
				CassandraConfig.getUser(), CassandraConfig.getPassword(),
				CassandraConfig.getContactPointsArray());
		
		// TODO: Generate a passowrd for the new user
		// TODO: Create User in Cassandra
		// TODO: Grante right permissions to the newly created user
		// TODO: Return user / password pair

		return ok(Json.toJson(response));
	}
}