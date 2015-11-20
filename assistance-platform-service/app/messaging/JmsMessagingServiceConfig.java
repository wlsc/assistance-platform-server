package messaging;

import play.Play;

public class JmsMessagingServiceConfig {
	public static String getUser() {
		return Play.application().configuration().getString("activemq.user");
	}
	
	public static String getPassword() {
		return Play.application().configuration().getString("activemq.password");
	}
	
	public static String getBroker() {
		return Play.application().configuration().getString("activemq.broker");
	}
}
