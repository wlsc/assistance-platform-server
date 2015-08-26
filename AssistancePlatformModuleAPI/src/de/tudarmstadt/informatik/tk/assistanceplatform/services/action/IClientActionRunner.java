package de.tudarmstadt.informatik.tk.assistanceplatform.services.action;

public interface IClientActionRunner {
	/**
	 * 
	 * @param userId
	 * @param deviceId if -1 then all devices
	 */
	void setTargetDevice(long userId, long deviceId);
	
	void showMessage(long userId, long deviceId, String message);
	void displayInformation();
	void sendMail();
}