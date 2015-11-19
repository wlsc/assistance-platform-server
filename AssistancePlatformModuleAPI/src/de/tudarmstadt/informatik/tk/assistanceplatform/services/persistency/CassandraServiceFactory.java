package de.tudarmstadt.informatik.tk.assistanceplatform.services.persistency;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.ModuleBundle;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClientFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.CassandraServiceConfigResponse;

/**
 * Modules can use this class to get access to the Cassandra Database.
 * @author bjeutter
 *
 */
public class CassandraServiceFactory {
	private static CassandraSessionProxy proxyInstance;

	public static CassandraSessionProxy getSessionProxy() {
		if (proxyInstance == null) {
			createProxyInstance();
		}

		return proxyInstance;
	}

	private static void createProxyInstance() {

		CassandraServiceConfigResponse config = PlatformClientFactory.getInstance().getDatabaseService(
				ModuleBundle.currentBundle().getModuleId());

		List<String> adresses = new LinkedList<String>( Arrays.asList(config.address) );
		adresses.add(PlatformClientFactory.getInstance().getUsedHostWithoutPort()); // Workaround if module started outside platform machine

		InetAddress[] contactPoints = adresses.stream().map((s) -> {
			try {
				return InetAddress.getByName(s);
			} catch (Exception e) {
			}
			return null;
		}).toArray(InetAddress[]::new);
		
		proxyInstance = new CassandraSessionProxy(contactPoints, config.keystoreName, config.user, config.password);
	}
}
