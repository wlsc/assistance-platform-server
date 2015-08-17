package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Accelerometer extends SensorData {
    public double x;
    public double y;
    public double z;
    
    /**
     * Accuracy (optional!)
     */
    @JsonProperty(value = "accuracy")
    public int accuracyOptional;
    
    
    public Accelerometer() {
    	super();
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + accuracyOptional;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
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
		Accelerometer other = (Accelerometer) obj;
		if (accuracyOptional != other.accuracyOptional)
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
}