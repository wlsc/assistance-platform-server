package de.tudarmstadt.informatik.tk.assistanceplatform.data;

import java.time.Instant;
import java.util.UUID;

import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.annotations.ClusteringColumn;


public abstract class Event {
	public UUID id;
	
	@ClusteringColumn
	public long timestamp;
	
	public Event() {
		if(id == null) {
			id = UUIDs.random();
		}
	}
	
	public Event(long timestamp) {
		this.id = UUIDs.random();
		this.timestamp = timestamp;
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Instant getTimestampAsInstant() {
		return Instant.ofEpochMilli(timestamp);
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
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
