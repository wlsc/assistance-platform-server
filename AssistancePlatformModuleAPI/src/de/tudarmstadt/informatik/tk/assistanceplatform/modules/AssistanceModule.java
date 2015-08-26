package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.IMessagingService;

/**
 * Assistance Modules are the ones that interact with the user, give recommendations etc.
 * @author bjeutter
 *
 */
public abstract class AssistanceModule extends Module {
	// Dieses Assistenzmodule soll nun auf verschiedene Data Module, bzw. deren Kontext, zugreifen können
	// WIe Typisierung gestalten?
	
	public AssistanceModule() {
		super();
	}
}