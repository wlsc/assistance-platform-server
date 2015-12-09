package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_powerlevel")
public class PowerLevel extends SensorData {
	public float percent;

	public PowerLevel(float percent) {
		super();
		this.percent = percent;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}
}
