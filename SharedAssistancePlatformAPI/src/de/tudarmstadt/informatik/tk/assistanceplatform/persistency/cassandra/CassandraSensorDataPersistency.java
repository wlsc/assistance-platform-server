package de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra;

import java.util.Date;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserDeviceEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.IUserDeviceEventPersistency;

/**
 * Implements the sensor data persistency for the explicit Cassandra datatstore
 * @author bjeutter
 *
 */
public class CassandraSensorDataPersistency implements IUserDeviceEventPersistency {
	private Session cassandraSession;
	
	private MappingManager mappingManager;
	
	public CassandraSensorDataPersistency(CassandraSessionProxy prox) {
		this(prox.getSession());
	}
	
	public CassandraSensorDataPersistency(Session sess) {
		this.cassandraSession = sess;
		
		this.mappingManager = new MappingManager(cassandraSession);
	}
	
	@Override
	public boolean persist(UserDeviceEvent data) {
		data.setServerTimestamp(new Date());
		
		Statement s = createSaveStatement(data);
		
		cassandraSession.execute(s); 
		
		return true;
	}
	
	@Override
	public boolean persistMany(UserDeviceEvent[] data) {
		for(UserDeviceEvent d : data) {
			d.setServerTimestamp(new Date());
			persist(d);
		}
		
		return true;
	}
	
	private <T extends UserDeviceEvent> Statement createSaveStatement(T data) {
		Mapper<T> sensorMapper = mappingManager.mapper((Class<T>)data.getClass());
		
		Statement saveStatement = sensorMapper.saveQuery(data);
		
		return saveStatement;
	}
}