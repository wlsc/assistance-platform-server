package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;


/**
 * Module bundles are containers of together deployed modules. They bootstrap the module.
 * @author bjeutter
 *
 */
public abstract class ModuleBundle {
	private Module containedModules[];
	
	private IUserActivationChecker userActivationListChecker;

	public ModuleBundle(MessagingService messagingService, IUserActivationChecker userActivationListChecker) {
		this.userActivationListChecker = userActivationListChecker;
		containedModules = initializeContainedModules(messagingService);
		
		startPeriodicRegistration();
	}

	private void startPeriodicRegistration() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> {
			registerBundleForUsage();
		}, 0, 15, TimeUnit.MINUTES);
	}
	
	private void registerBundleForUsage() {
		ModuleBundleInformation bundleInfo = getBundleInformation();
		
		if(bundleInfo == null) {
			//throw new Exception("getBundleInformation() has to be properly implemented.");
			return;
		}
		
		// TODO: REST API Anfrage an Platform schicken
	}
	
	public IUserActivationChecker userActivationListChecker() {
		return userActivationListChecker;
	}
	
	/**
	 * Implement this and return a unique ID for this Module(bundle) - a good candidate is the package name.
	 * @return
	 */
	protected abstract String getModuleId();
	
	/**
	 * Implement this and return a object which describes the meta data of this bundle.
	 * @return
	 */
	protected abstract ModuleBundleInformation getBundleInformation();
	
	/**
	 * Instantiate all used modules in this method and return them.
	 */
	protected abstract Module[] initializeContainedModules(MessagingService messagingService);
}