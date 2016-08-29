package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

import org.apache.log4j.Logger;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClientFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.ServiceConfigResponse;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

public class ConfiguredJmsServiceFactory {
  private static JmsMessagingService jmsInstance;

  public static JmsMessagingService getJmsInstance() {
    return jmsInstance;
  }

  public static JmsMessagingService createJmsInstance(String moduleID) {
    ServiceConfigResponse config =
        PlatformClientFactory.getInstance().getServiceConfig(moduleID, "activemq");

    String broker = resolveRightBrokerAddress(config.address[0],
        PlatformClientFactory.getInstance().getUsedHostWithoutPort());

    Logger.getLogger(ConfiguredJmsServiceFactory.class).info("Using JMS Broker: " + broker);

    String user = config.user;
    String password = config.password;

    jmsInstance = new JmsMessagingService(broker, user, password);

    return jmsInstance;
  }

  private static String resolveRightBrokerAddress(String receivedBrokerAdress,
      String platformHost) {
    if (!PortChecker.portIsOpen(receivedBrokerAdress, 1000)) {
      String[] split = receivedBrokerAdress.replace("tcp://", "").split(":");

      Logger.getLogger(ConfiguredJmsServiceFactory.class)
          .warn("Received broker address not reachable. Trying platform host as broker.");

      return "tcp://" + platformHost + ":" + split[1];
    }

    return receivedBrokerAdress;
  }
}
