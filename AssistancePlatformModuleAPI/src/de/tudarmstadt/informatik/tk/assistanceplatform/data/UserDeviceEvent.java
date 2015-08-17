package de.tudarmstadt.informatik.tk.assistanceplatform.data;

import java.time.Instant;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class UserDeviceEvent extends UserEvent {
	public long deviceId;
	
	public UserDeviceEvent() {
		
	}
	
	public UserDeviceEvent(long userId, long deviceId, long timestamp) {
		super(userId, timestamp);
		this.deviceId = deviceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (deviceId ^ (deviceId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDeviceEvent other = (UserDeviceEvent) obj;
		if (deviceId != other.deviceId)
			return false;
		return true;
	}
}