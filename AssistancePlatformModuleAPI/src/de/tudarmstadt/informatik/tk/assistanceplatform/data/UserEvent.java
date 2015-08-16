package de.tudarmstadt.informatik.tk.assistanceplatform.data;

public abstract class UserEvent extends Event {
	public Long userId;
	
	public UserEvent() {
		
	}
	
	public UserEvent(long userId, long timestamp) {
		super(timestamp);
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	

}
