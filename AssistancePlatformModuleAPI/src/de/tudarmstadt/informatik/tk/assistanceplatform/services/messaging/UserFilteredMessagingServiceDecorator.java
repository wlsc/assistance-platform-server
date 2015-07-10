package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.UserActivationList;

public class UserFilteredMessagingServiceDecorator extends MessagingService {
	public MessagingService messagingServiceToFilter;

	private UserActivationList activationList;
	
	public UserFilteredMessagingServiceDecorator(MessagingService serviceToFilter, UserActivationList activationList) {
		this.messagingServiceToFilter = serviceToFilter;
		this.activationList = activationList;
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
			if(!activationList.isActivatedForUser(dataAsUserEvent.userId)) {
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
