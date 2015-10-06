package persistency;

import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.ISensorDataPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;;

public class ConfiguredSensorPersistencyProxy {
	private ISensorDataPersistency sensorDataPersistency;
	
	public ConfiguredSensorPersistencyProxy() {
		// TODO: Parameter aus Config ziehen
		CassandraSessionProxy sessionProxy = new CassandraSessionProxy(new String[] { "127.0.0.1" }, "assistancedata");
		
		sensorDataPersistency = new CassandraSensorDataPersistency(sessionProxy);
	}
	
	public ISensorDataPersistency getSensorDataPersistency() {
		return sensorDataPersistency;
	}
}