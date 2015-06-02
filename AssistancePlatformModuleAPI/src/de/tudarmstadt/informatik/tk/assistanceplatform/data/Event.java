package de.tudarmstadt.informatik.tk.assistanceplatform.data;


public abstract class Event {
	public final Long timestamp;
	
	public Event(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int hash = super.hashCode();
		hash = timestamp.hashCode();
		
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof Event)) {
			return false;
		}
		return timestamp.equals( ((Event)obj).timestamp);
	}
}
