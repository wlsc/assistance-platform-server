package de.tudarmstadt.informatik.tk.assistanceplatform.services.users;

import java.util.HashSet;
import java.util.Set;

/**
 * Simple set implementation of the activation checker interface.
 */
public class UserActivationList implements IUserActivationList, IUserActivationChecker {
	private Set<Long> usersWhoActivated = new HashSet<>();
	
	@Override
	public void addActivationByUser(long userId) {
		usersWhoActivated.add(userId);
	}

	@Override
	public void removeActivationFromUser(long userId) {
		usersWhoActivated.remove(userId);
	}
	
	@Override
	public boolean isActivatedForUser(long userId) {
		return usersWhoActivated.contains(userId);
	}
}
