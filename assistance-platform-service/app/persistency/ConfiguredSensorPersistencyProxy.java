package persistency;

import java.util.List;

import com.typesafe.config.ConfigFactory;

import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.ISensorDataPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;;

public class ConfiguredSensorPersistencyProxy {
	private ISensorDataPersistency sensorDataPersistency;
	
	public ConfiguredSensorPersistencyProxy() {
		CassandraSessionProxy sessionProxy = new CassandraSessionProxy(getContactPoints(), getKeystoreName());
		
		sensorDataPersistency = new CassandraSensorDataPersistency(sessionProxy);
	}
	
	private String[] getContactPoints() {
		List<String> contactPoints = ConfigFactory.defaultApplication().getStringList("cassandra.contactPoints");
		
		return contactPoints.toArray(new String[contactPoints.size()]);
	}
	
	private String getKeystoreName() {
		return ConfigFactory.defaultApplication().getString("cassandra.keystoreName");
	}
	
	public ISensorDataPersistency getSensorDataPersistency() {
		return sensorDataPersistency;
	}
}