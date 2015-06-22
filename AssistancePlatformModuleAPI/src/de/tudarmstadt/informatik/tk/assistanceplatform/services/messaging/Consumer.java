package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService.Channel;

@FunctionalInterface
public interface Consumer<T> {
	void consumeDataOfChannel(Channel channel, T data);
}