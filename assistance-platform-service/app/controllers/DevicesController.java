package controllers;

import models.APIErrorException;
import models.AssistanceAPIErrors;
import models.Device;
import persistency.DevicePersistency;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import requests.RegisterDeviceForMessagingRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DevicesController extends RestController {
	@Security.Authenticated(UserAuthenticator.class)
	public Result registerDeviceForMessaging() {
		JsonNode jsonRequest = request().body().asJson();

		RegisterDeviceForMessagingRequest request = null;

		// Convert the JSON to a Request Object
		try {
			request = (new ObjectMapper()).treeToValue(jsonRequest,
					RegisterDeviceForMessagingRequest.class);
		} catch (Exception ex) {
			Logger.warn("Parsing send message to device request failed", ex);
			return badRequestJson(AssistanceAPIErrors.invalidParametersGeneral);
		}
		
		long userId = getUserIdForRequest();

		// Validate the request
		try {
			validateRegistrationRequest(request, userId);
		} catch (APIErrorException e) {
			return badRequestJson(e.getError());
		}
		
		// Do the actual linkage to the messaging service
		DevicePersistency.linkDeviceToMessagingService(request.deviceId, request.messagingRegistrationId);
		
		// TODO: ggf. bei dem Messaging Dienstleister nachfragen, ob die ID gültig ist?

		return ok();
	}

	private void validateRegistrationRequest(
			RegisterDeviceForMessagingRequest request, long userId) throws APIErrorException {
		if (request.deviceId <= 0) {
			throw new APIErrorException(
					AssistanceAPIErrors.invalidParametersGeneral);
		} else {
			if (!DevicePersistency.ownedByUser(request.deviceId, userId)) { // Check if device is owned by user (implicitly also checks if the device exists)
				throw new APIErrorException(
						AssistanceAPIErrors.deviceIdNotKnown);
			}
		}
	}
	
	@Security.Authenticated(UserAuthenticator.class)
	public Result listDevices() {
		long userId = getUserIdForRequest();
		
		Device[] usersDevices = DevicePersistency.findDevicesOfUser(userId);
		
		return ok(Json.toJson(usersDevices));
	}
	
	@Security.Authenticated(UserAuthenticator.class)
	public Result setUserDefinedName() {
		long userId = getUserIdForRequest();
		
		JsonNode jsonRequest = request().body().asJson();
		
		long deviceId = -1;
		
		try {
			deviceId = getDeviceId(userId, jsonRequest);
		} catch (APIErrorException e) {
			return badRequestJson(e.getError());
		}
		
		String userDefinedName = jsonRequest.path("user_defined_name").asText();
		
		if(userDefinedName.length() > 30) {
			userDefinedName = userDefinedName.substring(0, 30);
		}
		
		if(!DevicePersistency.setUserDefinedName(deviceId, userDefinedName)) {
			return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
		}
		
		return ok();
	}
	
	private long getDeviceId(long userId, JsonNode request) throws APIErrorException {
		if(!request.has("device_id") && !request.has("user_defined_name")) {
			throw new APIErrorException(AssistanceAPIErrors.missingParametersGeneral);
		} else {
			long deviceId = request.path("device_id").asLong();
			
			if(!DevicePersistency.ownedByUser(deviceId, userId)) {
				throw new APIErrorException(AssistanceAPIErrors.deviceIdNotKnown);
			}
			
			return deviceId;
		}
	}
}