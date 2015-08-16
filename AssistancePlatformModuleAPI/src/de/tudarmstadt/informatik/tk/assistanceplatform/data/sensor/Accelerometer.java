package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Accelerometer extends SensorData {
    public Double x;
    public Double y;
    public Double z;
    
    /**
     * Accuracy (optional!)
     */
    @JsonProperty(value = "accuracy")
    public Integer accuracyOptional;
    
    
    public Accelerometer() {
    	super();
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((accuracyOptional == null) ? 0 : accuracyOptional.hashCode());
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		result = prime * result + ((z == null) ? 0 : z.hashCode());
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
		Accelerometer other = (Accelerometer) obj;
		if (accuracyOptional == null) {
			if (other.accuracyOptional != null)
				return false;
		} else if (!accuracyOptional.equals(other.accuracyOptional))
			return false;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		if (z == null) {
			if (other.z != null)
				return false;
		} else if (!z.equals(other.z))
			return false;
		return true;
	}
}