package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "sensor_wificonnection")
public class WifiConnection extends SensorData implements Serializable {
	public String ssid;
	public String bssid;
	
	 @JsonProperty(value = "channel")
	public int channelOptional;
	 
	 @JsonProperty(value = "frequency")
	public int frequencyOptional;
	 
	 @JsonProperty(value = "linkSpeed")
	public int linkSpeedOptional;
	 
	 @JsonProperty(value = "signalStrength")
	public int signalStrenghtOptional;
	 
	 @JsonProperty(value = "networkId")
	public int networkIdOptional;
	
	public WifiConnection() {
		super();
	}
	
	

	public String getSsid() {
		return ssid;
	}



	public void setSsid(String ssid) {
		this.ssid = ssid;
	}



	public String getBssid() {
		return bssid;
	}



	public void setBssid(String bssid) {
		this.bssid = bssid;
	}



	public int getChannelOptional() {
		return channelOptional;
	}



	public void setChannelOptional(int channelOptional) {
		this.channelOptional = channelOptional;
	}



	public int getFrequencyOptional() {
		return frequencyOptional;
	}



	public void setFrequencyOptional(int frequencyOptional) {
		this.frequencyOptional = frequencyOptional;
	}



	public int getLinkSpeedOptional() {
		return linkSpeedOptional;
	}



	public void setLinkSpeedOptional(int linkSpeedOptional) {
		this.linkSpeedOptional = linkSpeedOptional;
	}



	public int getSignalStrenghtOptional() {
		return signalStrenghtOptional;
	}



	public void setSignalStrenghtOptional(int signalStrenghtOptional) {
		this.signalStrenghtOptional = signalStrenghtOptional;
	}



	public int getNetworkIdOptional() {
		return networkIdOptional;
	}



	public void setNetworkIdOptional(int networkIdOptional) {
		this.networkIdOptional = networkIdOptional;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bssid == null) ? 0 : bssid.hashCode());
		result = prime * result + channelOptional;
		result = prime * result + frequencyOptional;
		result = prime * result + linkSpeedOptional;
		result = prime * result + networkIdOptional;
		result = prime * result + signalStrenghtOptional;
		result = prime * result + ((ssid == null) ? 0 : ssid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		WifiConnection other = (WifiConnection) obj;
		if (bssid == null) {
			if (other.bssid != null)
				return false;
		} else if (!bssid.equals(other.bssid))
			return false;
		if (channelOptional != other.channelOptional)
			return false;
		if (frequencyOptional != other.frequencyOptional)
			return false;
		if (linkSpeedOptional != other.linkSpeedOptional)
			return false;
		if (networkIdOptional != other.networkIdOptional)
			return false;
		if (signalStrenghtOptional != other.signalStrenghtOptional)
			return false;
		if (ssid == null) {
			if (other.ssid != null)
				return false;
		} else if (!ssid.equals(other.ssid))
			return false;
		return true;
	}	
}
