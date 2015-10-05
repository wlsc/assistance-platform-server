package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "sensor_gyroscope")
public class Gyroscope extends SensorData {
	public double x;
    public double y;
    public double z;
    
    @JsonProperty(value = "xUncalibratedNoDrift")
    public double xUncalibratedNoDriftOptional;
    @JsonProperty(value = "yUncalibratedNoDrift")
    public double yUncalibratedNoDriftOptional;
    @JsonProperty(value = "zUncalibratedNoDrift")
    public double zUncalibratedNoDriftOptional;
    
    @JsonProperty(value = "xUncalibratedEstimatedDrift")
    public double xUncalibratedEstimatedDriftOptional;
    @JsonProperty(value = "yUncalibratedEstimatedDrift")
    public double yUncalibratedEstimatedDriftOptional;
    @JsonProperty(value = "zUncalibratedEstimatedDrift")
    public double zUncalibratedEstimatedDriftOptional;
    
	public Gyroscope() {
		super();
	}
	
	
	public Gyroscope(long userId, long deviceId, long timestamp, double x, double y, double z) {
		super(userId, deviceId, timestamp);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Gyroscope(double x, double y, double z,
			double xUncalibratedNoDriftOptional,
			double yUncalibratedNoDriftOptional,
			double zUncalibratedNoDriftOptional,
			double xUncalibratedEstimatedDriftOptional,
			double yUncalibratedEstimatedDriftOptional,
			double zUncalibratedEstimatedDriftOptional) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.xUncalibratedNoDriftOptional = xUncalibratedNoDriftOptional;
		this.yUncalibratedNoDriftOptional = yUncalibratedNoDriftOptional;
		this.zUncalibratedNoDriftOptional = zUncalibratedNoDriftOptional;
		this.xUncalibratedEstimatedDriftOptional = xUncalibratedEstimatedDriftOptional;
		this.yUncalibratedEstimatedDriftOptional = yUncalibratedEstimatedDriftOptional;
		this.zUncalibratedEstimatedDriftOptional = zUncalibratedEstimatedDriftOptional;
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


	public double getXUncalibratedNoDriftOptional() {
		return xUncalibratedNoDriftOptional;
	}


	public void setXUncalibratedNoDriftOptional(double xUncalibratedNoDriftOptional) {
		this.xUncalibratedNoDriftOptional = xUncalibratedNoDriftOptional;
	}


	public double getYUncalibratedNoDriftOptional() {
		return yUncalibratedNoDriftOptional;
	}


	public void setYUncalibratedNoDriftOptional(double yUncalibratedNoDriftOptional) {
		this.yUncalibratedNoDriftOptional = yUncalibratedNoDriftOptional;
	}


	public double getZUncalibratedNoDriftOptional() {
		return zUncalibratedNoDriftOptional;
	}


	public void setZUncalibratedNoDriftOptional(double zUncalibratedNoDriftOptional) {
		this.zUncalibratedNoDriftOptional = zUncalibratedNoDriftOptional;
	}


	public double getXUncalibratedEstimatedDriftOptional() {
		return xUncalibratedEstimatedDriftOptional;
	}


	public void setXUncalibratedEstimatedDriftOptional(
			double xUncalibratedEstimatedDriftOptional) {
		this.xUncalibratedEstimatedDriftOptional = xUncalibratedEstimatedDriftOptional;
	}


	public double getYUncalibratedEstimatedDriftOptional() {
		return yUncalibratedEstimatedDriftOptional;
	}


	public void setYUncalibratedEstimatedDriftOptional(
			double yUncalibratedEstimatedDriftOptional) {
		this.yUncalibratedEstimatedDriftOptional = yUncalibratedEstimatedDriftOptional;
	}


	public double getZUncalibratedEstimatedDriftOptional() {
		return zUncalibratedEstimatedDriftOptional;
	}


	public void setZUncalibratedEstimatedDriftOptional(
			double zUncalibratedEstimatedDriftOptional) {
		this.zUncalibratedEstimatedDriftOptional = zUncalibratedEstimatedDriftOptional;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(xUncalibratedEstimatedDriftOptional);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(xUncalibratedNoDriftOptional);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(yUncalibratedEstimatedDriftOptional);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(yUncalibratedNoDriftOptional);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(zUncalibratedEstimatedDriftOptional);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(zUncalibratedNoDriftOptional);
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
		Gyroscope other = (Gyroscope) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(xUncalibratedEstimatedDriftOptional) != Double
				.doubleToLongBits(other.xUncalibratedEstimatedDriftOptional))
			return false;
		if (Double.doubleToLongBits(xUncalibratedNoDriftOptional) != Double
				.doubleToLongBits(other.xUncalibratedNoDriftOptional))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(yUncalibratedEstimatedDriftOptional) != Double
				.doubleToLongBits(other.yUncalibratedEstimatedDriftOptional))
			return false;
		if (Double.doubleToLongBits(yUncalibratedNoDriftOptional) != Double
				.doubleToLongBits(other.yUncalibratedNoDriftOptional))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		if (Double.doubleToLongBits(zUncalibratedEstimatedDriftOptional) != Double
				.doubleToLongBits(other.zUncalibratedEstimatedDriftOptional))
			return false;
		if (Double.doubleToLongBits(zUncalibratedNoDriftOptional) != Double
				.doubleToLongBits(other.zUncalibratedNoDriftOptional))
			return false;
		return true;
	}
}