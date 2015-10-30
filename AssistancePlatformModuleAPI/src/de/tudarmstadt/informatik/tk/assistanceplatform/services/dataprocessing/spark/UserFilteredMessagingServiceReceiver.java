package de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeper;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.UserFilteredMessagingServiceDecorator;

public class UserFilteredMessagingServiceReceiver<T> extends MessagingServiceReceiver<T> {
	private static final long serialVersionUID = 5383380291390118768L;

	
	private String moduleIdToFilter;
	
	public UserFilteredMessagingServiceReceiver(String moduleIdToFilter, Class<T> eventType) {
		super(eventType);
		this.moduleIdToFilter = moduleIdToFilter;
	}
	
	@Override
	protected MessagingService createMessagingService() {
		MessagingService ms = super.createMessagingService();
		
		UserActivationListKeeper activationListKeeper = new UserActivationListKeeper(moduleIdToFilter, ms);
		
		ms = new UserFilteredMessagingServiceDecorator(ms, activationListKeeper.getUserActivationChecker());
		
		return ms;
	}
}
