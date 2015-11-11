package persistency.config;

import java.net.InetAddress;
import java.util.List;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class CassandraConfig {
	public static String getUser() {
		return ConfigFactory.defaultApplication().getString("cassandra.user");
	}
	
	public static String getPassword() {
		return ConfigFactory.defaultApplication().getString("cassandra.password");
	}
	
	public static List<String> getContactPoints() {
		Config c = ConfigFactory.defaultApplication().resolve();

		return c.getStringList("cassandra.contactPoints");
	}
	
	public static String[] getContactPointsArray() {
		return getContactPoints().stream().toArray(String[]::new);
	}

	public static InetAddress[] getContactPointsAsAddr() {
		List<String> contactPoints = getContactPoints();

		return contactPoints.stream().map((s) -> {
			try {
				return InetAddress.getByName(s);
			} catch (Exception e) {
			}
			return null;
		}).toArray(size -> new InetAddress[size]);
	}
}
