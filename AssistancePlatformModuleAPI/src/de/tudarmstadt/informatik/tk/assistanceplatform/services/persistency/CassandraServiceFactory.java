package de.tudarmstadt.informatik.tk.assistanceplatform.services.persistency;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.ModuleBundle;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClientFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.ServiceConfigResponse;

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
		ServiceConfigResponse config = PlatformClientFactory.getInstance().getDatabaseService(
				ModuleBundle.currentBundle().getModuleId());
		
		//proxyInstance = new CassandraSessionProxy(contactPoints, keystoreName, user, password)
	}
}
