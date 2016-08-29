package de.tudarmstadt.informatik.tk.assistanceplatform.platform.data;

public class UserRegistrationInformationEvent extends PlatformEvent {
  public long userId;

  public boolean wantsToBeRegistered;

  public String moduleId;

  public UserRegistrationInformationEvent() {}

  public UserRegistrationInformationEvent(long userId, String moduleId,
      boolean wantsToBeRegistered) {
    this.userId = userId;
    this.wantsToBeRegistered = wantsToBeRegistered;
    this.moduleId = moduleId;
  }
}
