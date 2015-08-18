package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

import java.util.HashMap;
import java.util.Map;

/**
 * A messaging service can be used to publish / subscribe typed objects.
 */
public abstract class MessagingService {
	private Map<String, Channel> channels = new HashMap<>();
	
	public MessagingService() {
		
	}
	
	public <T> Channel<T> channel(Class<T> eventType) {
		String name = eventType.getName();
		
		return channel(name, eventType);
	}

	public <T> Channel<T> channel(String name, Class<T> eventType) {
		Channel<T> result = channels.get(name);
		
		if(result == null) {
			Channel<T> newChannel = new Channel<T>(this, name, eventType);
			channels.put(name, newChannel);
			result = newChannel;
		}
		
		return result;
	}
	
	protected <T> void notifyConsumer(Consumer<T> consumer, Channel<T> channel, T obj) {
		consumer.consumeDataOfChannel(channel, obj);
	}
	
	protected abstract <T> void subscribe(Consumer<T> consumer, Channel<T> channel);
	
	protected abstract <T> void unsubscribe(Consumer<T> consumer, Channel<T> channel);
	
	protected abstract <T> void publish(Channel<T> channel, T data);
}