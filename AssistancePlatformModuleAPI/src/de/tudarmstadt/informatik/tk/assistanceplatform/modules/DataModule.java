package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;


public abstract class DataModule extends Module {
	public DataModule(MessagingService messagingService) {
		super(messagingService);
	}
}