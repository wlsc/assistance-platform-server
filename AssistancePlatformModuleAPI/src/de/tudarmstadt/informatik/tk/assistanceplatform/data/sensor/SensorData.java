package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.time.Instant;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserDeviceEvent;

@JsonIgnoreProperties(ignoreUnknown=true)
public abstract class SensorData extends UserDeviceEvent {
	public SensorData() {
	}

	public SensorData(long userId, long deviceId, Date timestamp) {
		super(userId, deviceId, timestamp);
	}
}