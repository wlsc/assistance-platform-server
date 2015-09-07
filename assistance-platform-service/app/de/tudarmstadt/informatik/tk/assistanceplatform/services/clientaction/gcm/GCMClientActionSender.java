package de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.gcm;


import java.util.Arrays;

import com.fasterxml.jackson.databind.JsonNode;

import play.Logger;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest.VisibleNotification;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.AbstractClientActionSender;

public class GCMClientActionSender extends AbstractClientActionSender {
	private final String apiKey;
	
	public GCMClientActionSender(String apiKey) {
		this.apiKey = apiKey;
	}
	
	@Override
	public boolean platformSpecificSend(String[] receiverIds, VisibleNotification notification, String data) {
		
		WSRequest request = WS.url("https://android.googleapis.com/gcm/send")
		.setContentType("application/json")
		.setHeader("Authorization", "key="+apiKey);
		
		GCMRequest gcmRequest = new GCMRequest();
		
		// Set Registration IDS
		Arrays.stream(receiverIds).forEach(gcmRequest::addRegId);
		
		// Set data
		gcmRequest.setData(data);
		
		// Set notification
		gcmRequest.setVisibleNotification(notification);
		
		// Post the request
		JsonNode json = Json.toJson(gcmRequest);
		Promise<WSResponse> response = request.post(json);
	
		try {
			WSResponse wsResponse = response.get(5000);
			
			if(wsResponse.getStatus() != 200) {
				Logger.warn("GCM Request wasn't successfull, code: " + wsResponse.getStatus());
				
				return false;
			}
		} catch(Exception ex) {
			Logger.error("GCM Request failed: " + ex);
			return false;
		}
        
		return true;
	}
}