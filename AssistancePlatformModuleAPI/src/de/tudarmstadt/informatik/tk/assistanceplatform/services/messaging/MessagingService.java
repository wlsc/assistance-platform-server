package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

import java.util.HashMap;
import java.util.Map;

public abstract class MessagingService<T> {
	private Map<String, Channel> channels = new HashMap<>();
	
	public MessagingService() {
		
	}

	public class Channel {
		private final String name;
		private final Class<T> type;
		
		public Channel(String name, Class<T> type) {
			this.name = name;
			this.type = type;
		}
		
		public String getName() {
			return name;
		}
		
		public Class<T> getType() {
			return type;
		}
		
		public void subscribeConsumer(Consumer<T> consumer) {
			subscribe(consumer, this);
		}
		
		public void unsubscribeConsumer(Consumer<T> consumer) {
			unsubscribe(consumer, this);
		}
		
		public void publish(T data) {
			MessagingService.this.publish(this, data);
		}
	}
	
	public Channel channel(String name, Class<T> eventType) {
		Channel result = channels.get(name);
		
		if(result == null) {
			Channel newChannel = new Channel(name, eventType);
			channels.put(name, newChannel);
			result = newChannel;
		}
		
		return result;
	}
	
	protected abstract void subscribe(Consumer<T> consumer, Channel channel);
	
	protected abstract void unsubscribe(Consumer<T> consumer, Channel channel);
	
	protected abstract void publish(Channel channel, T data);
}