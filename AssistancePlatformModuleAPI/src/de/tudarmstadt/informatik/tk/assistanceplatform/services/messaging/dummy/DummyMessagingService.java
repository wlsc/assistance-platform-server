package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.dummy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Consumer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;

public class DummyMessagingService extends MessagingService {
	private Map<Channel, Set<Consumer>> channelConsumers = new HashMap<>();
	
	private void createConsumerSetForChannelIfInexistent(Channel channel) {
		if(!channelConsumers.containsKey(channel)) {
			channelConsumers.put(channel, new HashSet<>());
		}
	}
	
	private Set<Consumer> getSetForChannel(Channel channel) {
		return channelConsumers.get(channel);
	}
	
	@Override
	protected void subscribe(Consumer consumer, Channel channel) {
		createConsumerSetForChannelIfInexistent(channel);
		
		getSetForChannel(channel).add(consumer);
	}

	@Override
	protected void unsubscribe(Consumer consumer, Channel channel) {
		Set<Consumer> consumers = getSetForChannel(channel);
		if(consumers != null) {
			consumers.remove(consumer);
		}
		
	}

	@Override
	protected void publish(Channel channel, Object data) {
		Set<Consumer> consumers = getSetForChannel(channel);
		consumers.forEach((c) -> {
			c.consumeDataOfChannel(channel, data);
		});
	}
}
