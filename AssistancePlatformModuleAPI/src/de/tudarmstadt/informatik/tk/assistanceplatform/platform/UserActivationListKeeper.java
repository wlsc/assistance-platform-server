package de.tudarmstadt.informatik.tk.assistanceplatform.platform;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.actions.IGetUserActivationsForModule;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.UserActivationList;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.UserActivationListFactory;

public class UserActivationListKeeper {
  private UserActivationList userActivationList;

  private String moduleIdResponsibleFor;


  public UserActivationListKeeper(String moduleIdResponsibleFor, MessagingService messagingService,
      IGetUserActivationsForModule activationsPuller) {
    this.moduleIdResponsibleFor = moduleIdResponsibleFor;

    this.userActivationList = UserActivationListFactory.getInstance();

    // Pull registrations until now
    activationsPuller.getUserActivationsForModule(moduleIdResponsibleFor,
        this::initializeExistingActivations);

    // Register for new informations
    new PlatformEventSubscriber<UserRegistrationInformationEvent>(messagingService,
        this::handleEvent, UserRegistrationInformationEvent.class);
  }

  private void initializeExistingActivations(long[] usersThatActivatedTheModule) {
    for (long userId : usersThatActivatedTheModule) {
      setActivationOfUser(userId, true);
    }
  }

  private void handleEvent(Channel<UserRegistrationInformationEvent> channel,
      UserRegistrationInformationEvent data) {
    if (data.moduleId.equals(moduleIdResponsibleFor)) {
      setActivationOfUser(data.userId, data.wantsToBeRegistered);
    }
  }

  private void setActivationOfUser(long userId, boolean activated) {
    if (activated) {
      userActivationList.addActivationByUser(userId);
    } else {
      userActivationList.removeActivationFromUser(userId);
    }
  }

  public IUserActivationChecker getUserActivationChecker() {
    return userActivationList;
  }
}
