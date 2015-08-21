package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.IMessagingService;


public abstract class DataModule extends Module {
	public DataModule(IMessagingService messagingService) {
		super(messagingService);
	}
}