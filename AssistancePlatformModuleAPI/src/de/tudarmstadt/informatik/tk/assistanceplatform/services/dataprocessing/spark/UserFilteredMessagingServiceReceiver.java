package de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeper;
import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeperFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClientFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.UserFilteredMessagingServiceDecorator;

public class UserFilteredMessagingServiceReceiver<T> extends
		MessagingServiceReceiver<T> {
	private static final long serialVersionUID = 5383380291390118768L;

	private String moduleIdToFilter;

	private String platformUrlAndPort;

	public UserFilteredMessagingServiceReceiver(String moduleIdToFilter,
			Class<T> eventType, String platformUrlAndPort) {
		super(eventType);
		this.moduleIdToFilter = moduleIdToFilter;
		this.platformUrlAndPort = platformUrlAndPort;
	}

	@Override
	protected MessagingService createMessagingService() {
		MessagingService ms = super.createMessagingService();

		UserActivationListKeeper activationListKeeper = UserActivationListKeeperFactory
				.createInstance(moduleIdToFilter, ms, platformUrlAndPort);

		ms = new UserFilteredMessagingServiceDecorator(ms,
				activationListKeeper.getUserActivationChecker());

		return ms;
	}
}
