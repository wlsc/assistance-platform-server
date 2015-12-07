package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_runningprocess")
public class RunningProcess extends SensorData {
	public String name;

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
