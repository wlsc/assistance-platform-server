package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.IMessagingService;

public abstract class Module {
	private IMessagingService messagingService;
	
	public Module(IMessagingService messagingService) {
		this.messagingService = messagingService;
		startup();
	}
	
	public IMessagingService messagingService() {
		return this.messagingService;
	}
	
	private void startup() {
		doAfterStartup();
	}
	
	/**
	 * This method gets called imediately after all services are set up. Implement this for one-time initialization routines like pulling latest data.
	 */
	protected abstract void doAfterStartup();
}