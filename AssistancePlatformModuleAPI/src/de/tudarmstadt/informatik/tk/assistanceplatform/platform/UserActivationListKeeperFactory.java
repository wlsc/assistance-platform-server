package de.tudarmstadt.informatik.tk.assistanceplatform.platform;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;

public class UserActivationListKeeperFactory {
	private static UserActivationListKeeper instance;
	
	public static UserActivationListKeeper createInstance(String moduleIdResponsibleFor,
			MessagingService messagingService, String platformUrl) {
		if (instance == null) {
			instance = new UserActivationListKeeper(moduleIdResponsibleFor, messagingService, platformUrl);
		}

		return instance;
	}
}
