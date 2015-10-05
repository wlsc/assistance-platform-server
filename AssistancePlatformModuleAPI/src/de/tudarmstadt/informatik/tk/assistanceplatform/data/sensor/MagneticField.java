package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "sensor_magneticfield")
public class MagneticField extends SensorData {
	public double x;
    public double y;
    public double z;
    
    @JsonProperty(value = "xUncalibratedNoHardIron")
	public double xUncalibratedNoHardIronOptional;
    @JsonProperty(value = "yUncalibratedNoHardIron")
    public double yUncalibratedNoHardIronOptional;
    @JsonProperty(value = "zUncalibratedNoHardIron")
    public double zUncalibratedNoHardIronOptional;
    
    @JsonProperty(value = "xUncalibratedEstimatedIronBias")
	public double xUncalibratedEstimatedIronBiasOptional;
    @JsonProperty(value = "yUncalibratedEstimatedIronBias")
    public double yUncalibratedEstimatedIronBiasOptional;
    @JsonProperty(value = "zUncalibratedEstimatedIronBias")
    public double zUncalibratedEstimatedIronBiasOptional;
    
    @JsonProperty(value = "accuracy")
    public int accuracyOptional;
    
	public MagneticField() {
		super();
	}
	
	public MagneticField(long userId, long deviceId, Long timestamp, double x, double y, double z) {
		super(userId, deviceId, timestamp);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getXUncalibratedNoHardIronOptional() {
		return xUncalibratedNoHardIronOptional;
	}

	public void setXUncalibratedNoHardIronOptional(
			double xUncalibratedNoHardIronOptional) {
		this.xUncalibratedNoHardIronOptional = xUncalibratedNoHardIronOptional;
	}

	public double getYUncalibratedNoHardIronOptional() {
		return yUncalibratedNoHardIronOptional;
	}

	public void setYUncalibratedNoHardIronOptional(
			double yUncalibratedNoHardIronOptional) {
		this.yUncalibratedNoHardIronOptional = yUncalibratedNoHardIronOptional;
	}

	public double getZUncalibratedNoHardIronOptional() {
		return zUncalibratedNoHardIronOptional;
	}

	public void setZUncalibratedNoHardIronOptional(
			double zUncalibratedNoHardIronOptional) {
		this.zUncalibratedNoHardIronOptional = zUncalibratedNoHardIronOptional;
	}

	public double getXUncalibratedEstimatedIronBiasOptional() {
		return xUncalibratedEstimatedIronBiasOptional;
	}

	public void setXUncalibratedEstimatedIronBiasOptional(
			double xUncalibratedEstimatedIronBiasOptional) {
		this.xUncalibratedEstimatedIronBiasOptional = xUncalibratedEstimatedIronBiasOptional;
	}

	public double getYUncalibratedEstimatedIronBiasOptional() {
		return yUncalibratedEstimatedIronBiasOptional;
	}

	public void setYUncalibratedEstimatedIronBiasOptional(
			double yUncalibratedEstimatedIronBiasOptional) {
		this.yUncalibratedEstimatedIronBiasOptional = yUncalibratedEstimatedIronBiasOptional;
	}

	public double getZUncalibratedEstimatedIronBiasOptional() {
		return zUncalibratedEstimatedIronBiasOptional;
	}

	public void setZUncalibratedEstimatedIronBiasOptional(
			double zUncalibratedEstimatedIronBiasOptional) {
		this.zUncalibratedEstimatedIronBiasOptional = zUncalibratedEstimatedIronBiasOptional;
	}

	public int getAccuracyOptional() {
		return accuracyOptional;
	}

	public void setAccuracyOptional(int accuracyOptional) {
		this.accuracyOptional = accuracyOptional;
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
		MagneticField other = (MagneticField) obj;
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
