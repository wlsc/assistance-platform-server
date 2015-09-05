package de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction;

import com.typesafe.config.ConfigFactory;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.gcm.GCMClientActionSender;

public class ClientActionSenderFactory {
	public AbstractClientActionSender getClientSender(String os) throws PlatformNotSupportedException {
		if(os != null) {
			switch(os) {
			case "android":
				return gcmActionSender();
			case "ios":
				return gcmActionSender();
			}
		}
		
		throw new PlatformNotSupportedException();
	}
	
	private GCMClientActionSender gcmActionSender() {
		String apiKey = ConfigFactory.defaultApplication().getString("gcm.apikey");
		
		return new GCMClientActionSender(apiKey);
	}
}
