package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_networktraffic")
public class NetworkTraffic extends SensorData {
	public long rxBytes;
	public long txBytes;
	public boolean background;
	public double longitude;
	public double latitude;
	
	public NetworkTraffic() {}

	public NetworkTraffic(long rxBytes, long txBytes, boolean background,
			double longitude, double latitude) {
		super();
		this.rxBytes = rxBytes;
		this.txBytes = txBytes;
		this.background = background;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public long getRxBytes() {
		return rxBytes;
	}

	public void setRxBytes(long rxBytes) {
		this.rxBytes = rxBytes;
	}

	public long getTxBytes() {
		return txBytes;
	}

	public void setTxBytes(long txBytes) {
		this.txBytes = txBytes;
	}

	public boolean isBackground() {
		return background;
	}

	public void setBackground(boolean background) {
		this.background = background;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
