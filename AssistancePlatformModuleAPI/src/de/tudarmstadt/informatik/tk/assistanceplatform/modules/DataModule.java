package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import java.security.PrivilegedActionException;

import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.IUserDeviceEventPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark.ISparkService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.persistency.CassandraServiceFactory;

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
	
	public final void setSparkService(ISparkService service) {
		this.sparkService = service;
	}
	
	public final ISparkService getSparkService() {
		return sparkService;
	}

	public final IUserDeviceEventPersistency getEventPersistency() {
		if(eventPersistency == null) {
			eventPersistency = new CassandraSensorDataPersistency(CassandraServiceFactory.getSessionProxy());
		}
		
		return eventPersistency;
	}

	public final void setEventPersistency(IUserDeviceEventPersistency eventPersistency) {
		this.eventPersistency = eventPersistency;
	}
}