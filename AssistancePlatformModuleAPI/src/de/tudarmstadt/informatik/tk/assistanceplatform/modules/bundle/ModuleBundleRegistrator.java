package de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.exceptions.ModuleBundleInformationMissingException;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClient;

class ModuleBundleRegistrator {
	private final ModuleBundle bundle;
	
	private PlatformClient client;
	
	public ModuleBundleRegistrator(ModuleBundle bundle, PlatformClient platformClient) {
		this.bundle = bundle;
		this.client = platformClient;
	}
	
	public void startPeriodicRegistration() {
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
	
	public void registerBundleForUsage(boolean startupRequest) throws ModuleBundleInformationMissingException {
		ModuleBundleInformation bundleInfo = bundle.getBundleInformation();
		
		if(bundleInfo == null) {
			throw new ModuleBundleInformationMissingException("getBundleInformation() has to be properly implemented.");
		}
		
		Consumer<Void> onSuccess = (v) -> {
			client.localizeModule(bundle, (v2) -> {
				Logger.getLogger(ModuleBundle.class).error("Failed to localize module bundle.");
			});
		};
		
		if(startupRequest) {
			client.registerModule(bundle, onSuccess, (v) -> {
				Logger.getLogger(ModuleBundle.class).error("Failed to register module bundle. Shutting down.");
				System.exit(-1);
			}, true);
		} else {
			client.updateModule(bundle, onSuccess, (v) -> {
				Logger.getLogger(ModuleBundle.class).error("Failed to update module bundle (keep alive). Shutting down.");
				System.exit(-1);
			});	
		}
	}
}
