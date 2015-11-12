package controllers;

import persistency.ActiveAssistanceModulePersistency;
import persistency.cassandra.config.CassandraConfig;
import persistency.cassandra.usercreation.CassandraModuleUser;
import persistency.cassandra.usercreation.CassandraModuleUserCreator;
import play.libs.Json;
import play.mvc.Result;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.CassandraServiceConfigResponse;
import errors.AssistanceAPIErrors;

public class ModuleServicesController extends RestController {
	private CassandraModuleUserCreator userCreator = new CassandraModuleUserCreator();
	
	public Result database(String moduleId) {
		if (!ActiveAssistanceModulePersistency.doesModuleWithIdExist(moduleId)) {
			return badRequestJson(AssistanceAPIErrors.moduleDoesNotExist);
		}

		CassandraModuleUser user = userCreator.createUserForModule(moduleId);
		
		CassandraServiceConfigResponse response = new CassandraServiceConfigResponse(
				user.user, user.password,
				CassandraConfig.getContactPointsArray(),
				user.keyspace
				);

		return ok(Json.toJson(response));
	}
}