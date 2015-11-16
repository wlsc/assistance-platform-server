package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_light")
public class Light extends SensorData {
	public float value;
	public int accuracy;
	
	public Light() {}
	
	public Light(float value, int accuracy) {
		super();
		this.value = value;
		this.accuracy = accuracy;
	}

	public float getValue() {
		return value;
	}
	
	public void setValue(float value) {
		this.value = value;
	}
	
	public int getAccuracy() {
		return accuracy;
	}
	
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
}
