package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

public class Channel<T> {
	private final MessagingService messagingService;
	private final String name;
	private final Class<T> type;
	
	public Channel(MessagingService messagingService, String name, Class<T> type) {
		this.messagingService = messagingService;
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
		this.messagingService.subscribe(consumer, this);
	}
	
	public void unsubscribeConsumer(Consumer<T> consumer) {
		this.messagingService.unsubscribe(consumer, this);
	}
	
	public void publish(T data) {
		this.messagingService.publish(this, data);
	}
}