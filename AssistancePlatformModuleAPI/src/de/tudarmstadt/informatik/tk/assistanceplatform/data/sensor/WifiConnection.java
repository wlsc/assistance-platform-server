package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserDeviceEvent;

public class WifiConnection extends UserDeviceEvent {
	public String ssid;
	public String bssid;
	
	public Integer channelOptional;
	public Integer frequencyOptional;
	public Integer linkSpeedOptional;
	public Integer signalStrenghtOptional;
	public Integer networkIdOptional;
	
	public WifiConnection() {
		super();
	}
}
