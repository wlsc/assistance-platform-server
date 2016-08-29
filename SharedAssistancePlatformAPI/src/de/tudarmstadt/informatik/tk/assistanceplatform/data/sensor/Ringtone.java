package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_ringtone")
public class Ringtone extends SensorData implements Serializable {
  public int mode;

  public Ringtone() {
    super();
  }

  public Ringtone(int mode) {
    super();
    this.mode = mode;
  }

  public int getMode() {
    return mode;
  }

  public void setMode(int mode) {
    this.mode = mode;
  }
}
