package controllers;

import models.APIErrorException;
import models.AssistanceAPIErrors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.AbstractClientActionSender;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.ClientActionSenderFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.PlatformNotSupportedException;
import persistency.DevicePersistency;
import persistency.UserPersistency;
import play.Logger;
import play.mvc.Result;
import requests.SendMessageToDeviceRequest;

/**
 * This controller is responsible for handling requests by module to provide the way back to the user / client.
 * @author bjeutter
 */
public class ClientActionController extends RestController {
	public Result sendMessageToDevices() {
		JsonNode jsonRequest = request().body().asJson();
		
		SendMessageToDeviceRequest request = null;
		
		// Convert the JSON to a Request Object
		try {
			request = (new ObjectMapper()).treeToValue(jsonRequest, SendMessageToDeviceRequest.class);
		} catch(Exception ex) {
			Logger.warn("Parsing send message to device request failed", ex);
			return badRequestJson(AssistanceAPIErrors.invalidParametersGeneral);
		}
		
		// Validate the request
		try {
			validateSendMessageRequest(request);
		} catch(APIErrorException e) {
			return badRequestJson(e.getError());
		}
		
		// Iterate through devices and send appropriate messages
		for(long deviceId : request.deviceIds) {
			// Get the platform specific receiver / device IDs for all the devices
			String platformOfDevice = null; // TODO: Platform des Devices auslesen
			
			// TODO: In Zukunft ggf. empfangen, von welchem Modul diese Anfrage kommt
			
			ClientActionSenderFactory actionSenderFactory = new ClientActionSenderFactory();
			try {
				AbstractClientActionSender sender = actionSenderFactory.getClientSender(platformOfDevice);
				boolean sendResult = sender.sendDataToUserDevices(request.userId, request.deviceIds, request.visibleNotification, request.data);
				
				if(sendResult) {
					return ok();
				} else {
					return internalServerErrorJson(AssistanceAPIErrors.unknownInternalServerError);
				}
			} catch (PlatformNotSupportedException e) {
				return badRequestJson(AssistanceAPIErrors.unsupportedPlatform);
			}
		}
		
		return ok();
	}
	
	private void validateSendMessageRequest(SendMessageToDeviceRequest request) throws APIErrorException {
		if(!UserPersistency.doesUserWithIdExist(request.userId)) {
			throw new APIErrorException(AssistanceAPIErrors.userDoesNotExists);
		}
		
		if(request.deviceIds == null) {
			throw new APIErrorException(AssistanceAPIErrors.missingParametersGeneral);
		} else {
			for(long dId : request.deviceIds) {
				if(!DevicePersistency.ownedByUser(dId, request.userId)) {  // Check if device is owned by user (implicitly also checks if the device exists)
					throw new APIErrorException(AssistanceAPIErrors.deviceIdNotKnown);
				} 
			}
		}
	}
}
