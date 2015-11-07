package de.tudarmstadt.informatik.tk.assistanceplatform.persistency;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserDeviceEvent;

/**
 * Describes the interface needed for persisting sensor / event data
 * @author bjeutter
 */
public interface IUserDeviceEventPersistency {
	public boolean persist(UserDeviceEvent data);
	
	public boolean persistMany(UserDeviceEvent[] data);
}