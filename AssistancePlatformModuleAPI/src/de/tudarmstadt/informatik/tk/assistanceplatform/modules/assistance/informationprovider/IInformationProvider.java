package de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider;

/**
 * This interface defines the method which should provide the current information of a implementing module 
 * @author bjeutter
 *
 */
public interface IInformationProvider {
	ModuleInformationCard currentModuleInformationForUserAndDevice(long userId, long deviceId);
}