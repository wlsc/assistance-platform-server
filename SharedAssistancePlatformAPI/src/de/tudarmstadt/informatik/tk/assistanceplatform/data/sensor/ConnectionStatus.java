package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_connectionstatus")
public class ConnectionStatus extends SensorData {
	public boolean isWifi;
	public boolean isMobile;
	
	public ConnectionStatus() {
		super();
	}
	
	

	public boolean getIsWifi() {
		return isWifi;
	}



	public void setIsWifi(boolean isWifi) {
		this.isWifi = isWifi;
	}



	public boolean getIsMobile() {
		return isMobile;
	}



	public void setIsMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (isMobile ? 1231 : 1237);
		result = prime * result + (isWifi ? 1231 : 1237);
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
		ConnectionStatus other = (ConnectionStatus) obj;
		if (isMobile != other.isMobile)
			return false;
		if (isWifi != other.isWifi)
			return false;
		return true;
	}
}