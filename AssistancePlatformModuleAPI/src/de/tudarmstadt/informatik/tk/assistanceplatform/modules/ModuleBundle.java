package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;


/**
 * Module bundles are containers of together deployed modules. They bootstrap the module.
 * @author bjeutter
 *
 */
public abstract class ModuleBundle {
	private Module containedModules[];
	
	private MessagingService messagingService;
	
	public ModuleBundle(MessagingService messagingService) {
		containedModules = initializeContainedModules(messagingService);
	}
	
	protected abstract Module[] initializeContainedModules(MessagingService messagingService);
	
	/*public void registerModules(ModuleRegistry registry) {
		if(containedModules != null) {
			for(Module m : containedModules) {
				//ModuleProxy proxy
				//ModuleRegistration registration = new ModuleRegistration(proxy);
				//registry.registerModule(registration);
			}
		}
	}*/
}