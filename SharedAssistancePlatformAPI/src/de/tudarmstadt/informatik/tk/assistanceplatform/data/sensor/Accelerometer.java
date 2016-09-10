package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "sensor_accelerometer")
public class Accelerometer extends SensorData implements Serializable {
  public double x;
  public double y;
  public double z;

  /**
   * Accuracy (optional!)
   */
  @JsonProperty("accuracy")
  public int accuracyOptional;

  public Accelerometer() {
    super();
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
    temp = Double.doubleToLongBits(y);
    result = prime * result + (int) (temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(z);
    result = prime * result + (int) (temp ^ temp >>> 32);
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
    Accelerometer other = (Accelerometer) obj;
    return !(accuracyOptional != other.accuracyOptional) && !(Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) && !(Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) && !(Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z));
  }
}
