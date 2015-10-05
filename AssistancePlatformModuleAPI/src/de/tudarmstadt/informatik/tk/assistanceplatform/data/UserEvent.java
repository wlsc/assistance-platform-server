package de.tudarmstadt.informatik.tk.assistanceplatform.data;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;


public abstract class UserEvent extends Event {
	@PartitionKey(0)
	@Column(name = "user_id")
	public long userId;
	
	public UserEvent() {
	}
	
	public UserEvent(long userId, long timestamp) {
		super(timestamp);
		this.userId = userId;
	}
	
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (userId ^ (userId >>> 32));
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
		UserEvent other = (UserEvent) obj;
		if (userId != other.userId)
			return false;
		return true;
	}
}
