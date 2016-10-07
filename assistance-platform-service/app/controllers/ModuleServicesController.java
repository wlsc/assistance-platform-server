package controllers;

import com.typesafe.config.ConfigFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.CassandraServiceConfigResponse;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.ServiceConfigResponse;
import errors.AssistanceAPIErrors;
import messaging.JmsMessagingServiceConfig;
import persistency.ActiveAssistanceModulePersistency;
import persistency.cassandra.config.CassandraConfig;
import persistency.cassandra.usercreation.CassandraModuleUser;
import persistency.cassandra.usercreation.CassandraModuleUserCreator;
import play.libs.Json;
import play.mvc.Result;
import sensorhandling.tucan.TucanSecurityConfig;

public class ModuleServicesController extends RestController {
    private CassandraModuleUserCreator userCreator = new CassandraModuleUserCreator();

    public Result database(String moduleId) {
        if (!ActiveAssistanceModulePersistency.doesModuleWithIdExist(moduleId)) {
            return badRequestJson(AssistanceAPIErrors.moduleDoesNotExist);
        }

        CassandraModuleUser user = userCreator.createUserForModule(moduleId);

        CassandraServiceConfigResponse response = new CassandraServiceConfigResponse(
                user.user, user.password,
                CassandraConfig.getContactPointsArray(), user.keyspace);

        return ok(Json.toJson(response));
    }

    public Result spark(String moduleId) {
        if (!ActiveAssistanceModulePersistency.doesModuleWithIdExist(moduleId)) {
            return badRequestJson(AssistanceAPIErrors.moduleDoesNotExist);
        }

        ServiceConfigResponse response = new ServiceConfigResponse(null, null,
                new String[]{getSparkMaster()});

        return ok(Json.toJson(response));
    }

    private String getSparkMaster() {
        return ConfigFactory.defaultApplication().getString("spark.master");
    }

    public Result activemq(String moduleId) {
        ServiceConfigResponse response = new ServiceConfigResponse(
                JmsMessagingServiceConfig.getUser(),
                JmsMessagingServiceConfig.getPassword(),
                new String[]{JmsMessagingServiceConfig.getBroker()});

        return ok(Json.toJson(response));
    }

    public Result tucan(String moduleId) {
        if (!ActiveAssistanceModulePersistency.doesModuleWithIdExist(moduleId)) {
            return badRequestJson(AssistanceAPIErrors.moduleDoesNotExist);
        }

        // Prüfen ob Modul erlaubt ist diesen Endpoint aufzurufen
        // TODO: Refactor in Datenbank, sodass ohne Config File Module
        // hinzugefügt werden und geprüft werden können
        if (!TucanSecurityConfig.getAllowedModules().contains(moduleId)) {
            return badRequestJson(AssistanceAPIErrors.badAuthenciationData);
        }

        // Prüfen, ob das Modul das Passwort für den Endpoint bereitgestellt hat
        String[] enteredEndpointPw = request().headers()
                .get("X-SECURITY-TOKEN");
        if (enteredEndpointPw == null
                || enteredEndpointPw.length != 1
                || !TucanSecurityConfig.getEndpointPassword().equals(
                enteredEndpointPw[0])) {
            return badRequestJson(AssistanceAPIErrors.badAuthenciationData);
        }

        ServiceConfigResponse response = new ServiceConfigResponse(null, TucanSecurityConfig.getTucanSecret(), null);

        return ok(Json.toJson(response));
    }
}