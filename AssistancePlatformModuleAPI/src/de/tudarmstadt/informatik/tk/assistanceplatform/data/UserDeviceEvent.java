package de.tudarmstadt.informatik.tk.assistanceplatform.data;

import java.util.Date;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;


public class UserDeviceEvent extends UserEvent {
	@PartitionKey(1)
	@Column(name = "device_id")
	public long deviceId;
	
	public UserDeviceEvent() {
		
	}
	
	public UserDeviceEvent(long userId, long deviceId, Date timestamp) {
		super(userId, timestamp);
		this.deviceId = deviceId;
	}
	

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
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