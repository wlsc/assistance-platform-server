package de.tudarmstadt.informatik.tk.assistanceplatform.services.users;

public interface IUserActivationChecker {
	boolean isActivatedForUser(Long userId);
}
