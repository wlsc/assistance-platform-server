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
		// TODO Auto-generated method stub
		int hash = super.hashCode();
		hash = 89 * hash + Long.valueOf(userId).hashCode();
		
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof UserEvent)) {
			return false;
		}
		
		return super.equals(obj) && userId.equals(((UserEvent)obj).userId);
	}
}
