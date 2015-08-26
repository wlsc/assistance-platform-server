package de.tudarmstadt.informatik.tk.assistanceplatform.services.action;

public interface IClientActionRunner {
	void showMessage(long userId, long deviceId, String message);
	void displayInformation();
	void sendMail();
}