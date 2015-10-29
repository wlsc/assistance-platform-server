package persistency;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.ISensorDataPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;

public class ConfiguredSensorPersistencyProxy {
	private ISensorDataPersistency sensorDataPersistency;

	public ConfiguredSensorPersistencyProxy() {

		CassandraSessionProxy sessionProxy = new CassandraSessionProxy(
				getContactPoints(), getKeystoreName());

		sensorDataPersistency = new CassandraSensorDataPersistency(sessionProxy);
	}

	private InetAddress[] getContactPoints() {
		List<String> contactPoints = null;

		Config c = ConfigFactory.defaultApplication().resolve();

		contactPoints = c.getStringList("cassandra.contactPoints");

		return contactPoints.stream().map((s) -> {
			try {
				return InetAddress.getByName(s);
			} catch (Exception e) {
			}
			return null;
		}).toArray(size -> new InetAddress[size]);
	}

	private String getKeystoreName() {
		return ConfigFactory.defaultApplication().getString(
				"cassandra.keystoreName");
	}

	public ISensorDataPersistency getSensorDataPersistency() {
		return sensorDataPersistency;
	}
}