package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.IClientActionRunner;


/**
 * Assistance Modules are the ones that interact with the user, give recommendations etc.
 * @author bjeutter
 *
 */
public abstract class AssistanceModule extends Module {
	// Dieses Assistenzmodule soll nun auf verschiedene Data Module, bzw. deren Kontext, zugreifen können
	// WIe Typisierung gestalten?
	
	private IClientActionRunner actionRunner;
	
	public void setActionRunner(IClientActionRunner actionRunner) {
		this.actionRunner = actionRunner;
	}
	
	protected IClientActionRunner getActionRunner() {
		return actionRunner;
	}
}