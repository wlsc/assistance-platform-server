package controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang.math.NumberUtils;

import models.ActiveAssistanceModule;
import persistency.ActiveAssistanceModulePersistency;
import persistency.UserModuleActivationPersistency;
import play.Logger;
import play.cache.Cache;
import play.libs.Json;
import play.mvc.Result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Capability;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.ModuleActivationsResponse;
import errors.APIError;
import errors.AssistanceAPIErrors;

public class ModulesController extends RestController {
	public Result register() {
		return receiveAndProcessAssistanceModuleInformation(false,
				AssistanceAPIErrors.moduleAlreadyExists,
				ActiveAssistanceModulePersistency::create);
	}

	public Result update() {
		return receiveAndProcessAssistanceModuleInformation(true,
				AssistanceAPIErrors.moduleDoesNotExist,
				ActiveAssistanceModulePersistency::update);
	}

	public Result alive() {
		JsonNode postData = request().body().asJson();

		if (postData.has("id")) {
			String id = postData.get("id").asText();

			if (!ActiveAssistanceModulePersistency.doesModuleWithIdExist(id)) {
				return badRequestJson(AssistanceAPIErrors.moduleDoesNotExist);
			}

			if (!ActiveAssistanceModulePersistency.setIsAlive(id)) {
				return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
			}
		} else {
			return badRequestJson(AssistanceAPIErrors.missingParametersGeneral);
		}

		return ok();
	}

	public Result activations(String moduleId) {
		return ok(Json.toJson(new ModuleActivationsResponse(
				UserModuleActivationPersistency
						.userActivationsForModule(moduleId))));
	}

	private Result receiveAndProcessAssistanceModuleInformation(
			boolean expectedExistanceOfModule,
			APIError errorWhenModuleExistanceOtherThanExpected,
			Function<ActiveAssistanceModule, Boolean> func) {
		JsonNode postData = request().body().asJson();

		if (areAllRequiredRegisterParametersPosted(postData)) {
			String id = getIdNode(postData).textValue();

			if (ActiveAssistanceModulePersistency.doesModuleWithIdExist(id) != expectedExistanceOfModule) {
				return badRequestJson(errorWhenModuleExistanceOtherThanExpected);
			}

			String name = getNameNode(postData).textValue();
			String logoUrl = getLogoUrlNode(postData).textValue();
			String description_short = getDescrShortNode(postData).textValue();
			String description_long = getDescrLongNode(postData).textValue();

			ObjectMapper objectMapper = new ObjectMapper();

			Capability[] requiredCapabilites = null;
			Capability[] optionalCapabilities = null;

			try {
				requiredCapabilites = objectMapper.treeToValue(
						getRequiredCapsNode(postData), Capability[].class);
				optionalCapabilities = objectMapper.treeToValue(
						getOptionalCapsNode(postData), Capability[].class);
			} catch (JsonProcessingException e) {
				Logger.warn(
						"Seems like someone posted malformed JSON for module information",
						e);
			}

			String copyright = getCopyrightNode(postData).textValue();

			String administratorEmail = getAdminEmailNode(postData).asText();

			String supportEmail = getSupportEmailNode(postData).asText();
			
			String restContactAddress = getRestContactAddress(postData); 

			// TODO: Validation der Parameter?

			ActiveAssistanceModule module = new ActiveAssistanceModule(name,
					id, logoUrl, description_short, description_long,
					requiredCapabilites, optionalCapabilities, copyright,
					administratorEmail, supportEmail, restContactAddress);

			if (func.apply(module)
					&& ActiveAssistanceModulePersistency.setIsAlive(module.id)) {
				clearCacheForLanguage("en");
				return ok(); 
			}

			return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
		} else {
			return badRequestJson(AssistanceAPIErrors.missingModuleParameters);
		}
	}

	private boolean areAllRequiredRegisterParametersPosted(JsonNode postData) {
		return postData != null && !getIdNode(postData).isMissingNode()
				&& !getNameNode(postData).isMissingNode()
				&& !getLogoUrlNode(postData).isMissingNode()
				&& !getDescrShortNode(postData).isMissingNode()
				&& !getDescrLongNode(postData).isMissingNode()
				&& !getRequiredCapsNode(postData).isMissingNode()
				&& !getOptionalCapsNode(postData).isMissingNode()
				&& !getCopyrightNode(postData).isMissingNode()
				&& !getAdminEmailNode(postData).isMissingNode();
	}
	
	private String getRestContactAddress(JsonNode postData) {
		String submittedAddressData = postData.findPath("restContactAddress").asText();
		
		// If it is just a number, then it is the port
		if(NumberUtils.isNumber(submittedAddressData)) {
			String requestorIp = request().remoteAddress();
			
			return requestorIp + ":" + submittedAddressData;
		}
		
		return submittedAddressData;
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

	private JsonNode getSupportEmailNode(JsonNode postData) {
		return postData.findPath("supportEmail");
	}

	public Result localize() {
		JsonNode postData = request().body().asJson();

		if (areAllRequiredLocalizationParametersPosted(postData)) {
			String id = getIdNode(postData).textValue();

			if (!ActiveAssistanceModulePersistency.doesModuleWithIdExist(id)) {
				return badRequestJson(AssistanceAPIErrors.moduleDoesNotExist);
			}

			String name = getNameNode(postData).textValue();
			String logoUrl = getLogoUrlNode(postData).textValue();
			String description_short = getDescrShortNode(postData).textValue();
			String description_long = getDescrLongNode(postData).textValue();

			ActiveAssistanceModule module = new ActiveAssistanceModule(name,
					id, logoUrl, description_short, description_long, null,
					null, null, null, null, null);

			String languageCode = getLanguageCode(postData).textValue();

			if (ActiveAssistanceModulePersistency
					.localize(languageCode, module)) {
				clearCacheForLanguage(languageCode);

				return ok(); // TODO: Ggf zurück geben, wann sich das Modul das
								// nächste mal "Alive" melden soll
			}

			return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
		} else {
			return badRequestJson(AssistanceAPIErrors.missingModuleParameters);
		}
	}

	public Result bootstrapConfiguration() {
		Map<String, Object> result = new HashMap<>();

		result.put("spark_master", "spark://Bennets-MBP:7077");
		// result.put("broker_url", "spark://Bennets-MBP:7077");

		return ok(result);
	}

	private void clearCacheForLanguage(String languageCode) {
		Cache.remove("moduleList" + languageCode);
	}

	private boolean areAllRequiredLocalizationParametersPosted(JsonNode postData) {
		return postData != null && !getIdNode(postData).isMissingNode()
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