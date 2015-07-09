package controllers;

import models.ActiveAssistanceModule;
import models.AssistanceAPIErrors;
import models.UserModuleActivation;
import persistency.ActiveAssistanceModulePersistency;
import persistency.UserModuleActivationPersistency;
import play.cache.Cached;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;

import com.fasterxml.jackson.databind.JsonNode;

public class AssistanceController extends RestController {
	@Security.Authenticated(UserAuthenticator.class)
	@Cached(key = "moduleList")
	public Result list() {
		ActiveAssistanceModule[] assiModules = ActiveAssistanceModulePersistency.list();
		JsonNode json = Json.toJson(assiModules);
		return ok(json);
	}
	
	@Security.Authenticated(UserAuthenticator.class)
	public Result activate() {
		JsonNode postData = request().body()
				.asJson();
		
		// Get Module ID to activate from request
		JsonNode moduleIdNode = postData.findPath("module_id");
		if(moduleIdNode.isMissingNode()) {
			return badRequestJson(AssistanceAPIErrors.missingModuleIDParameter);
		}
		
		String moduleId = moduleIdNode.textValue();
		
		// Check if the module exists / is registered in the platform
		if(!ActiveAssistanceModulePersistency.doesModuleWithIdExist(moduleId)) {
			return badRequestJson(AssistanceAPIErrors.moduleDoesNotExist);
		}
		
		// Get User id from request
		Long userId = getUserIdForRequest();
		
		UserModuleActivation attemptedActivation = new UserModuleActivation(userId, moduleId);
		
		if(UserModuleActivationPersistency.create(attemptedActivation)) {
			// TODO: Event in Event Stream pushen, sodass MOdule Aktivierung mitbekommen
			
			return ok(); // TODO: Ggf. noch mal mit der Module ID bestätigen oder sogar die Liste aller aktivierten Module (IDs) zurückgeben?
		} else {
			if(UserModuleActivationPersistency.doesActivationExist(attemptedActivation)) {
				return badRequestJson(AssistanceAPIErrors.moduleActivationAlreadyActive);
			} else {
				return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
			}
		}
	}

	
	public Result deactivate() {
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
		
		UserModuleActivation activationToRemove = new UserModuleActivation(userId, moduleId);
		
		if(UserModuleActivationPersistency.remove(activationToRemove)) {
			// TODO: Event in Event Stream pushen, sodass MOdule Aktivierung mitbekommen
			
			return ok(); // TODO: Ggf. noch mal mit der Module ID bestätigen oder sogar die Liste aller aktivierten Module (IDs) zurückgeben?
		} else {
			if(!UserModuleActivationPersistency.doesActivationExist(activationToRemove)) {
				return badRequestJson(AssistanceAPIErrors.moduleActivationNotActive);
			} else {
				return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
			}
		}
	}
}