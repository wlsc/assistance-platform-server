package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

import java.util.HashMap;
import java.util.Map;

public abstract class MessagingService {
	private Map<String, Channel> channels = new HashMap<>();
	
	public MessagingService() {
		
	}

	public class Channel {
		private final String name;
		
		public Channel(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public void subscribeConsumer(Consumer consumer) {
			subscribe(consumer, this);
		}
		
		public void unsubscribeConsumer(Consumer consumer) {
			unsubscribe(consumer, this);
		}
		
		public void publish(Object data) {
			MessagingService.this.publish(this, data);
		}
	}
	
	public Channel channel(String name) {
		Channel result = channels.get(name);
		
		if(result == null) {
			Channel newChannel = new Channel(name);
			channels.put(name, newChannel);
			result = newChannel;
		}
		
		return result;
	}
	
	protected abstract void subscribe(Consumer consumer, Channel channel);
	
	protected abstract void unsubscribe(Consumer consumer, Channel channel);
	
	protected abstract void publish(Channel channel, Object data);
}