package de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeper;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.IClientActionRunner;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest.ModuleBundleClientActionRunnerProxy;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest.RESTClientActionRunner;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark.ISparkService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark.SparkService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClient;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClientFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.UserFilteredMessagingServiceDecorator;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.UserActivationListFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.UserActivationList;

/**
 * This class is responsible for bootstrapping the module bundle and providing all needed services. 
 * Use its constructors in a main method to start the bundle.
 * @author bjeutter
 */
public class BundleBootstrapper {
	
	/**
	 * Initalizes & starts the bundle
	 * @param bundle The bundle that shall be started
	 * @param platformUrlAndPort The url of the platform
	 */
	public static void bootstrap(ModuleBundle bundle, String platformUrlAndPort, boolean localMode) {
		// Prepare Messaging Service and User Activation List
		MessagingService ms = new JmsMessagingService();
		// TODO: Fetch configuration from platform
		
		UserActivationListKeeper activationsKeeper = new UserActivationListKeeper(bundle.getModuleId(), ms);
		
		IUserActivationChecker activationChecker = activationsKeeper.getUserActivationChecker();

		ms = new UserFilteredMessagingServiceDecorator(ms, activationChecker);

		// Prepare Platform Client & Action Runner
		PlatformClient client = PlatformClientFactory.getInstance(platformUrlAndPort);
		IClientActionRunner actionRunner = new RESTClientActionRunner(client);
		actionRunner = new ModuleBundleClientActionRunnerProxy(bundle,
				actionRunner);
		
		// Spark Service
		ISparkService sparkService = createSparkService(bundle, localMode);

		// Finally start the bundle
		bundle.bootstrapBundle(ms, activationChecker, client, actionRunner, sparkService);
	}
	
	private static ISparkService createSparkService(ModuleBundle bundle, boolean localMode) {
		String appName = bundle.getBundleInformation().englishModuleBundleInformation.name;
		// TODO: Fetch Master information from platform
		// TODO: Add debug / locale mode
		String master = localMode ? "local[*]" : "spark://Bennets-MBP:7077"; 
		String[] jars = new String[] { System.getProperty("user.home") + "/" + bundle.getModuleId() + ".jar" };
		ISparkService sparkService = new SparkService(bundle.getModuleId(), appName, master, jars);
		
		return sparkService;
	}
	
	/**
	 * Initalizes & starts the bundle (uses default platform url)
	 * @param bundle The bundle that shall be started
	 */
	public static void bootstrap(ModuleBundle bundle) {
		bootstrap(bundle, PlatformClientFactory.defaultPlatformUrlAndPort, false);
	}
}
