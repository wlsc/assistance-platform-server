package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.dummy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Consumer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;

public class DummyMessagingService extends MessagingService {
	private Map<Channel, Set> channelConsumers = new HashMap<>();
	
	private <T> void createConsumerSetForChannelIfInexistent(Channel<T> channel) {
		if(!channelConsumers.containsKey(channel)) {
			channelConsumers.put(channel, new HashSet<>());
		}
	}
	
	private <T> Set<Consumer<T>> getSetForChannel(Channel<T> channel) {
		return channelConsumers.get(channel);
	}
	
	@Override
	protected <T> void subscribe(Consumer<T> consumer, Channel<T> channel) {
		createConsumerSetForChannelIfInexistent(channel);
		
		getSetForChannel(channel).add(consumer);
	}

	@Override
	protected <T> void unsubscribe(Consumer<T> consumer, Channel<T> channel) {
		Set<Consumer<T>> consumers = getSetForChannel(channel);
		if(consumers != null) {
			consumers.remove(consumer);
		}
		
	}

	@Override
	protected <T> boolean publish(Channel<T> channel, T data) {
		Set<Consumer<T>> consumers = getSetForChannel(channel);
		consumers.forEach((c) -> {
			notifyConsumer(c, channel, data);
		});
		return true;
	}
}
