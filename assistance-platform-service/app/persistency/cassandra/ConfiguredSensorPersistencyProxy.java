package persistency.cassandra;

import play.Logger;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.IUserDeviceEventPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;

public class ConfiguredSensorPersistencyProxy {
	private IUserDeviceEventPersistency sensorDataPersistency;

	public ConfiguredSensorPersistencyProxy() {
		Logger.info("Start delay (30 sec) for cassandra ...");
		try {
			Thread.sleep(30 * 1000);
		} catch (InterruptedException e) {
			Logger.error("Thread sleep failed on waiting for cassandra", e);
		}
		Logger.info("Finished heavy delay for cassandra.");
		
		sensorDataPersistency = new CassandraSensorDataPersistency(CassandraSessionProxyFactory.getSessionProxy());
	}

	public IUserDeviceEventPersistency getSensorDataPersistency() {
		return sensorDataPersistency;
	}
}