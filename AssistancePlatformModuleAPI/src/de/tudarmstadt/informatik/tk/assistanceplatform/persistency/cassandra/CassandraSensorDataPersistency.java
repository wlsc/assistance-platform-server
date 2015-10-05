package de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.ISensorDataPersistency;

public class CassandraSensorDataPersistency implements ISensorDataPersistency {
	private Session cassandraSession;
	
	public CassandraSensorDataPersistency(Session sess) {
		this.cassandraSession = sess;
	}
	
	@Override
	public boolean pesist(SensorData data) {
		Mapper sensorMapper = new MappingManager(cassandraSession).mapper(data.getClass());
		
		sensorMapper.save((Object)data);
		
		return true;
	}
}