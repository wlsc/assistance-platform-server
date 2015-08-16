package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserDeviceEvent;

public class ConnectionStatus extends UserDeviceEvent {
	public Boolean isWifi;
	public Boolean isMobile;
	
	public ConnectionStatus() {
		super();
	}
}