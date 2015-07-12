package de.tudarmstadt.informatik.tk.assistanceplatform.services.users;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserActivationList implements IUserActivationChecker {
	private Set<Long> usersWhoActivated = new HashSet<>();
	
	public void addActivationByUser(Long userId) {
		usersWhoActivated.add(userId);
	}
	
	public void removeActivationFromUser(Long userId) {
		usersWhoActivated.remove(userId);
	}
	
	@Override
	public boolean isActivatedForUser(Long userId) {
		return usersWhoActivated.contains(userId);
	}
}
