package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

public interface IMessagingService {
	<T> Channel<T> channel(Class<T> eventType);

	<T> Channel<T> channel(String name, Class<T> eventType);
}
