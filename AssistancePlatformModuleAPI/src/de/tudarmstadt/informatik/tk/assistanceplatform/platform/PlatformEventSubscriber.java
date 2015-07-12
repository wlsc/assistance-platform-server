package de.tudarmstadt.informatik.tk.assistanceplatform.platform;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.PlatformEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Consumer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;

class PlatformEventSubscriber<T extends PlatformEvent> {
	private MessagingService messagingService;
	
	public PlatformEventSubscriber(MessagingService messagingService, Consumer<T> subscriber, Class<T> type) {
		this.messagingService = messagingService;
		
		doSubscription(subscriber, type);
	}
	
	private void doSubscription(Consumer<T> subscriber, Class<T> type) {
		this.messagingService.channel(type).subscribeConsumer(subscriber);
	}
}