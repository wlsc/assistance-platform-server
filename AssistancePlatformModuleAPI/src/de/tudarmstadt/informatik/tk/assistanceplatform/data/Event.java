package de.tudarmstadt.informatik.tk.assistanceplatform.data;

import java.time.Instant;


public abstract class Event {
	public long timestamp;
	
	public Event() {
		
	}
	
	public Event(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public Instant getTimestampAsInstant() {
		return Instant.ofEpochMilli(timestamp);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}
	
}
