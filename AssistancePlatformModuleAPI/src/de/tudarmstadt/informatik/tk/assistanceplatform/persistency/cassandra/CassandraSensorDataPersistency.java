package de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.ISensorDataPersistency;

/**
 * Implements the sensor data persistency for the explicit Cassandra datatstore
 * @author bjeutter
 *
 */
public class CassandraSensorDataPersistency implements ISensorDataPersistency {
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
	public boolean persist(SensorData data) {
		Statement s = createSaveStatement(data);
		
		cassandraSession.execute(s); 
		
		return true;
	}
	
	@Override
	public boolean persistMany(SensorData[] data) {
		for(SensorData d : data) {
			persist(d);
		}
		
		return true;
	}
	
	private <T extends SensorData> Statement createSaveStatement(T data) {
		Mapper<T> sensorMapper = mappingManager.mapper((Class<T>)data.getClass());
		
		Statement saveStatement = sensorMapper.saveQuery(data);
		
		return saveStatement;
	}
}