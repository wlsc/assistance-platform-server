package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.ModuleBundle;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClientFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.CassandraServiceConfigResponse;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.ServiceConfigResponse;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

public class ConfiguredJmsServiceFactory {
	private static JmsMessagingService jmsInstance;

	public static JmsMessagingService getJmsInstance() {
		return jmsInstance;
	}

	public static void createJmsInstance(String moduleID) {
		ServiceConfigResponse config = PlatformClientFactory.getInstance().getServiceConfig(moduleID, "activemq");

		String broker = config.address[0];
		String user = config.user;
		String password = config.password;
		
		jmsInstance = new JmsMessagingService(broker, user, password);
		
		//createJmsInstance = new CassandraSessionProxy(contactPoints, config.keystoreName, config.user, config.password);
	}
}
