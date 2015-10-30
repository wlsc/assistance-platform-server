package de.tudarmstadt.informatik.tk.assistanceplatform.platform;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.UserActivationList;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.UserActivationListFactory;

public class UserActivationListKeeper {
	private UserActivationList userActivationList;
	
	private String moduleIdResponsibleFor ;

	public UserActivationListKeeper(String moduleIdResponsibleFor,
			MessagingService messagingService) {
		this.moduleIdResponsibleFor = moduleIdResponsibleFor;
		
		this.userActivationList = UserActivationListFactory.getInstance();
		
		// TODO: Initial alle bisherigen Subscriptions pullen


		new PlatformEventSubscriber<UserRegistrationInformationEvent>(
				messagingService, this::handleEvent,
				UserRegistrationInformationEvent.class);
	}

	private void handleEvent(Channel<UserRegistrationInformationEvent> channel,
			UserRegistrationInformationEvent data) {
		if (data.moduleId.equals(moduleIdResponsibleFor)) {
			if (data.wantsToBeRegistered) {
				userActivationList.addActivationByUser(data.userId);
			} else {
				userActivationList.removeActivationFromUser(data.userId);
			}
		}
	}

	public IUserActivationChecker getUserActivationChecker() {
		return userActivationList;
	}
}