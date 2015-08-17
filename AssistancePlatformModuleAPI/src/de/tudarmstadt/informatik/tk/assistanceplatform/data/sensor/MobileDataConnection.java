package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MobileDataConnection extends SensorData {
	public String carriername;
	public String mobileCarrierCode;
	public String mobileNetworkCode;
	
	@JsonProperty(value = "voipAvailable")
	public boolean voipAvailableOptional;
	
	public MobileDataConnection() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((carriername == null) ? 0 : carriername.hashCode());
		result = prime
				* result
				+ ((mobileCarrierCode == null) ? 0 : mobileCarrierCode
						.hashCode());
		result = prime
				* result
				+ ((mobileNetworkCode == null) ? 0 : mobileNetworkCode
						.hashCode());
		result = prime * result + (voipAvailableOptional ? 1231 : 1237);
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
		MobileDataConnection other = (MobileDataConnection) obj;
		if (carriername == null) {
			if (other.carriername != null)
				return false;
		} else if (!carriername.equals(other.carriername))
			return false;
		if (mobileCarrierCode == null) {
			if (other.mobileCarrierCode != null)
				return false;
		} else if (!mobileCarrierCode.equals(other.mobileCarrierCode))
			return false;
		if (mobileNetworkCode == null) {
			if (other.mobileNetworkCode != null)
				return false;
		} else if (!mobileNetworkCode.equals(other.mobileNetworkCode))
			return false;
		if (voipAvailableOptional != other.voipAvailableOptional)
			return false;
		return true;
	}
}
