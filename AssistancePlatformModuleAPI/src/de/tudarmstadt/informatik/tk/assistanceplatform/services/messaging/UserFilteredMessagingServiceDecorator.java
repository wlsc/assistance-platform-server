package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;

/**
 * This class is responsible for filtering messages (/ events) via a user activation list. 
 * Only events of users that are on the activation list are passed through by this class.
 */
public class UserFilteredMessagingServiceDecorator extends MessagingService {
	public MessagingService messagingServiceToFilter;

	private IUserActivationChecker activationChecker;
	
	public UserFilteredMessagingServiceDecorator(MessagingService serviceToFilter, IUserActivationChecker activationChecker) {
		this.messagingServiceToFilter = serviceToFilter;
		this.activationChecker = activationChecker;
	}
	
	@Override
	protected <T> void subscribe(Consumer<T> consumer, Channel<T> channel) {
		Consumer<T> filterConsumer = (c, d) -> {
			if(shouldBlockPassthroughOfEvent(d)) {
				return;
			}
			
			consumer.consumeDataOfChannel(c, d);
		};
		
		messagingServiceToFilter.subscribe(filterConsumer, channel);
	}
	
	private <T> boolean shouldBlockPassthroughOfEvent(T event) {
		if(event instanceof UserEvent) {
			UserEvent dataAsUserEvent = (UserEvent)event;
			if(!activationChecker.isActivatedForUser(dataAsUserEvent.userId)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	protected <T> void unsubscribe(Consumer<T> consumer, Channel<T> channel) {
		messagingServiceToFilter.unsubscribe(consumer, channel);
	}

	@Override
	protected <T> void publish(Channel<T> channel, T data) {
		messagingServiceToFilter.publish(channel, data);
	}
}
