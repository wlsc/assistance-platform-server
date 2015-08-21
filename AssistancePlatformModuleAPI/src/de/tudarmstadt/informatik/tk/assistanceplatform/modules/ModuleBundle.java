package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import org.apache.log4j.Logger;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.exceptions.ModuleBundleInformationMissingException;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.IMessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;


/**
 * Module bundles are containers of together deployed modules. They bootstrap the module.
 * @author bjeutter
 *
 */
public abstract class ModuleBundle {
	private final Module containedModules[];
	
	private final IUserActivationChecker userActivationListChecker;
	
	private final String platformUrlAndPort;

	public ModuleBundle(String platformUrlAndPort, IMessagingService messagingService, IUserActivationChecker userActivationListChecker) {
		this.userActivationListChecker = userActivationListChecker;
		containedModules = initializeContainedModules(messagingService);
		
		this.platformUrlAndPort = platformUrlAndPort;
		
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
	 * Instantiate all used modules in this method and return them.
	 */
	protected abstract Module[] initializeContainedModules(IMessagingService messagingService);
}