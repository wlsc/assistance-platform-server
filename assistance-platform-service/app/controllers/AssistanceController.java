package controllers;

import java.util.function.Predicate;

import models.ActiveAssistanceModule;
import models.AssistanceAPIErrors;
import models.UserModuleActivation;
import persistency.ActiveAssistanceModulePersistency;
import persistency.UserModuleActivationPersistency;
import play.cache.Cache;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;

import com.fasterxml.jackson.databind.JsonNode;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

public class AssistanceController extends RestController {
	MessagingService ms = new JmsMessagingService();
	
	@Security.Authenticated(UserAuthenticator.class)
	public Result list(String language) {
	
		JsonNode result = Cache.getOrElse("moduleList"+language, () -> {
			ActiveAssistanceModule[] assiModules = ActiveAssistanceModulePersistency.list(language);
			
			JsonNode json = Json.toJson(assiModules);
			return json;
		}, 3600);
		
		return ok(result);
	}
	
	@Security.Authenticated(UserAuthenticator.class)
	public Result activate() {
		return handleActivationStatusChange((a) -> {
			return UserModuleActivationPersistency.create(a);
		}, false);
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
		Long userId = getUserIdForRequest();
		
		UserModuleActivation activation = new UserModuleActivation(userId, moduleId);
		
		if(activationCheck.test(activation)) {
			// TODO: Event in Event Stream pushen, sodass MOdule Aktivierung mitbekommen
			publishUserRegistrationInformationEvent(userId, endResultOfRegistrationStatus);
			
			return ok(); // TODO: Ggf. noch mal mit der Module ID bestätigen oder sogar die Liste aller aktivierten Module (IDs) zurückgeben?
		} else {
			if(UserModuleActivationPersistency.doesActivationExist(activation) == endResultOfRegistrationStatus) {
				return badRequestJson(AssistanceAPIErrors.moduleActivationNotActive);
			} else {
				return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
			}
		}
	}
	
	private void publishUserRegistrationInformationEvent(Long userId, boolean wantsToBeRegistered) {
		ms.channel(UserRegistrationInformationEvent.class).publish(new UserRegistrationInformationEvent(userId, wantsToBeRegistered));
	}
}