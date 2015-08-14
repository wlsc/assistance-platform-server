package de.tudarmstadt.informatik.tk.assistanceplatform.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"type"})
public class Position extends UserEvent {
	public Double latitude;
	
	@JsonProperty(required = true)
	public Double longitude;
	public Double accuracyHorizontal;
	public Double speed;
	
	/**
	 * Altitude (optional)
	 */
	public Double altitude;
	
	/**
	 * Vertical accuracy (optional)
	 */
	public Double accuracyVertical;
	
	/**
	 * Course (optional)
	 */
	public Integer course;
	
	/**
	 * Floor (optional)
	 */
	public Integer floor;
	
	public Position() {
		super();
	}
	
	public Position(double latitude, double longitude, long userId, long timestamp) {
		super(userId, timestamp);
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
		// TODO Auto-generated method stub
		int hash = super.hashCode();
		hash = 89 * hash + Double.valueOf(latitude).hashCode();
		hash = 89 * hash + Double.valueOf(longitude).hashCode();
		
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Position)) {
			return false;
		}
		
		Position obj2 = (Position)obj;
		
		return super.equals(obj) 
				&& latitude.equals(obj2.latitude) 
				&& longitude.equals(obj2.longitude)
				&& accuracyHorizontal.equals(obj2.accuracyHorizontal)
				&& speed.equals(obj2.speed);
	}
}