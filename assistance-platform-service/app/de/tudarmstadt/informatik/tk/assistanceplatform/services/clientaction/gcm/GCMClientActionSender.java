package de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.gcm;

import org.apache.log4j.Logger;

import play.libs.F.Promise;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.AbstractClientActionSender;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.VisibleNotification;

public class GCMClientActionSender extends AbstractClientActionSender {
	private final String apiKey;
	
	public GCMClientActionSender(String apiKey) {
		this.apiKey = apiKey;
	}
	
	@Override
	protected boolean doPlatformSpecificSend(String[] receiverIds, VisibleNotification notification, String data) {
		
		WSRequest request = WS.url("https://android.googleapis.com/gcm/send")
		.setContentType("application/json")
		.setAuth("key=" + apiKey);
		
		GCMRequest gcmRequest = new GCMRequest();
		for(String r : receiverIds) {
			gcmRequest.addRegId(r);
		}
		
		gcmRequest.setData(data);
		
		gcmRequest.setVisibleNotification(notification);
		
		Promise<WSResponse> response = request.post(Json.toJson(gcmRequest));
	
		try {
			WSResponse wsResponse = response.get(5000);
			
			if(wsResponse.getStatus() != 200) {
				Logger.getLogger(GCMClientActionSender.class).warn("GCM Request wasn't successfull, code: " + wsResponse.getStatus());
				
				return false;
			}
		} catch(Exception ex) {
			Logger.getLogger(GCMClientActionSender.class).error("GCM Request failed: " + ex);
			return false;
		}
        
		return true;
	}
}