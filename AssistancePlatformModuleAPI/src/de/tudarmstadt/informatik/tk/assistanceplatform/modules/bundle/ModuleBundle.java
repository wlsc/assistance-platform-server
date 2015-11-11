package de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.DataModule;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Module;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.AssistanceModule;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.IClientActionRunner;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark.ISparkService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClient;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.IMessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.ModuleRestServerFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;

/**
 * Module bundles are containers of together deployed modules. They bootstrap
 * the module.
 * 
 * @author bjeutter
 *
 */
public abstract class ModuleBundle implements IModuleBundleIdProvider {
	private static ModuleBundle activeBundle;
	
	public static ModuleBundle currentBundle() {
		return activeBundle;
	}
	
	private Module containedModules[];

	private IUserActivationChecker userActivationListChecker;

	private IClientActionRunner actionRunner;

	private PlatformClient platformClient;

	private ISparkService sparkService;

	/**
	 * Starts the module and sets the required parameters
	 * 
	 * @param platformUrlAndPort
	 * @param messagingService
	 * @param userActivationListChecker
	 * @param actionRunner
	 */
	public void bootstrapBundle(IMessagingService messagingService,
			IUserActivationChecker userActivationListChecker,
			PlatformClient platformClient, IClientActionRunner actionRunner,
			ISparkService sparkService) {
		activeBundle = this;
		
		// Save references to all required compopnents
		this.actionRunner = actionRunner;
		this.userActivationListChecker = userActivationListChecker;
		this.platformClient = platformClient;
		this.sparkService = sparkService;

		// Initialize and run the contained modules
		this.containedModules = initializeContainedModules();
		this.startContainedModules(messagingService);
		registerBundle();
	}

	private void startContainedModules(IMessagingService messagingService) {
		ExecutorService executor = Executors.newCachedThreadPool();

		for (Module m : containedModules) {
			m.setMessagingService(messagingService);

			executor.submit(() -> {
				if (m instanceof AssistanceModule) {
					AssistanceModule assiModule = ((AssistanceModule) m);
					assiModule.setActionRunner(actionRunner);
					assiModule.setModuleIdProvider(this);
				} else if (m instanceof DataModule) {
					DataModule dataModule = ((DataModule) m);
					
					dataModule.setSparkService(sparkService);
				}

				m.start();
			});
		}
	}

	private void registerBundle() {
		ModuleBundleRegistrator registrator = new ModuleBundleRegistrator(this,
				platformClient);

		registrator.startPeriodicRegistration();
	}

	public IUserActivationChecker userActivationListChecker() {
		return userActivationListChecker;
	}
	
	public String getRestContactAddress() {
		int port = ModuleRestServerFactory.getInstance().getPort();
		return Integer.toString(port); // Lets just use the port so the receiver
										// can resolve the correct IP address
	}

	/**
	 * Implement this and return a unique ID for this Module(bundle) - a good
	 * candidate is the package name.
	 * 
	 * @return
	 */
	@Override
	public abstract String getModuleId();

	/**
	 * Implement this and return a object which describes the meta data of this
	 * bundle.
	 * 
	 * @return
	 */
	public abstract ModuleBundleInformation getBundleInformation();

	/**
	 * Instantiate all used modules in this method and return them. NEVER! run
	 * them.
	 */
	protected abstract Module[] initializeContainedModules();
}