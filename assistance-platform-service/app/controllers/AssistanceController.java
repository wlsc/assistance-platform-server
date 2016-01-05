package controllers;

import java.text.DateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Predicate;

import messaging.JmsMessagingServiceFactory;
import models.ActiveAssistanceModule;
import models.UserModuleActivation;
import persistency.ActiveAssistanceModulePersistency;
import persistency.DevicePersistency;
import persistency.UserModuleActivationPersistency;
import play.Logger;
import play.cache.Cache;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;

import com.fasterxml.jackson.databind.JsonNode;

import de.tudarmstadt.informatik.tk.assistance.model.client.feedback.content.ClientFeedbackDto;
import de.tudarmstadt.informatik.tk.assistanceplatform.information.CurrentModuleInformationAggregator;
import de.tudarmstadt.informatik.tk.assistanceplatform.information.IModuleInformationPrioritizer;
import de.tudarmstadt.informatik.tk.assistanceplatform.information.ModuleInformationByTimestampPrioritizer;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;
import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import errors.AssistanceAPIErrors;

public class AssistanceController extends RestController {
	MessagingService ms = JmsMessagingServiceFactory.createServiceFromConfig();

	@Security.Authenticated(UserAuthenticator.class)
	public Result list(String language) {

		JsonNode result = Cache
				.getOrElse(
						"moduleList" + language,
						() -> {
							ActiveAssistanceModule[] assiModules = ActiveAssistanceModulePersistency
									.list(language);

							JsonNode json = Json.toJson(assiModules);
							return json;
						}, 3600);

		return ok(result);
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result activations() {
		Long userId = getUserIdForRequest();

		String[] result = UserModuleActivationPersistency
				.activatedModuleIdsForUser(userId);

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

	private Result handleActivationStatusChange(
			Predicate<UserModuleActivation> activationCheck,
			boolean endResultOfRegistrationStatus) {
		JsonNode postData = request().body().asJson();

		// Get Module ID to activate from request
		JsonNode moduleIdNode = postData.findPath("module_id");
		if (moduleIdNode.isMissingNode()) {
			return badRequestJson(AssistanceAPIErrors.missingModuleIDParameter);
		}

		String moduleId = moduleIdNode.textValue();
		
		// Check if module exists
		if(!ActiveAssistanceModulePersistency.doesModuleWithIdExist(moduleId)) {
			return badRequestJson(AssistanceAPIErrors.moduleDoesNotExist);
		}

		// Get User id from request
		long userId = getUserIdForRequest();

		UserModuleActivation activation = new UserModuleActivation(userId,
				moduleId);

		if (activationCheck.test(activation)) {
			publishUserRegistrationInformationEvent(userId, moduleId,
					endResultOfRegistrationStatus);

			return ok();
		} else {
			if (UserModuleActivationPersistency.doesActivationExist(activation) == endResultOfRegistrationStatus) {
				return badRequestJson(AssistanceAPIErrors.moduleActivationNotActive);
			} else {
				return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
			}
		}
	}

	private void publishUserRegistrationInformationEvent(long userId,
			String moduleId, boolean wantsToBeRegistered) {
		ms.channel(UserRegistrationInformationEvent.class).publish(
				new UserRegistrationInformationEvent(userId, moduleId,
						wantsToBeRegistered));
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Promise<Result> current(Long deviceId) {
		return processCurrentForModules(
				deviceId,
				(uId) -> getActivatedModuleEndpoints(uId),
				(cards) -> {
					IModuleInformationPrioritizer infoPrioritizer = new ModuleInformationByTimestampPrioritizer();
					return infoPrioritizer.getPrioritizedInformationList(cards);
				});
	}

	private ActiveAssistanceModule[] getActivatedModuleEndpoints(long userId) {
		return UserModuleActivationPersistency
				.activatedModuleEndpointsForUser(userId);
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Promise<Result> currentForModule(String moduleId, Long deviceId) {
		long userId = getUserIdForRequest();
		
		if(!UserModuleActivationPersistency.doesActivationExist(userId, moduleId)) {
			return Promise.pure(badRequestJson(AssistanceAPIErrors.moduleActivationNotActive));
		}
		
		return processCurrentForModules(
				deviceId,
				(uId) -> UserModuleActivationPersistency.activatedModuleEndpointsForUser(new String[] { moduleId }),
				(c) -> c
				);
	}

	private Promise<Result> processCurrentForModules(
			long deviceId,
			Function<Long, ActiveAssistanceModule[]> moduleProvider,
			Function<List<ModuleInformationCard>, List<ModuleInformationCard>> postProcessingStep) {
		Promise<Result> deviceIdCheckErrror = checkDeviceId(deviceId);

		if (deviceIdCheckErrror != null) {
			return deviceIdCheckErrror;
		}

		// Get User id from request
		long userId = getUserIdForRequest();

		// Get the activated modules
		ActiveAssistanceModule[] activatedModules = null;
		try {
			activatedModules = moduleProvider.apply(userId);
		} catch (Throwable e) {
			Logger.error("Something went wrong on fetching module information", e);
		}

		Promise<List<ModuleInformationCard>> cardPromise = requestModuleInformationCards(
				userId, deviceId, activatedModules);

		if (postProcessingStep != null) {
			cardPromise = cardPromise.map(postProcessingStep);
		}

		return cardPromise.map((prioritizedCards) -> {
			// Map to Client format
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
			df.setTimeZone(tz);
			
			ClientFeedbackDto[] feedbackDTOs = prioritizedCards.stream().map((mic) -> {
				String created = df.format(mic.getTimestamp());

				return new ClientFeedbackDto(mic.getModuleId(), mic.getContent(),created);
			}).toArray(ClientFeedbackDto[]::new);
			// End mapping to client format
			
			JsonNode jsonResult = Json.toJson(feedbackDTOs);

			return ok(jsonResult);
		});
	}

	private Promise<Result> checkDeviceId(long deviceId) {
		if (!DevicePersistency.doesExist(deviceId)) {
			return Promise
					.pure(badRequestJson(AssistanceAPIErrors.deviceIdNotKnown));
		}

		return null;
	}

	private Promise<List<ModuleInformationCard>> requestModuleInformationCards(
			long userId, long deviceId, ActiveAssistanceModule[] modules) {
		CurrentModuleInformationAggregator informationAggregator = new CurrentModuleInformationAggregator(
				userId, deviceId, modules);

		Promise<List<ModuleInformationCard>> result = informationAggregator
				.requestCurrentInformationCards();

		return result;
	}
}
