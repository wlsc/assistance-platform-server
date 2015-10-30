package de.tudarmstadt.informatik.tk.assistanceplatform.services.users;

public class UserActivationListFactory {
	private static UserActivationList instance;

	public static UserActivationList getInstance() {
		if (instance == null) {
			return new UserActivationList();
		}

		return instance;
	}
}