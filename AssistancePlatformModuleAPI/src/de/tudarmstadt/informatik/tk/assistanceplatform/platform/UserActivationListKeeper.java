package de.tudarmstadt.informatik.tk.assistanceplatform.platform;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.UserActivationList;

public class UserActivationListKeeper {
	private UserActivationList userActivationList;
	
	public UserActivationListKeeper(MessagingService messagingService) {
		this.userActivationList = new UserActivationList();
		
		// TODO: Initial alle bisherigen Subscriptions pullen
		
		new PlatformEventSubscriber<UserRegistrationInformationEvent>(messagingService, this::handleEvent, UserRegistrationInformationEvent.class);
	}
	
	private void handleEvent(Channel<UserRegistrationInformationEvent> channel, UserRegistrationInformationEvent data) {
		if(data.wantsToBeRegistered) {
			userActivationList.addActivationByUser(data.userId);
		} else {
			userActivationList.removeActivationFromUser(data.userId);
		}
	}
	
	public IUserActivationChecker getUserActivationChecker() {
		return userActivationList;
	}
}
