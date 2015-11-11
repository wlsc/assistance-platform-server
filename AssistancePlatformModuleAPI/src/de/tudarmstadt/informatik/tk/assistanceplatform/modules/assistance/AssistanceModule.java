package de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.datastax.driver.core.Session;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Module;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.IInformationCardCustomizer;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.IInformationProvider;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationProvider;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.IModuleBundleIdProvider;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.IClientActionRunner;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.MappedServlet;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.ModuleRestServer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.ModuleRestServerFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.required.services.resources.ServiceResources;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.required.services.resources.ServiceResourcesFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.persistency.CassandraServiceFactory;


/**
 * Assistance Modules are the ones that interact with the user, give recommendations etc..
 * This class should be sub-classed and then implement the desired Assistance Logic.
 * @author bjeutter
 *
 */
public abstract class AssistanceModule extends Module {
	private IModuleBundleIdProvider moduleIdProvider;
	
	private IClientActionRunner actionRunner;
	
	private IInformationProvider informationProvider;
	
	private CassandraSessionProxy cassandraProxy;
	
	@Override
	protected final void internalDoBeforeStartup() {
		try {
			ModuleRestServer server = ModuleRestServerFactory.getInstance();
			
			// Prepapre Resources for REST services
			ServiceResourcesFactory.setInstance(new ServiceResources(this));
			
			server.setCustomServlets(generateCustomServelets());
			server.start();
		} catch (Exception e) {
			Logger.getLogger(AssistanceModule.class).error("An error occured while starting the module rest server", e);
		}
	}

	public final void setActionRunner(IClientActionRunner actionRunner) {
		this.actionRunner = actionRunner;
	}
	
	public final void setModuleIdProvider(IModuleBundleIdProvider idProvider) {
		this.moduleIdProvider = idProvider;
	}
	
	
	/**
	 * Gets the action runner which is required to perform actions on the client devices, like sending messages etc.
	 * @return
	 */
	protected final IClientActionRunner getActionRunner() {
		return actionRunner;
	}
	
	public final IInformationProvider getInformationProvider() {
		if(informationProvider == null) {
			informationProvider = new ModuleInformationProvider(getModuleIdProvider(), generateInformationCardCustomizer());
		}
		
		return informationProvider;
	}
	
	protected final IModuleBundleIdProvider getModuleIdProvider() {
		return this.moduleIdProvider;
	}
	
	protected final Session getCassandraSession() {
		if(cassandraProxy == null) {
			cassandraProxy = CassandraServiceFactory.getSessionProxy();
		}
		
		return cassandraProxy.getSession();
	}

	/**
	 * If you want to provide "pull" based "current" module information to the user, then implement this class by instantiating your InformationProvider.
	 * If you don't plan such a feature, then just return null. 
	 * @return
	 */
	protected abstract IInformationCardCustomizer generateInformationCardCustomizer();
	
	/**
	 * Implement this if your module should provide a custom REST service, e.g. for 3party Apps to communicate directly with your module.
	 * Otherwise just return null.
	 * @return
	 */
	protected abstract Collection<MappedServlet> generateCustomServelets();
}