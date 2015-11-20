package messaging;

import play.Play;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

public class JmsMessagingServiceFactory {
	public static JmsMessagingService createServiceFromConfig() {
		String user = Play.application().configuration().getString("activemq.user");
		String password = Play.application().configuration().getString("activemq.password");
		String broker = Play.application().configuration().getString("activemq.broker");
		
		return new JmsMessagingService(JmsMessagingServiceConfig.getBroker(), JmsMessagingServiceConfig.getUser(), JmsMessagingServiceConfig.getPassword());
	}
}
