package de.tudarmstadt.informatik.tk.assistanceplatform.data;

public class UserDeviceEvent extends UserEvent {
	public Long deviceId;
	
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
		result = prime * result
				+ ((deviceId == null) ? 0 : deviceId.hashCode());
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
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		return true;
	}
	

}