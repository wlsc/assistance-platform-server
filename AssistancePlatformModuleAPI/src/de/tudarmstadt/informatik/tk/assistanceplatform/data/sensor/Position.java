package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Position extends SensorData {
	public Double latitude;
	
	@JsonProperty(required = true)
	public Double longitude;
	public Double accuracyHorizontal;
	public Double speed;
	
	/**
	 * Altitude (optional)
	 */
	@JsonProperty(value = "altitude")
	public Double altitudeOptional;
	
	/**
	 * Vertical accuracy (optional)
	 */
	@JsonProperty(value = "accuracyVertical")
	public Double accuracyVerticalOptional;
	
	/**
	 * Course (optional)
	 */
	@JsonProperty(value = "course")
	public Integer courseOptional;
	
	/**
	 * Floor (optional)
	 */
	@JsonProperty(value = "floor")
	public Integer floorOptional;
	
	public Position() {
		super();
	}
	
	public Position(long userId, long deviceId, long timestamp, double latitude, double longitude) {
		super(userId, deviceId, timestamp);
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double distance(Position pos2) {

	    final int R = 6371; // Radius of the earth

	    Double latDistance = Math.toRadians(pos2.latitude - this.latitude);
	    Double lonDistance = Math.toRadians(pos2.longitude - this.longitude);
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(pos2.latitude))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    return distance;
	    //distance = Math.pow(distance, 2);

	   // return Math.sqrt(distance);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((accuracyHorizontal == null) ? 0 : accuracyHorizontal
						.hashCode());
		result = prime
				* result
				+ ((accuracyVerticalOptional == null) ? 0
						: accuracyVerticalOptional.hashCode());
		result = prime
				* result
				+ ((altitudeOptional == null) ? 0 : altitudeOptional.hashCode());
		result = prime * result
				+ ((courseOptional == null) ? 0 : courseOptional.hashCode());
		result = prime * result
				+ ((floorOptional == null) ? 0 : floorOptional.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
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
		if (accuracyHorizontal == null) {
			if (other.accuracyHorizontal != null)
				return false;
		} else if (!accuracyHorizontal.equals(other.accuracyHorizontal))
			return false;
		if (accuracyVerticalOptional == null) {
			if (other.accuracyVerticalOptional != null)
				return false;
		} else if (!accuracyVerticalOptional
				.equals(other.accuracyVerticalOptional))
			return false;
		if (altitudeOptional == null) {
			if (other.altitudeOptional != null)
				return false;
		} else if (!altitudeOptional.equals(other.altitudeOptional))
			return false;
		if (courseOptional == null) {
			if (other.courseOptional != null)
				return false;
		} else if (!courseOptional.equals(other.courseOptional))
			return false;
		if (floorOptional == null) {
			if (other.floorOptional != null)
				return false;
		} else if (!floorOptional.equals(other.floorOptional))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (speed == null) {
			if (other.speed != null)
				return false;
		} else if (!speed.equals(other.speed))
			return false;
		return true;
	}
}