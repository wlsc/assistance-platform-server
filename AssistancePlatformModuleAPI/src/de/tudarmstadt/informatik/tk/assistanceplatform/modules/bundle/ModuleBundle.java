package de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.DataModule;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Module;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.AssistanceModule;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.exceptions.ModuleBundleInformationMissingException;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.IClientActionRunner;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark.ISparkService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClient;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.IMessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;


/**
 * Module bundles are containers of together deployed modules. They bootstrap the module.
 * @author bjeutter
 *
 */
public abstract class ModuleBundle {
	private Module containedModules[];
	
	private IUserActivationChecker userActivationListChecker;
	
	private IClientActionRunner actionRunner;
	
	private PlatformClient platformClient;
	
	private ISparkService sparkService;

	/**
	 * Starts the module and sets the required parameters
	 * @param platformUrlAndPort
	 * @param messagingService
	 * @param userActivationListChecker
	 * @param actionRunner
	 */
	public void bootstrapBundle(IMessagingService messagingService, IUserActivationChecker userActivationListChecker, PlatformClient platformClient, IClientActionRunner actionRunner, ISparkService sparkService) {
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
		
		for(Module m : containedModules) {
			m.setMessagingService(messagingService);
			
			executor.submit(() -> { 
				if(m instanceof AssistanceModule) {
					((AssistanceModule) m).setActionRunner(actionRunner);
				} else if(m instanceof DataModule) {
					((DataModule) m).setSparkService(sparkService);
				}
				
				m.start();
			});
		}
	}
	
	private void registerBundle() {
		ModuleBundleRegistrator registrator = new ModuleBundleRegistrator(this, platformClient);
		
		try {
			registrator.registerBundleForUsage(true);
		} catch (ModuleBundleInformationMissingException e) {
			Logger.getLogger(ModuleBundle.class).error("An error occured on module registration with assistance platform", e);
		}
		
		registrator.startPeriodicRegistration();
	}


	public IUserActivationChecker userActivationListChecker() {
		return userActivationListChecker;
	}
	
	/**
	 * Implement this and return a unique ID for this Module(bundle) - a good candidate is the package name.
	 * @return
	 */
	public abstract String getModuleId();
	
	/**
	 * Implement this and return a object which describes the meta data of this bundle.
	 * @return
	 */
	public abstract ModuleBundleInformation getBundleInformation();
	
	/**
	 * Instantiate all used modules in this method and return them. NEVER! run them.
	 */
	protected abstract Module[] initializeContainedModules();
}