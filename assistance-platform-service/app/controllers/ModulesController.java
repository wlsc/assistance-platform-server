package controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import models.APIError;
import models.ActiveAssistanceModule;
import models.AssistanceAPIErrors;
import persistency.ActiveAssistanceModulePersistency;
import play.cache.Cache;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public class ModulesController extends RestController {
	public Result register() {		
		return receiveAndProcessAssistanceModuleInformation(false, AssistanceAPIErrors.moduleAlreadyExists, ActiveAssistanceModulePersistency::create);
	}
	
	public Result update() {
		return receiveAndProcessAssistanceModuleInformation(true, AssistanceAPIErrors.moduleDoesNotExist, ActiveAssistanceModulePersistency::update);
	}
	
	public Result alive() {
		JsonNode postData = request().body().asJson();
		
		if(postData.has("id")) {
			String id = postData.get("id").asText();
			
			if(!ActiveAssistanceModulePersistency.doesModuleWithIdExist(id)) {
				return badRequestJson(AssistanceAPIErrors.moduleDoesNotExist);
			}
			
			if(!ActiveAssistanceModulePersistency.setIsAlive(id)) {
				return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
			}
		} else {
			return badRequestJson(AssistanceAPIErrors.missingParametersGeneral);
		}
		
		return ok();
	}
	
	private Result receiveAndProcessAssistanceModuleInformation(boolean expectedExistanceOfModule, APIError errorWhenModuleExistanceOtherThanExpected, Function<ActiveAssistanceModule, Boolean> func) {
		JsonNode postData = request().body().asJson();

		if (areAllRequiredRegisterParametersPosted(postData)) {
			String id = getIdNode(postData).textValue();
			
			if(ActiveAssistanceModulePersistency.doesModuleWithIdExist(id) != expectedExistanceOfModule) {
				return badRequestJson(errorWhenModuleExistanceOtherThanExpected);
			}
			
			String name = getNameNode(postData).textValue();
			String logoUrl = getLogoUrlNode(postData).textValue();
			String description_short = getDescrShortNode(postData).textValue();
			String description_long = getDescrLongNode(postData).textValue();
			
			List<String> requiredCapabilites = new LinkedList<>();
			getRequiredCapsNode(postData).forEach(n -> {
				requiredCapabilites.add(n.asText());
			});
			
			List<String> optionalCapabilities = new LinkedList<>();
			getOptionalCapsNode(postData).forEach(n -> {
				optionalCapabilities.add(n.asText());
			});
			
			String copyright = getCopyrightNode(postData).textValue();
			
			String administratorEmail = getAdminEmailNode(postData).asText();
			
			// TODO: Validation der Parameter?
			
			ActiveAssistanceModule module = new ActiveAssistanceModule(name, id, logoUrl, description_short, description_long, requiredCapabilites.toArray(new String[0]), optionalCapabilities.toArray(new String[0]), copyright, administratorEmail);
			
			if(func.apply(module)) {
				return ok(); // TODO: Ggf zurück geben, wann sich das Modul das nächste mal "Alive" melden soll
			}
			
			return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
		} else {
			return badRequestJson(AssistanceAPIErrors.missingModuleParameters);
		}
	}

	private boolean areAllRequiredRegisterParametersPosted(JsonNode postData) {
		return postData != null 
				&& !getIdNode(postData).isMissingNode()
				&& !getNameNode(postData).isMissingNode()
				&& !getLogoUrlNode(postData).isMissingNode()
				&& !getDescrShortNode(postData).isMissingNode()
				&& !getDescrLongNode(postData).isMissingNode()
				&& !getRequiredCapsNode(postData).isMissingNode()
				&& !getOptionalCapsNode(postData).isMissingNode()
				&& !getCopyrightNode(postData).isMissingNode()
				&& !getAdminEmailNode(postData).isMissingNode();
	}

	private JsonNode getIdNode(JsonNode postData) {
		return postData.findPath("id");
	}

	private JsonNode getRequiredCapsNode(JsonNode postData) {
		return postData.withArray("requiredCaps");
	}

	private JsonNode getOptionalCapsNode(JsonNode postData) {
		return postData.withArray("optionalCaps");
	}

	private JsonNode getCopyrightNode(JsonNode postData) {
		return postData.findPath("copyright");
	}
	
	private JsonNode getAdminEmailNode(JsonNode postData) {
		return postData.findPath("administratorEmail");
	}
	
	public Result localize() {
		JsonNode postData = request().body().asJson();

		if (areAllRequiredLocalizationParametersPosted(postData)) {
			String id = getIdNode(postData).textValue();
			
			if(!ActiveAssistanceModulePersistency.doesModuleWithIdExist(id)) {
				return badRequestJson(AssistanceAPIErrors.moduleDoesNotExist);
			}
			
			String name = getNameNode(postData).textValue();
			String logoUrl = getLogoUrlNode(postData).textValue();
			String description_short = getDescrShortNode(postData).textValue();
			String description_long = getDescrLongNode(postData).textValue();

			ActiveAssistanceModule module = new ActiveAssistanceModule(name, id, logoUrl, description_short, description_long, null, null, null, null);
			
			String languageCode = getLanguageCode(postData).textValue();
			
			if(ActiveAssistanceModulePersistency.localize(languageCode, module)) {
				Cache.remove("moduleList" + languageCode);
				
				return ok(); // TODO: Ggf zurück geben, wann sich das Modul das nächste mal "Alive" melden soll
			}
			
			return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
		} else {
			return badRequestJson(AssistanceAPIErrors.missingModuleParameters);
		}
	}
	
	private boolean areAllRequiredLocalizationParametersPosted(JsonNode postData) {
		return postData != null 
				&& !getIdNode(postData).isMissingNode()
				&& !getNameNode(postData).isMissingNode()
				&& !getLogoUrlNode(postData).isMissingNode()
				&& !getDescrShortNode(postData).isMissingNode()
				&& !getDescrLongNode(postData).isMissingNode()
				&& !getLanguageCode(postData).isMissingNode();
	}
	
	private JsonNode getNameNode(JsonNode postData) {
		return postData.findPath("name");
	}

	private JsonNode getLogoUrlNode(JsonNode postData) {
		return postData.findPath("logoUrl");
	}

	private JsonNode getDescrShortNode(JsonNode postData) {
		return postData.findPath("descriptionShort");
	}

	private JsonNode getDescrLongNode(JsonNode postData) {
		return postData.findPath("descriptionLong");
	}
	
	private JsonNode getLanguageCode(JsonNode postData) {
		return postData.findPath("languageCode");
	}
}