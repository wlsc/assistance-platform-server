package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.dummy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Consumer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;

public class DummyMessagingService<T> extends MessagingService<T> {
	private Map<Channel, Set<Consumer<T>>> channelConsumers = new HashMap<>();
	
	private void createConsumerSetForChannelIfInexistent(Channel channel) {
		if(!channelConsumers.containsKey(channel)) {
			channelConsumers.put(channel, new HashSet<>());
		}
	}
	
	private Set<Consumer<T> > getSetForChannel(Channel channel) {
		return channelConsumers.get(channel);
	}
	
	@Override
	protected void subscribe(Consumer<T> consumer, Channel channel) {
		createConsumerSetForChannelIfInexistent(channel);
		
		getSetForChannel(channel).add(consumer);
	}

	@Override
	protected void unsubscribe(Consumer consumer, Channel channel) {
		Set<Consumer<T>> consumers = getSetForChannel(channel);
		if(consumers != null) {
			consumers.remove(consumer);
		}
		
	}

	@Override
	protected void publish(Channel channel, T data) {
		Set<Consumer<T>> consumers = getSetForChannel(channel);
		consumers.forEach((c) -> {
			c.consumeDataOfChannel(channel, data);
		});
	}
}
