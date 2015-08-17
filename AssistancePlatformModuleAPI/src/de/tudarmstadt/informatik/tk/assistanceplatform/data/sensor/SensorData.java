package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.time.Instant;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserDeviceEvent;

@JsonIgnoreProperties(ignoreUnknown=true)
public abstract class SensorData extends UserDeviceEvent {
	public SensorData() {
	}

	public SensorData(long userId, long deviceId, Long timestamp) {
		super(userId, deviceId, timestamp);
	}
}