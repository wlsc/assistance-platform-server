package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserDeviceEvent;

public class Loudness extends SensorData {
	public float loudness;
	
	public Loudness() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(loudness);
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
		if (Float.floatToIntBits(loudness) != Float
				.floatToIntBits(other.loudness))
			return false;
		return true;
	}
}