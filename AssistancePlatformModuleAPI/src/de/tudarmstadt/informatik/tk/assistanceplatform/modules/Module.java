package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Consumer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.IMessagingService;

public abstract class Module {
	private Map<Class, Consumer> eventRegistrations = new HashMap<>();

	private IMessagingService messagingService;

	public Module() {
	}

	public final void setMessagingService(IMessagingService messagingService) {
		this.messagingService = messagingService;
	}

	/**
	 * NEVER! call this method, this method gets called by the API.
	 */
	public final void start() {
		internalDoBeforeStartup();
		doBeforeStartup();
		subscribeRegisteredEventsAndSetMessagingService(messagingService);
		doAfterStartup();
	}

	public <T> void registerForEventOfType(Class<T> type, Consumer<T> consumer) {
		eventRegistrations.put(type, consumer);
	}

	public <T> boolean emitEvent(T event) {
		Class<T> type = (Class<T>) event.getClass();
		return this.messagingService.channel(type).publish(event);
	}

	private void subscribeRegisteredEventsAndSetMessagingService(
			IMessagingService messagingService) {
		this.messagingService = messagingService;

		for (Entry<Class, Consumer> entry : eventRegistrations.entrySet()) {
			messagingService.channel(entry.getKey()).subscribeConsumer(
					entry.getValue());
		}

		this.eventRegistrations = null;
	}

	protected abstract void internalDoBeforeStartup();

	/**
	 * This method gets called before all services are set up and events are
	 * registered. This is the place to register for events.
	 */
	protected abstract void doBeforeStartup();

	/**
	 * This method gets called imediately after all services are set up and
	 * events are registered. Implement this for one-time initialization
	 * routines like pulling latest data.
	 */
	protected abstract void doAfterStartup();
}