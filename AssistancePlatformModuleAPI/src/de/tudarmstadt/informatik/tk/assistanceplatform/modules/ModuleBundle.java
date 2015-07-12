package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.UserActivationList;


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
	}
	
	public IUserActivationChecker getUserActivationListChecker() {
		return userActivationListChecker;
	}
	
	protected abstract Module[] initializeContainedModules(MessagingService messagingService);
}