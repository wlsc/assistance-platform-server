package de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction;

public abstract class AbstractClientActionSender {
	/**
	 * Implement this to do the actual sending which is specific to the used
	 * platform.
	 * 
	 * @param receiverIds
	 *            The receiverIDs are the platform specific receiver tokens.
	 * @param data
	 *            The data to be sent
	 * @return
	 */
	public abstract boolean platformSpecificSend(String[] receiverIds,
			VisibleNotification notification, String data);
}
