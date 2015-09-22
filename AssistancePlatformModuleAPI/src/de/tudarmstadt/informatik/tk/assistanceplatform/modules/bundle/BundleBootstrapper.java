package de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeper;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.IClientActionRunner;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest.ModuleBundleClientActionRunnerProxy;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest.RESTClientActionRunner;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClient;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.UserFilteredMessagingServiceDecorator;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;

/**
 * This class is responsible for bootstrapping a module bundle. 
 * Use its constructors in a main method to start the bundle.
 * @author bjeutter
 */
public class BundleBootstrapper {
	private static final String defaultPlatformUrlAndPort = "localhost:9000";
	
	/**
	 * Initalizes & starts the bundle
	 * @param bundle The bundle that shall be started
	 * @param platformUrlAndPort The url of the platform
	 */
	public static void bootstrap(ModuleBundle bundle, String platformUrlAndPort) {
		// Prepare Messaging Service and User Activation List
		MessagingService ms = new JmsMessagingService();

		UserActivationListKeeper activationListKeeper = new UserActivationListKeeper(
				ms);

		IUserActivationChecker activationChecker = activationListKeeper
				.getUserActivationChecker();

		ms = new UserFilteredMessagingServiceDecorator(ms, activationChecker);

		// Prepare Platform Client & Action Runner
		PlatformClient client = new PlatformClient("localhost:9000");
		IClientActionRunner actionRunner = new RESTClientActionRunner(client);
		actionRunner = new ModuleBundleClientActionRunnerProxy(bundle,
				actionRunner);

		// Finally start the bundle
		bundle.bootstrapBundle(ms, activationChecker, client, actionRunner);
	}
	
	/**
	 * Initalizes & starts the bundle (uses default platform url)
	 * @param bundle The bundle that shall be started
	 */
	public static void bootstrap(ModuleBundle bundle) {
		bootstrap(bundle, defaultPlatformUrlAndPort);
	}
}
