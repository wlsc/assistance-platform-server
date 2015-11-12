package persistency.cassandra;

import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.IUserDeviceEventPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;

public class ConfiguredSensorPersistencyProxy {
	private IUserDeviceEventPersistency sensorDataPersistency;

	public ConfiguredSensorPersistencyProxy() {
		sensorDataPersistency = new CassandraSensorDataPersistency(CassandraSessionProxyFactory.getSessionProxy());
	}

	public IUserDeviceEventPersistency getSensorDataPersistency() {
		return sensorDataPersistency;
	}
}