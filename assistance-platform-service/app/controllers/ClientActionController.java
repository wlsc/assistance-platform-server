package controllers;

import models.AssistanceAPIErrors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.AbstractClientActionSender;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.ClientActionSenderFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.PlatformNotSupportedException;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.VisibleNotification;
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
		
		try {
			request = (new ObjectMapper()).treeToValue(jsonRequest, SendMessageToDeviceRequest.class);
		} catch(Exception ex) {
			Logger.warn("", ex);
			return badRequestJson(AssistanceAPIErrors.invalidParametersGeneral);
		}
		
		String platformOfDevice = null; // TODO: Platform des Devices auslesen
		
		// TODO: In Zukunft ggf. empfangen, von welchem Modul diese Anfrage kommt
		
		ClientActionSenderFactory actionSenderFactory = new ClientActionSenderFactory();
		try {
			// TODO: Ggf. Akka Actor verwenden?
			AbstractClientActionSender sender = actionSenderFactory.getClientSender(platformOfDevice);
			boolean sendResult = sender.sendDataToUserDevices(request.userId, request.deviceIds, request.visibleNotification, request.data);
			
			if(sendResult) {
				return ok();
			} else {
				// TODO: erorr zurückgegeben
			}
		} catch (PlatformNotSupportedException e) {
			// TODO: error zurückgeben
		}
		
		return TODO;
	}
}
