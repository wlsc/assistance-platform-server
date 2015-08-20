package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.exceptions.ModuleBundleInformationMissingException;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClient;
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
	
	private String platformUrlAndPort;

	public ModuleBundle(String platformUrlAndPort, MessagingService messagingService, IUserActivationChecker userActivationListChecker) {
		this.userActivationListChecker = userActivationListChecker;
		containedModules = initializeContainedModules(messagingService);
		
		this.platformUrlAndPort = platformUrlAndPort;
		
		try {
			registerBundleForUsage(true);
		} catch (ModuleBundleInformationMissingException e) {
			Logger.getLogger(ModuleBundle.class).error("An error occured on module registration with assistance platform", e);
		}
		
		startPeriodicRegistration();
	}

	private void startPeriodicRegistration() {
		long minutesToWaitForUpdate = 15;
		
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> {
			try {
				registerBundleForUsage(false);
			} catch (Exception e) {
				Logger.getLogger(ModuleBundle.class).error("An error occured on module registration with assistance platform", e);
				
				scheduler.shutdownNow();
			}
		}, minutesToWaitForUpdate, minutesToWaitForUpdate, TimeUnit.MINUTES);
	}
	
	private void registerBundleForUsage(boolean startupRequest) throws ModuleBundleInformationMissingException {
		ModuleBundleInformation bundleInfo = getBundleInformation();
		
		if(bundleInfo == null) {
			throw new ModuleBundleInformationMissingException("getBundleInformation() has to be properly implemented.");
		}
		
		PlatformClient client = new PlatformClient(platformUrlAndPort);
		
		if(startupRequest) {
			client.registerModule(this, (v) -> {
				Logger.getLogger(ModuleBundle.class).error("Failed to register module bundle. Shutting down.");
				System.exit(-1);
			}, true);
		} else {
			client.updateModule(this, (v) -> {
				Logger.getLogger(ModuleBundle.class).error("Failed to update module bundle (keep alive). Shutting down.");
				System.exit(-1);
			});	
		}
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
	protected abstract Module[] initializeContainedModules(MessagingService messagingService);
}