package de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeper;
import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeperFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.UserFilteredMessagingServiceDecorator;

public class UserFilteredMessagingServiceReceiver<T> extends MessagingServiceReceiver<T> {
  public UserFilteredMessagingServiceReceiver(String usingModuleId, String platformUrlAndPort,
      Class<T> eventType) {
    super(usingModuleId, platformUrlAndPort, eventType);
  }

  private static final long serialVersionUID = 5383380291390118768L;

  @Override
  protected MessagingService createMessagingService() {
    MessagingService ms = super.createMessagingService();

    UserActivationListKeeper activationListKeeper =
        UserActivationListKeeperFactory.createInstance(this.usingModuleId, ms, platformUrlAndPort);

    ms = new UserFilteredMessagingServiceDecorator(ms,
        activationListKeeper.getUserActivationChecker());

    return ms;
  }
}
