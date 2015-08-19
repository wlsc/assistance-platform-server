package controllers;

import java.util.function.Predicate;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;

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
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

@Api(value = "/assistance", description = "Operations for user assistance, like activation and deactivation of modules.")
public class AssistanceController extends RestController {
	MessagingService ms = new JmsMessagingService();
	
	@ApiOperation(nickname = "list", value = "List the available assistance modules", httpMethod = "POST")
	@Security.Authenticated(UserAuthenticator.class)
	 @ApiImplicitParams({
		    @ApiImplicitParam(name = "name", value = "User's name", required = true, dataType = "string", paramType = "header"),
		    @ApiImplicitParam(name = "email", value = "User's email", required = false, dataType = "string", paramType = "query"),
		    @ApiImplicitParam(name = "id", value = "User ID", required = true, dataType = "long", paramType = "body")
		  })
	public Result list(
			@ApiParam(value = "Language code (iso 639-1) that tells in which language the list should be returned") @PathParam("language") String language) {
	
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