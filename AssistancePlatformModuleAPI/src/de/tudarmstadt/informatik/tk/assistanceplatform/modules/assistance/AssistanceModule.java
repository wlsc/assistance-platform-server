package de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance;

import java.util.Collection;

import org.apache.log4j.Logger;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Module;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.InformationProvider;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.IClientActionRunner;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.ModuleRestServer;


/**
 * Assistance Modules are the ones that interact with the user, give recommendations etc..
 * This class should be sub-classed and then implement the desired Assistance Logic.
 * @author bjeutter
 *
 */
public abstract class AssistanceModule extends Module {
	private IClientActionRunner actionRunner;
	
	private InformationProvider informationProvider;
	
	@Override
	protected final void internalDoBeforeStartup() {
		try {
			ModuleRestServer server = new ModuleRestServer(generateCustomServelets());
			server.start();
		} catch (Exception e) {
			Logger.getLogger(AssistanceModule.class).error("An error occured while starting the module rest server", e);
		}
	}

	public final void setActionRunner(IClientActionRunner actionRunner) {
		this.actionRunner = actionRunner;
	}
	
	/**
	 * Gets the action runner which is required to perform actions on the client devices, like sending messages etc.
	 * @return
	 */
	protected final IClientActionRunner getActionRunner() {
		return actionRunner;
	}
	
	public final InformationProvider getInformationProvider() {
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
	
	/**
	 * Implement this if your module should provide a custom REST service, e.g. for 3party Apps to communicate directly with your module.
	 * Otherwise just return null.
	 * @return
	 */
	public abstract Collection<ModuleRestServer.MappedServlet> generateCustomServelets();
}