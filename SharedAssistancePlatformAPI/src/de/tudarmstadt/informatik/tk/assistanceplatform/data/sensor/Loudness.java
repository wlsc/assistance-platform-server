package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_loudness")
public class Loudness extends SensorData implements Serializable {
  public float loudness;

  public Loudness() {
    super();
  }



  public float getLoudness() {
    return loudness;
  }



  public void setLoudness(float loudness) {
    this.loudness = loudness;
  }



  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Float.floatToIntBits(loudness);
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
    Loudness other = (Loudness) obj;
    return !(Float.floatToIntBits(loudness) != Float.floatToIntBits(other.loudness));
  }
}
