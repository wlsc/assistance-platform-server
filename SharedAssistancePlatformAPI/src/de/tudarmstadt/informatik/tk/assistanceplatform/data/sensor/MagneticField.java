package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.io.Serializable;
import java.util.Date;

import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "sensor_magneticfield")
public class MagneticField extends SensorData implements Serializable {
  public double x;
  public double y;
  public double z;

  @JsonProperty("xUncalibratedNoHardIron")
  public float xUncalibratedNoHardIronOptional;
  @JsonProperty("yUncalibratedNoHardIron")
  public float yUncalibratedNoHardIronOptional;
  @JsonProperty("zUncalibratedNoHardIron")
  public float zUncalibratedNoHardIronOptional;

  @JsonProperty("xUncalibratedEstimatedIronBias")
  public float xUncalibratedEstimatedIronBiasOptional;
  @JsonProperty("yUncalibratedEstimatedIronBias")
  public float yUncalibratedEstimatedIronBiasOptional;
  @JsonProperty("zUncalibratedEstimatedIronBias")
  public float zUncalibratedEstimatedIronBiasOptional;

  @JsonProperty("accuracy")
  public int accuracyOptional;

  public MagneticField() {
    super();
  }

  public MagneticField(long userId, long deviceId, Date timestamp, double x, double y, double z) {
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

  public float getXUncalibratedNoHardIronOptional() {
    return xUncalibratedNoHardIronOptional;
  }

  public void setXUncalibratedNoHardIronOptional(float xUncalibratedNoHardIronOptional) {
    this.xUncalibratedNoHardIronOptional = xUncalibratedNoHardIronOptional;
  }

  public float getYUncalibratedNoHardIronOptional() {
    return yUncalibratedNoHardIronOptional;
  }

  public void setYUncalibratedNoHardIronOptional(float yUncalibratedNoHardIronOptional) {
    this.yUncalibratedNoHardIronOptional = yUncalibratedNoHardIronOptional;
  }

  public float getZUncalibratedNoHardIronOptional() {
    return zUncalibratedNoHardIronOptional;
  }

  public void setZUncalibratedNoHardIronOptional(float zUncalibratedNoHardIronOptional) {
    this.zUncalibratedNoHardIronOptional = zUncalibratedNoHardIronOptional;
  }

  public float getXUncalibratedEstimatedIronBiasOptional() {
    return xUncalibratedEstimatedIronBiasOptional;
  }

  public void setXUncalibratedEstimatedIronBiasOptional(
      float xUncalibratedEstimatedIronBiasOptional) {
    this.xUncalibratedEstimatedIronBiasOptional = xUncalibratedEstimatedIronBiasOptional;
  }

  public float getYUncalibratedEstimatedIronBiasOptional() {
    return yUncalibratedEstimatedIronBiasOptional;
  }

  public void setYUncalibratedEstimatedIronBiasOptional(
      float yUncalibratedEstimatedIronBiasOptional) {
    this.yUncalibratedEstimatedIronBiasOptional = yUncalibratedEstimatedIronBiasOptional;
  }

  public float getZUncalibratedEstimatedIronBiasOptional() {
    return zUncalibratedEstimatedIronBiasOptional;
  }

  public void setZUncalibratedEstimatedIronBiasOptional(
      float zUncalibratedEstimatedIronBiasOptional) {
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
    result = prime * result + (int) (temp ^ temp >>> 32);
    result = prime * result + Float.floatToIntBits(xUncalibratedEstimatedIronBiasOptional);
    result = prime * result + Float.floatToIntBits(xUncalibratedNoHardIronOptional);
    temp = Double.doubleToLongBits(y);
    result = prime * result + (int) (temp ^ temp >>> 32);
    result = prime * result + Float.floatToIntBits(yUncalibratedEstimatedIronBiasOptional);
    result = prime * result + Float.floatToIntBits(yUncalibratedNoHardIronOptional);
    temp = Double.doubleToLongBits(z);
    result = prime * result + (int) (temp ^ temp >>> 32);
    result = prime * result + Float.floatToIntBits(zUncalibratedEstimatedIronBiasOptional);
    result = prime * result + Float.floatToIntBits(zUncalibratedNoHardIronOptional);
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
    MagneticField other = (MagneticField) obj;
    return !(accuracyOptional != other.accuracyOptional) && !(Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) && !(Float.floatToIntBits(xUncalibratedEstimatedIronBiasOptional) != Float
        .floatToIntBits(other.xUncalibratedEstimatedIronBiasOptional)) && !(Float.floatToIntBits(xUncalibratedNoHardIronOptional) != Float
        .floatToIntBits(other.xUncalibratedNoHardIronOptional)) && !(Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) && !(Float.floatToIntBits(yUncalibratedEstimatedIronBiasOptional) != Float
        .floatToIntBits(other.yUncalibratedEstimatedIronBiasOptional)) && !(Float.floatToIntBits(yUncalibratedNoHardIronOptional) != Float
        .floatToIntBits(other.yUncalibratedNoHardIronOptional)) && !(Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) && !(Float.floatToIntBits(zUncalibratedEstimatedIronBiasOptional) != Float
        .floatToIntBits(other.zUncalibratedEstimatedIronBiasOptional)) && !(Float.floatToIntBits(zUncalibratedNoHardIronOptional) != Float
        .floatToIntBits(other.zUncalibratedNoHardIronOptional));
  }
}
