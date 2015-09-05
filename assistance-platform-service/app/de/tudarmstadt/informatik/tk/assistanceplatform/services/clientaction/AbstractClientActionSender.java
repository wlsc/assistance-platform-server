package de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction;

public abstract class AbstractClientActionSender {
	/**
	 * Sends a data package to a user device.
	 * @param userId The id of the user to send the data to
	 * @param deviceId The Id of the users device that should receive the data
	 * @param visibleMessage The message to be shown (can also be null)
	 * @param data The data to send (e.g. a JSON string)
	 * @return TRUE if the message was successfully sent, FALSE if an error occured
	 */
	public boolean sendDataToUserDevices(long userId, long[] deviceIds, VisibleNotification notification,  String data) {
		String[] receiverIds = null; // TODO: receiver ids müssen aus der Datenbank für das Device geholt werden
		
		return doPlatformSpecificSend(receiverIds, notification, data);
	}
	
	/**
	 * Sends a data package to all the registered devices of a user.
	 * @param userId The id of the user to send the data to
	 * @param visibleMessage The message to be shown (can also be null)
	 * @param data The data to send (e.g. a JSON string)
	 * @return TRUE if the message was successfully sent, FALSE if an error occured
	 */
	public boolean sendDataToAllUserDevices(long userId, VisibleNotification notification, String data) {
		long devicesOfUser[] = new long[] {}; 		// TODO: Find all devices of user
	
		return sendDataToUserDevices(userId, devicesOfUser, notification, data);
	}
	
	/**
	 * Sends a data package to a user device
	 * @param userId The id of the user to send the data to
	 * @param data The data to send (e.g. a JSON string)
	 * @return TRUE if the message was successfully sent, FALSE if an error occured
	 */
	public boolean sendDataToUserDevices(long userId, long[] deviceIds, String data) {
		return this.sendDataToUserDevices(userId, deviceIds, null, data);
	}
	
	/**
	 * Sends a data package to all the registered devices of a user.
	 * @param userId The id of the user to send the data to
	 * @param data The data to send (e.g. a JSON string)
	 * @return TRUE if the message was successfully sent, FALSE if an error occured
	 */
	public boolean sendDataToAllUserDevices(long userId, String data) {
		return this.sendDataToAllUserDevices(userId, null, data);
	}
	
	/**
	 * Implement this to do the actual sending which is specific to the used platform. 
	 * @param receiverIds The receiverIDs are the platform specific receiver tokens.
	 * @param data The data to be sent
	 * @return
	 */
	protected abstract boolean doPlatformSpecificSend(String[] receiverIds, VisibleNotification notification, String data);
}
