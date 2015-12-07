package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_runningtask")
public class RunningTask extends SensorData {
	public String name;
	public int stackPosition;
	
	public RunningTask(String name, int stackPosition) {
		super();
		this.name = name;
		this.stackPosition = stackPosition;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getStackPosition() {
		return stackPosition;
	}
	
	public void setStackPosition(int stackPosition) {
		this.stackPosition = stackPosition;
	}
}
