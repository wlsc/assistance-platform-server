package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.io.Serializable;
import java.util.Date;

import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "sensor_gyroscope")
public class Gyroscope extends SensorData implements Serializable {
  public double x;
  public double y;
  public double z;

  @JsonProperty("xUncalibratedNoDrift")
  public float xUncalibratedNoDriftOptional;
  @JsonProperty("yUncalibratedNoDrift")
  public float yUncalibratedNoDriftOptional;
  @JsonProperty("zUncalibratedNoDrift")
  public float zUncalibratedNoDriftOptional;

  @JsonProperty("xUncalibratedEstimatedDrift")
  public float xUncalibratedEstimatedDriftOptional;
  @JsonProperty("yUncalibratedEstimatedDrift")
  public float yUncalibratedEstimatedDriftOptional;
  @JsonProperty("zUncalibratedEstimatedDrift")
  public float zUncalibratedEstimatedDriftOptional;

  public Gyroscope() {
    super();
  }


  public Gyroscope(long userId, long deviceId, Date timestamp, double x, double y, double z) {
    super(userId, deviceId, timestamp);
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Gyroscope(double x, double y, double z, float xUncalibratedNoDriftOptional,
      float yUncalibratedNoDriftOptional, float zUncalibratedNoDriftOptional,
      float xUncalibratedEstimatedDriftOptional, float yUncalibratedEstimatedDriftOptional,
      float zUncalibratedEstimatedDriftOptional) {
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


  public float getXUncalibratedNoDriftOptional() {
    return xUncalibratedNoDriftOptional;
  }


  public void setXUncalibratedNoDriftOptional(float xUncalibratedNoDriftOptional) {
    this.xUncalibratedNoDriftOptional = xUncalibratedNoDriftOptional;
  }


  public float getYUncalibratedNoDriftOptional() {
    return yUncalibratedNoDriftOptional;
  }


  public void setYUncalibratedNoDriftOptional(float yUncalibratedNoDriftOptional) {
    this.yUncalibratedNoDriftOptional = yUncalibratedNoDriftOptional;
  }


  public float getZUncalibratedNoDriftOptional() {
    return zUncalibratedNoDriftOptional;
  }


  public void setZUncalibratedNoDriftOptional(float zUncalibratedNoDriftOptional) {
    this.zUncalibratedNoDriftOptional = zUncalibratedNoDriftOptional;
  }


  public float getXUncalibratedEstimatedDriftOptional() {
    return xUncalibratedEstimatedDriftOptional;
  }


  public void setXUncalibratedEstimatedDriftOptional(float xUncalibratedEstimatedDriftOptional) {
    this.xUncalibratedEstimatedDriftOptional = xUncalibratedEstimatedDriftOptional;
  }


  public float getYUncalibratedEstimatedDriftOptional() {
    return yUncalibratedEstimatedDriftOptional;
  }


  public void setYUncalibratedEstimatedDriftOptional(float yUncalibratedEstimatedDriftOptional) {
    this.yUncalibratedEstimatedDriftOptional = yUncalibratedEstimatedDriftOptional;
  }


  public float getZUncalibratedEstimatedDriftOptional() {
    return zUncalibratedEstimatedDriftOptional;
  }


  public void setZUncalibratedEstimatedDriftOptional(float zUncalibratedEstimatedDriftOptional) {
    this.zUncalibratedEstimatedDriftOptional = zUncalibratedEstimatedDriftOptional;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    long temp;
    temp = Double.doubleToLongBits(x);
    result = prime * result + (int) (temp ^ temp >>> 32);
    result = prime * result + Float.floatToIntBits(xUncalibratedEstimatedDriftOptional);
    result = prime * result + Float.floatToIntBits(xUncalibratedNoDriftOptional);
    temp = Double.doubleToLongBits(y);
    result = prime * result + (int) (temp ^ temp >>> 32);
    result = prime * result + Float.floatToIntBits(yUncalibratedEstimatedDriftOptional);
    result = prime * result + Float.floatToIntBits(yUncalibratedNoDriftOptional);
    temp = Double.doubleToLongBits(z);
    result = prime * result + (int) (temp ^ temp >>> 32);
    result = prime * result + Float.floatToIntBits(zUncalibratedEstimatedDriftOptional);
    result = prime * result + Float.floatToIntBits(zUncalibratedNoDriftOptional);
    return result;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Gyroscope other = (Gyroscope) obj;
    return !(Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) && !(Float.floatToIntBits(xUncalibratedEstimatedDriftOptional) != Float
        .floatToIntBits(other.xUncalibratedEstimatedDriftOptional)) && !(Float.floatToIntBits(xUncalibratedNoDriftOptional) != Float
        .floatToIntBits(other.xUncalibratedNoDriftOptional)) && !(Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) && !(Float.floatToIntBits(yUncalibratedEstimatedDriftOptional) != Float
        .floatToIntBits(other.yUncalibratedEstimatedDriftOptional)) && !(Float.floatToIntBits(yUncalibratedNoDriftOptional) != Float
        .floatToIntBits(other.yUncalibratedNoDriftOptional)) && !(Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) && !(Float.floatToIntBits(zUncalibratedEstimatedDriftOptional) != Float
        .floatToIntBits(other.zUncalibratedEstimatedDriftOptional)) && !(Float.floatToIntBits(zUncalibratedNoDriftOptional) != Float
        .floatToIntBits(other.zUncalibratedNoDriftOptional));
  }
}
