package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_ringtone")
public class RingtoneEvent extends SensorData {
	public int mode;

	public RingtoneEvent(int mode) {
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
