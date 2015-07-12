package de.tudarmstadt.informatik.tk.assistanceplatform.platform.data;

public class UserRegistrationInformationEvent extends PlatformEvent {
	public Long userId;
	
	public boolean wantsToBeRegistered;
	
	public  UserRegistrationInformationEvent() {
	}
	
	public UserRegistrationInformationEvent(Long userId, boolean wantsToBeRegistered) {
		this.userId = userId;
		this.wantsToBeRegistered = wantsToBeRegistered;
	}
}