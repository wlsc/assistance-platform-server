package de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle;

import java.util.Map;

import org.apache.log4j.Logger;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeper;
import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeperFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.IClientActionRunner;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest.ModuleBundleClientActionRunnerProxy;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest.RESTClientActionRunner;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark.ISparkService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark.SparkService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClient;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClientFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.ConfiguredJmsServiceFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.UserFilteredMessagingServiceDecorator;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.ModuleRestServer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.ModuleRestServerFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;

/**
 * This class is responsible for bootstrapping the module bundle and providing
 * all needed services. Use its constructors in a main method to start the
 * bundle.
 * 
 * @author bjeutter
 */
public class BundleBootstrapper {

	/**
	 * Initalizes & starts the bundle
	 * 
	 * @param bundle
	 *            The bundle that shall be started
	 * @param platformUrlAndPort
	 *            The url of the platform
	 */
	public static void bootstrap(ModuleBundle bundle,
			int restPort, String platformUrlAndPort, boolean localMode) {
		// Prepare Assistance Rest Server
		ModuleRestServer server = ModuleRestServerFactory.createInstance(restPort);
		
		// Prepare platform client
		PlatformClient client = PlatformClientFactory
				.createInstance(platformUrlAndPort);
		
		registerBundle(client, bundle);
	
		
		// Prepare Messaging Service and User Activation List
		MessagingService ms = createBasicMessagingService(bundle);
				
		UserActivationListKeeper activationsKeeper = UserActivationListKeeperFactory
				.createInstance(bundle.getModuleId(), ms, platformUrlAndPort);

		IUserActivationChecker activationChecker = activationsKeeper
				.getUserActivationChecker();
		
		ms = new UserFilteredMessagingServiceDecorator(ms, activationChecker);

		// Prepare Action runner
		IClientActionRunner actionRunner = new RESTClientActionRunner(client);
		actionRunner = new ModuleBundleClientActionRunnerProxy(bundle,
				actionRunner);

		// Spark Service
		ISparkService sparkService = createSparkService(bundle, localMode);

		// Finally start the bundle
		bundle.bootstrapBundle(ms, activationChecker, client, actionRunner,
				sparkService);
	}
	
	private static void registerBundle(PlatformClient platformClient, ModuleBundle bundle) {
		ModuleBundleRegistrator registrator = new ModuleBundleRegistrator(bundle,
				platformClient);

		registrator.startPeriodicRegistration();
		
		Logger.getLogger(BundleBootstrapper.class).warn("Waiting 10secs to propagate module registration.");
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < 10000);
	}

	private static MessagingService createBasicMessagingService(ModuleBundle bundle) {
		ConfiguredJmsServiceFactory.createJmsInstance(bundle.getModuleId());
		MessagingService ms = ConfiguredJmsServiceFactory.getJmsInstance(); 
		
		return ms;
	}

	private static ISparkService createSparkService(ModuleBundle bundle,
			boolean localMode) {
		String appName = bundle.getBundleInformation().englishModuleBundleInformation.name;
		String master = localMode ? "local[*]" : sparkMasterURL(bundle);
		
		String defaultJarPath = System.getProperty("user.home") + "/"
				+ bundle.getModuleId() + ".jar";
		
		String envJarPath = findModuleJarByEnv();
		
		String[] jars = new String[] { defaultJarPath, envJarPath };
		ISparkService sparkService = new SparkService(bundle, appName, master,
				jars);

		return sparkService;
	}

	private static String sparkMasterURL(ModuleBundle bundle) {
		return PlatformClientFactory.getInstance().getServiceConfig(
				bundle.getModuleId(), "spark").address[0];
	}
	
	private static String findModuleJarByEnv() {
		Map<String, String> env = System.getenv();
		
		String key = "MODULE_FAT_JAR_PATH";
		
		if(env.containsKey(key)) {
			return env.get(key);
		}
		
		return null;
	}

	/**
	 * Initalizes & starts the bundle (uses default platform url)
	 * 
	 * @param bundle
	 *            The bundle that shall be started
	 */
	public static void bootstrap(ModuleBundle bundle) {
		bootstrap(bundle, ModuleRestServer.DEFAULT_PORT,  PlatformClientFactory.defaultPlatformUrlAndPort,
				false);
	}
}
