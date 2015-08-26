package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.exceptions.ModuleBundleInformationMissingException;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.IClientActionRunner;
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
	
	private String platformUrlAndPort;
	
	private IClientActionRunner actionRunner;

	/**
	 * Starts the module and sets the required parameters
	 * @param platformUrlAndPort
	 * @param messagingService
	 * @param userActivationListChecker
	 * @param actionRunner
	 */
	public void bootstrapBundle(String platformUrlAndPort, IMessagingService messagingService, IUserActivationChecker userActivationListChecker, IClientActionRunner actionRunner) {
		this.platformUrlAndPort = platformUrlAndPort;
		
		this.actionRunner = actionRunner;
		
		this.userActivationListChecker = userActivationListChecker;
		
		this.containedModules = initializeContainedModules();
		
		this.startContainedModules(messagingService);
		
		registerBundle();
	}
	
	private void startContainedModules(IMessagingService messagingService) {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for(Module m : containedModules) {
			executor.submit(() -> { 
				if(m instanceof AssistanceModule) {
					((AssistanceModule) m).setActionRunner(actionRunner);
				}
				
				m.start(messagingService);
			});
		}
	}
	
	private void registerBundle() {
		ModuleBundleRegistrator registrator = new ModuleBundleRegistrator(this);
		
		try {
			registrator.registerBundleForUsage(true);
		} catch (ModuleBundleInformationMissingException e) {
			Logger.getLogger(ModuleBundle.class).error("An error occured on module registration with assistance platform", e);
		}
		
		registrator.startPeriodicRegistration();
	}
	
	public String getPlatformUrlAndPort() {
		return platformUrlAndPort;
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