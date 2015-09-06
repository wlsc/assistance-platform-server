package controllers;

import models.APIErrorException;
import models.AssistanceAPIErrors;
import persistency.DevicePersistency;
import play.Logger;
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
}