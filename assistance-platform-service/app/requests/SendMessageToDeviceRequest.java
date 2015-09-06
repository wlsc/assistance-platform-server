package requests;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.VisibleNotification;

/**
 * Simple POJO for a request to the platform which contains information about receiver and data etc. 
 */
public class SendMessageToDeviceRequest {
	public long userId;
	public long[] deviceIds;
	public VisibleNotification visibleNotification; 
	public String data;
	
	public SendMessageToDeviceRequest() {
	}
	
	public SendMessageToDeviceRequest(long userId, long[] deviceIds,
			VisibleNotification visibleNotification, String data) {
		super();
		this.userId = userId;
		this.deviceIds = deviceIds;
		this.visibleNotification = visibleNotification;
		this.data = data;
	}
}