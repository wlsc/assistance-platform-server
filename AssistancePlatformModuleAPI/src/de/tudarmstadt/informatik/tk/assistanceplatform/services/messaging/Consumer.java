package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService.Channel;

@FunctionalInterface
public interface Consumer {
	void consumeDataOfChannel(Channel channel, Object data);
}
