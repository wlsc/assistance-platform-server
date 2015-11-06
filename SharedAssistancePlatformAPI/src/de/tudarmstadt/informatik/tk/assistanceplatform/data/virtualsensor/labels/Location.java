package de.tudarmstadt.informatik.tk.assistanceplatform.data.virtualsensor.labels;

import com.datastax.driver.mapping.annotations.UDT;

@UDT(name = "location")
public class Location {
	public double longitude;
	public double latitude;
	public double accuracy;
	
	public Location() {
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

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	
	
}
