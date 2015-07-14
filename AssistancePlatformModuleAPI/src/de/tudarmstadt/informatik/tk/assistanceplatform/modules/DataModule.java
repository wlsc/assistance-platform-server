package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;


public abstract class DataModule extends Module {
	// TODO: Für interessante Notifications von der Core Platform registrieren und handeln?
	
	public DataModule(MessagingService messagingService) {
		super(messagingService);
	}
	
	//public abstract UserContext contextForUserWithId(long id); 
}