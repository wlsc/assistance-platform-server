package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserDeviceEvent;

public class MotionActivity extends UserDeviceEvent {	
	public Boolean walking;
	public Boolean running;
	public Boolean driving;
	public Boolean stationary;
	public Boolean unknown;
	public Integer accuracy;
	
	public Boolean onFootOptional;
	public Boolean tiltingOptional;
	
	public MotionActivity() {
		super();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accuracy == null) ? 0 : accuracy.hashCode());
		result = prime * result + ((driving == null) ? 0 : driving.hashCode());
		result = prime * result
				+ ((onFootOptional == null) ? 0 : onFootOptional.hashCode());
		result = prime * result + ((running == null) ? 0 : running.hashCode());
		result = prime * result
				+ ((stationary == null) ? 0 : stationary.hashCode());
		result = prime * result
				+ ((tiltingOptional == null) ? 0 : tiltingOptional.hashCode());
		result = prime * result + ((unknown == null) ? 0 : unknown.hashCode());
		result = prime * result + ((walking == null) ? 0 : walking.hashCode());
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
		MotionActivity other = (MotionActivity) obj;
		if (accuracy == null) {
			if (other.accuracy != null)
				return false;
		} else if (!accuracy.equals(other.accuracy))
			return false;
		if (driving == null) {
			if (other.driving != null)
				return false;
		} else if (!driving.equals(other.driving))
			return false;
		if (onFootOptional == null) {
			if (other.onFootOptional != null)
				return false;
		} else if (!onFootOptional.equals(other.onFootOptional))
			return false;
		if (running == null) {
			if (other.running != null)
				return false;
		} else if (!running.equals(other.running))
			return false;
		if (stationary == null) {
			if (other.stationary != null)
				return false;
		} else if (!stationary.equals(other.stationary))
			return false;
		if (tiltingOptional == null) {
			if (other.tiltingOptional != null)
				return false;
		} else if (!tiltingOptional.equals(other.tiltingOptional))
			return false;
		if (unknown == null) {
			if (other.unknown != null)
				return false;
		} else if (!unknown.equals(other.unknown))
			return false;
		if (walking == null) {
			if (other.walking != null)
				return false;
		} else if (!walking.equals(other.walking))
			return false;
		return true;
	}
}
