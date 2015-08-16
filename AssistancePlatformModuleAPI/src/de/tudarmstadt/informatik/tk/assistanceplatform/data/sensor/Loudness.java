package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserDeviceEvent;

public class Loudness extends UserDeviceEvent {
	public Float loudness;
	
	public Loudness() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((loudness == null) ? 0 : loudness.hashCode());
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
		Loudness other = (Loudness) obj;
		if (loudness == null) {
			if (other.loudness != null)
				return false;
		} else if (!loudness.equals(other.loudness))
			return false;
		return true;
	}
}