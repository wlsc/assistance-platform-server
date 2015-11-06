package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization.MessageSerialization;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization.kryo.KryoMessageSerialization;

/**
 * Configuration class for messaging service, like specification of serialization service.
 * @author bjeutter
 */
public class MessagingServiceConfiguration {
	private MessageSerialization messageSerialization;
	
	public MessagingServiceConfiguration() {
		setMessageSerialization(new KryoMessageSerialization());
		//setMessageSerialization(new JsonSerialization());
	}
	
	protected void setMessageSerialization(MessageSerialization serialization) {
		this.messageSerialization = serialization;
	}
	
	public MessageSerialization getMessageSerialization() {
		return messageSerialization;
	}
}
