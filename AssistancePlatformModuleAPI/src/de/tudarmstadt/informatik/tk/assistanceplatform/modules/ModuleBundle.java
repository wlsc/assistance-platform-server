package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.exceptions.ModuleBundleInformationMissingException;
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
			try {
				registerBundleForUsage();
			} catch (Exception e) {
				Logger.getLogger(ModuleBundle.class).error("An error occured on module registration with assistance platform", e);
				
				// TODO: Error via LOG4J ausgeben
				
				scheduler.shutdownNow();
			}
		}, 0, 15, TimeUnit.MINUTES);
	}
	
	private void registerBundleForUsage() throws ModuleBundleInformationMissingException {
		ModuleBundleInformation bundleInfo = getBundleInformation();
		
		if(bundleInfo == null) {
			throw new ModuleBundleInformationMissingException("getBundleInformation() has to be properly implemented.");
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