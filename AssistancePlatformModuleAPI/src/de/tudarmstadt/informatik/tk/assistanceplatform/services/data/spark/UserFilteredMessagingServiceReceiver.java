package de.tudarmstadt.informatik.tk.assistanceplatform.services.data.spark;

import java.util.function.Supplier;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeper;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.UserFilteredMessagingServiceDecorator;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

public class UserFilteredMessagingServiceReceiver<T> extends MessagingServiceReceiver<T> {

	public UserFilteredMessagingServiceReceiver(Class<T> eventType) {
		super(eventType);
		
		Supplier<MessagingService> messagingServiceConstructor = () -> {
			MessagingService ms = new JmsMessagingService();
			UserActivationListKeeper activationListKeeper = new UserActivationListKeeper(ms);
			
			ms = new UserFilteredMessagingServiceDecorator(ms, activationListKeeper.getUserActivationChecker());
			
			return ms;
		};
	}
	
	@Override
	protected MessagingService createMessagingService() {
		MessagingService ms = super.createMessagingService();
		
		UserActivationListKeeper activationListKeeper = new UserActivationListKeeper(ms);
		
		ms = new UserFilteredMessagingServiceDecorator(ms, activationListKeeper.getUserActivationChecker());
		
		return ms;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5383380291390118768L;

}
