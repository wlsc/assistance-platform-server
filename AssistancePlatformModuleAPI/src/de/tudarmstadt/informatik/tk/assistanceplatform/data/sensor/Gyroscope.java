package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;


public class Gyroscope extends SensorData {
	public Double x;
    public Double y;
    public Double z;
    
	public Gyroscope() {
		super();
	}
	
	public Gyroscope(long userId, long deviceId, long timestamp, double x, double y, double z) {
		super(userId, deviceId, timestamp);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		result = prime * result + ((z == null) ? 0 : z.hashCode());
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
		Gyroscope other = (Gyroscope) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		if (z == null) {
			if (other.z != null)
				return false;
		} else if (!z.equals(other.z))
			return false;
		return true;
	}
}