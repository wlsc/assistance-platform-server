package de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle;

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
			String platformUrlAndPort, boolean localMode) {
		// Prepare Messaging Service and User Activation List
		ConfiguredJmsServiceFactory.createJmsInstance(bundle.getModuleId());
		MessagingService ms = ConfiguredJmsServiceFactory.getJmsInstance(); 
				
		UserActivationListKeeper activationsKeeper = UserActivationListKeeperFactory
				.createInstance(bundle.getModuleId(), ms, platformUrlAndPort);

		IUserActivationChecker activationChecker = activationsKeeper
				.getUserActivationChecker();

		ms = new UserFilteredMessagingServiceDecorator(ms, activationChecker);

		// Prepare Platform Client & Action Runner
		PlatformClient client = PlatformClientFactory
				.getInstance(platformUrlAndPort);
		IClientActionRunner actionRunner = new RESTClientActionRunner(client);
		actionRunner = new ModuleBundleClientActionRunnerProxy(bundle,
				actionRunner);

		// Spark Service
		ISparkService sparkService = createSparkService(bundle, localMode);

		// Finally start the bundle
		bundle.bootstrapBundle(ms, activationChecker, client, actionRunner,
				sparkService);
	}

	private static ISparkService createSparkService(ModuleBundle bundle,
			boolean localMode) {
		String appName = bundle.getBundleInformation().englishModuleBundleInformation.name;
		String master = localMode ? "local[*]" : sparkMasterURL(bundle);
		String[] jars = new String[] { System.getProperty("user.home") + "/"
				+ bundle.getModuleId() + ".jar" };
		ISparkService sparkService = new SparkService(bundle, appName, master,
				jars);

		return sparkService;
	}

	private static String sparkMasterURL(ModuleBundle bundle) {
		return PlatformClientFactory.getInstance().getServiceConfig(
				bundle.getModuleId(), "spark").address[0];
	}

	/**
	 * Initalizes & starts the bundle (uses default platform url)
	 * 
	 * @param bundle
	 *            The bundle that shall be started
	 */
	public static void bootstrap(ModuleBundle bundle) {
		bootstrap(bundle, PlatformClientFactory.defaultPlatformUrlAndPort,
				false);
	}
}
