package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserDeviceEvent;

@JsonIgnoreProperties({"type"})
public abstract class SensorData extends UserDeviceEvent {
	public SensorData() {
	}

	public SensorData(long userId, long deviceId, long timestamp) {
		super(userId, deviceId, timestamp);
	}
}