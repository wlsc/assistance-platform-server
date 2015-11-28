package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "sensor_mobileconnection")
public class MobileConnection extends SensorData {
	public String carriername;
	public String mobileCountryCode;
	public String mobileNetworkCode;

	@JsonProperty(value = "voipAvailable")
	public boolean voipAvailableOptional;

	public MobileConnection() {
		super();
	}

	public String getCarriername() {
		return carriername;
	}

	public void setCarriername(String carriername) {
		this.carriername = carriername;
	}

	public String getMobileCountryCode() {
		return mobileCountryCode;
	}

	public void setMobileCountryCode(String mobileCountryCode) {
		this.mobileCountryCode = mobileCountryCode;
	}

	public String getMobileNetworkCode() {
		return mobileNetworkCode;
	}

	public void setMobileNetworkCode(String mobileNetworkCode) {
		this.mobileNetworkCode = mobileNetworkCode;
	}

	public boolean isVoipAvailableOptional() {
		return voipAvailableOptional;
	}

	public void setVoipAvailableOptional(boolean voipAvailableOptional) {
		this.voipAvailableOptional = voipAvailableOptional;
	}
}
