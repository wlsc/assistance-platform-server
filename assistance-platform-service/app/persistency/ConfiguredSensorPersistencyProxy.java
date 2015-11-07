package persistency;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import play.Play;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.IUserDeviceEventPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSensorDataPersistency;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;

public class ConfiguredSensorPersistencyProxy {
	private IUserDeviceEventPersistency sensorDataPersistency;

	public ConfiguredSensorPersistencyProxy() {

		CassandraSessionProxy sessionProxy = new CassandraSessionProxy(
				getContactPoints(), getKeystoreName(), getUser(),
				getPassword(), getSchemaCQL());

		sensorDataPersistency = new CassandraSensorDataPersistency(sessionProxy);
	}
	
	private String getUser() {
		return ConfigFactory.defaultApplication().getString("cassandra.user");
	}
	
	private String getPassword() {
		return ConfigFactory.defaultApplication().getString("cassandra.user");
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

	private String getSchemaCQL() {
		Path evolutionPath = Play.application()
				.getFile("conf/CassandraEvolutions/1.cql").toPath();
		String schemaCQL = "";
		try {
			schemaCQL = new String(Files.readAllBytes(evolutionPath)).replace(
					"\n", "");
		} catch (IOException e) {
		}
		return schemaCQL;
	}

	public IUserDeviceEventPersistency getSensorDataPersistency() {
		return sensorDataPersistency;
	}
}