package controllers;

import io.netty.util.internal.SystemPropertyUtil;

import java.util.List;
import java.util.function.Predicate;

import messaging.JmsMessagingServiceFactory;
import models.ActiveAssistanceModule;
import models.UserModuleActivation;
import persistency.ActiveAssistanceModulePersistency;
import persistency.UserModuleActivationPersistency;
import play.cache.Cache;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;

import com.fasterxml.jackson.databind.JsonNode;

import de.tudarmstadt.informatik.tk.assistanceplatform.information.CurrentModuleInformationAggregator;
import de.tudarmstadt.informatik.tk.assistanceplatform.information.IModuleInformationPrioritizer;
import de.tudarmstadt.informatik.tk.assistanceplatform.information.ModuleInformationPrioritizerImpl;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;
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
	public Promise<Result> current() {
		// Get User id from request
		long userId = getUserIdForRequest();
		
		System.out.println(userId);
		
		// Get the activated modules
		ActiveAssistanceModule[] activatedModules = getActivatedModuleEndpoints(userId);
		
		return requestModuleInformationCards(activatedModules)
		.map((cards) -> {
			IModuleInformationPrioritizer infoPrioritizer = new ModuleInformationPrioritizerImpl(cards);
			return infoPrioritizer.getPrioritizedInformationList();
		}).
		map((prioritizedCards) -> {
			JsonNode jsonResult = Json.toJson(prioritizedCards);
			
			return ok(jsonResult);
		});
	}
	
	private ActiveAssistanceModule[] getActivatedModuleEndpoints(long userId) {
		return UserModuleActivationPersistency.activatedModuleEndpointsForUser(userId);
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result currentForModule(String moduleId) {
		return TODO;
	}
	
	private Promise<List<ModuleInformationCard>> requestModuleInformationCards(ActiveAssistanceModule[] modules) {
		CurrentModuleInformationAggregator informationAggregator = new CurrentModuleInformationAggregator(modules);
		
		Promise<List<ModuleInformationCard>> result = informationAggregator.requestCurrentInformationCards();
		
		return result;
	}
}
