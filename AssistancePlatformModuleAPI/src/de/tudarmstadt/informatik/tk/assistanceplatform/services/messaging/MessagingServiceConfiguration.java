package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization.KryoMessageSerialization;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization.MessageSerialization;

public class MessagingServiceConfiguration {
	private MessageSerialization messageSerialization;
	
	public MessagingServiceConfiguration() {
		setMessageSerialization(new KryoMessageSerialization());
	}
	
	protected void setMessageSerialization(MessageSerialization serialization) {
		this.messageSerialization = serialization;
	}
	
	public MessageSerialization getMessageSerialization() {
		return messageSerialization;
	}
}
