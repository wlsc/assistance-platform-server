package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.util.Date;

import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "sensor_position")
public class Position extends SensorData {
	public double latitude;
	public double longitude;
	public double accuracyHorizontal;
	public double speed;
	
	/**
	 * Altitude (optional)
	 */
	@JsonProperty(value = "altitude")
	public double altitudeOptional;
	
	/**
	 * Vertical accuracy (optional)
	 */
	@JsonProperty(value = "accuracyVertical")
	public double accuracyVerticalOptional;
	
	/**
	 * Course (optional)
	 */
	@JsonProperty(value = "course")
	public int courseOptional;
	
	/**
	 * Floor (optional)
	 */
	@JsonProperty(value = "floor")
	public int floorOptional;
	
	public Position() {
		super();
	}
	
	public Position(long userId, long deviceId, Date timestamp, double latitude, double longitude) {
		super(userId, deviceId, timestamp);
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double distance(Position pos2) {
	    return distance(this.latitude, this.longitude, pos2.latitude, pos2.longitude);
	    //distance = Math.pow(distance, 2);

	   // return Math.sqrt(distance);
	}
	
	public static double distance(double latPos1, double longPos1, double latPos2, double longPos2) {
	    final int R = 6371; // Radius of the earth

	    Double latDistance = Math.toRadians(latPos2 - latPos1);
	    Double lonDistance = Math.toRadians(longPos2 - longPos1);
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(latPos1)) * Math.cos(Math.toRadians(latPos2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    return distance;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAccuracyHorizontal() {
		return accuracyHorizontal;
	}

	public void setAccuracyHorizontal(double accuracyHorizontal) {
		this.accuracyHorizontal = accuracyHorizontal;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAltitudeOptional() {
		return altitudeOptional;
	}

	public void setAltitudeOptional(double altitudeOptional) {
		this.altitudeOptional = altitudeOptional;
	}

	public double getAccuracyVerticalOptional() {
		return accuracyVerticalOptional;
	}

	public void setAccuracyVerticalOptional(double accuracyVerticalOptional) {
		this.accuracyVerticalOptional = accuracyVerticalOptional;
	}

	public int getCourseOptional() {
		return courseOptional;
	}

	public void setCourseOptional(int courseOptional) {
		this.courseOptional = courseOptional;
	}

	public int getFloorOptional() {
		return floorOptional;
	}

	public void setFloorOptional(int floorOptional) {
		this.floorOptional = floorOptional;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(accuracyHorizontal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(accuracyVerticalOptional);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(altitudeOptional);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + courseOptional;
		result = prime * result + floorOptional;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(speed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Position other = (Position) obj;
		if (Double.doubleToLongBits(accuracyHorizontal) != Double
				.doubleToLongBits(other.accuracyHorizontal))
			return false;
		if (Double.doubleToLongBits(accuracyVerticalOptional) != Double
				.doubleToLongBits(other.accuracyVerticalOptional))
			return false;
		if (Double.doubleToLongBits(altitudeOptional) != Double
				.doubleToLongBits(other.altitudeOptional))
			return false;
		if (courseOptional != other.courseOptional)
			return false;
		if (floorOptional != other.floorOptional)
			return false;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (Double.doubleToLongBits(speed) != Double
				.doubleToLongBits(other.speed))
			return false;
		return true;
	}
}