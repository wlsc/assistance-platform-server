package persistency.cassandra;

import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.IUserDeviceEventPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;

public class ConfiguredSensorPersistencyProxy {
    private IUserDeviceEventPersistency sensorDataPersistency;

    public IUserDeviceEventPersistency getSensorDataPersistency() {
        if (sensorDataPersistency == null) {
            sensorDataPersistency = new CassandraSensorDataPersistency(CassandraSessionProxyFactory.getSessionProxy());
        }
        return sensorDataPersistency;
    }
}