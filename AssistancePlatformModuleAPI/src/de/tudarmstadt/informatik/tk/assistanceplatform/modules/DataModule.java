package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import java.security.PrivilegedActionException;

import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.IUserDeviceEventPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark.ISparkService;

/**
 * DataModules are responsible for generating / aggregating higher-class data from the "normal" data of the platform.
 * This class should be sub-classed to process the data provided by the platform.
 * @author bjeutter
 *
 */
public abstract class DataModule extends Module {
	private ISparkService sparkService;
	
	private IUserDeviceEventPersistency eventPersistency;
	
	public DataModule() {
		super();
	}
	
	@Override
	protected final void internalDoBeforeStartup() {
	}
	
	public void setSparkService(ISparkService service) {
		this.sparkService = service;
	}
	
	public ISparkService getSparkService() {
		return sparkService;
	}

	public IUserDeviceEventPersistency getEventPersistency() {
		return eventPersistency;
	}

	public void setEventPersistency(IUserDeviceEventPersistency eventPersistency) {
		this.eventPersistency = eventPersistency;
	}
}