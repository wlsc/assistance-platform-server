package de.tudarmstadt.informatik.tk.assistanceplatform.services.action;

public interface IClientActionRunner {
	void showMessage(long userId, long deviceIds[], String title, String message);
	/**
	 * FOR TESTING ONLY!!! NOT FOR PRODUCTION MODE!
	 * @param userId
	 * @param deviceId
	 * @param title
	 * @param message
	 */
	void sendTestData(long userId, long deviceIds[], String data);
	void displayInformation();
	void sendMail();
}