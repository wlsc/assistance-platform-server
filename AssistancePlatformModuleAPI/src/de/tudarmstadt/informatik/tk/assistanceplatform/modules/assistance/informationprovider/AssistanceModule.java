package de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Module;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.IClientActionRunner;


/**
 * Assistance Modules are the ones that interact with the user, give recommendations etc..
 * This class should be sub-classed and then implement the desired Assistance Logic.
 * @author bjeutter
 *
 */
public abstract class AssistanceModule extends Module {
	private IClientActionRunner actionRunner;
	
	private InformationProvider informationProvider;
	
	public void setActionRunner(IClientActionRunner actionRunner) {
		this.actionRunner = actionRunner;
	}
	
	/**
	 * Gets the action runner which is required to perform actions on the client devices, like sending messages etc.
	 * @return
	 */
	protected IClientActionRunner getActionRunner() {
		return actionRunner;
	}
	
	public InformationProvider getInformationProvider() {
		if(informationProvider == null) {
			informationProvider = generateInformationProvider();
		}
		
		return informationProvider;
	}

	/**
	 * If you want to provide "pull" based "current" module information to the user, then implement this class by instantiating your InformationProvider.
	 * If you don't plan such a feature, then just return null. 
	 * @return
	 */
	public abstract InformationProvider generateInformationProvider();
}