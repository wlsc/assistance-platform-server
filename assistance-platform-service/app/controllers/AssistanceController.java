package controllers;

import java.util.function.Predicate;

import messaging.JmsMessagingServiceFactory;
import models.ActiveAssistanceModule;
import models.UserModuleActivation;
import persistency.ActiveAssistanceModulePersistency;
import persistency.UserModuleActivationPersistency;
import play.cache.Cache;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;

import com.fasterxml.jackson.databind.JsonNode;

import de.tudarmstadt.informatik.tk.assistanceplatform.information.CurrentModuleInformationAggregator;
import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;
import errors.AssistanceAPIErrors;

public class AssistanceController extends RestController {
	MessagingService ms = JmsMessagingServiceFactory.createServiceFromConfig();

	@Security.Authenticated(UserAuthenticator.class)
	public Result list(
			String language) {

		JsonNode result = Cache.getOrElse("moduleList"+language, () -> {
			ActiveAssistanceModule[] assiModules = ActiveAssistanceModulePersistency.list(language);

			JsonNode json = Json.toJson(assiModules);
			return json;
		}, 3600);

		return ok(result);
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result activations() {
		Long userId = getUserIdForRequest();

		String[] result = UserModuleActivationPersistency.activatedModuleIdsForUser(userId);

		return ok(Json.toJson(result));
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result activate() {
		return handleActivationStatusChange((a) -> {
			return UserModuleActivationPersistency.create(a);
		}, true);
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result deactivate() {
		return handleActivationStatusChange((a) -> {
			return UserModuleActivationPersistency.remove(a);
		}, false);
	}

	private Result handleActivationStatusChange(Predicate<UserModuleActivation> activationCheck, boolean endResultOfRegistrationStatus) {
		JsonNode postData = request().body()
				.asJson();

		// Get Module ID to activate from request
		JsonNode moduleIdNode = postData.findPath("module_id");
		if(moduleIdNode.isMissingNode()) {
			return badRequestJson(AssistanceAPIErrors.missingModuleIDParameter);
		}

		String moduleId = moduleIdNode.textValue();

		// Get User id from request
		long userId = getUserIdForRequest();

		UserModuleActivation activation = new UserModuleActivation(userId, moduleId);

		if(activationCheck.test(activation)) {
			publishUserRegistrationInformationEvent(userId, moduleId, endResultOfRegistrationStatus);

			return ok(); // TODO: Ggf. noch mal mit der Module ID bestätigen oder sogar die Liste aller aktivierten Module (IDs) zurückgeben?
		} else {
			if(UserModuleActivationPersistency.doesActivationExist(activation) == endResultOfRegistrationStatus) {
				return badRequestJson(AssistanceAPIErrors.moduleActivationNotActive);
			} else {
				return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
			}
		}
	}

	private void publishUserRegistrationInformationEvent(long userId, String moduleId, boolean wantsToBeRegistered) {
		ms.channel(UserRegistrationInformationEvent.class)
		.publish(new UserRegistrationInformationEvent(userId, moduleId, wantsToBeRegistered));
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result current() {
		// Get User id from request
		long userId = getUserIdForRequest();
		
		CurrentModuleInformationAggregator informationAggregator = new CurrentModuleInformationAggregator(userId);
		
		// 1. Suche alle aktivierten MOdule von dem User
		
		// 2. Schleife den Request an alle gefundenen Module (bzw. deren REST Server) weiter
		// 3. Sammle die Resultate
		// 4. Priorisiere die Resultate

		return TODO;
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result currentForModule(String moduleId) {
		// TODO: Frage das Module mit der ID {moduleId} nach seinen aktuellen Informationen

		return TODO;
	}
}
