package de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra;

import java.util.Arrays;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.ResultSet;
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
	
	public CassandraSensorDataPersistency(CassandraSessionProxy prox) {
		this.cassandraSession = prox.getSession();
	}
	
	public CassandraSensorDataPersistency(Session sess) {
		this.cassandraSession = sess;
	}
	
	@Override
	public boolean pesist(SensorData data) {
		Statement s = createSaveStatement(data);
		
		cassandraSession.executeAsync(s); 
		
		return true;
	}
	
	@Override
	public boolean persistMany(SensorData[] data) {
		BatchStatement batch = new BatchStatement();
		
		for(SensorData d : data) {
			batch.add(createSaveStatement(d));
		}
		
		cassandraSession.execute(batch);
		
		return true;
	}
	
	private Statement createSaveStatement(SensorData data) {
		Mapper sensorMapper = new MappingManager(cassandraSession).mapper(data.getClass());
		
		Object castedData = (Object)data;
		
		Statement saveStatement = sensorMapper.saveQuery(castedData);
		
		return saveStatement;
	}
}