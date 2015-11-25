package messaging;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

public class JmsMessagingServiceFactory {
	public static JmsMessagingService createServiceFromConfig() {
		return new JmsMessagingService(JmsMessagingServiceConfig.getBroker(), JmsMessagingServiceConfig.getUser(), JmsMessagingServiceConfig.getPassword());
	}
}