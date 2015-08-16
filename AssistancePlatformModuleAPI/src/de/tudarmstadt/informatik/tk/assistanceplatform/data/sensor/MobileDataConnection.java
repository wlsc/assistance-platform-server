package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserDeviceEvent;

public class MobileDataConnection extends UserDeviceEvent {
	public String carriername;
	public String mobileCarrierCode;
	public String mobileNetworkCode;
	
	public Boolean voipAvailableOptional;
	
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
		result = prime
				* result
				+ ((voipAvailableOptional == null) ? 0 : voipAvailableOptional
						.hashCode());
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
		if (voipAvailableOptional == null) {
			if (other.voipAvailableOptional != null)
				return false;
		} else if (!voipAvailableOptional.equals(other.voipAvailableOptional))
			return false;
		return true;
	}	
}
