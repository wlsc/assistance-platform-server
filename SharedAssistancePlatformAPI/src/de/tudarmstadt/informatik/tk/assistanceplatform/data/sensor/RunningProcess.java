package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_runningprocess")
public class RunningProcess extends SensorData implements Serializable {
  public String name;

  public RunningProcess() {
    super();
  }

  public RunningProcess(String name) {
    super();
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
