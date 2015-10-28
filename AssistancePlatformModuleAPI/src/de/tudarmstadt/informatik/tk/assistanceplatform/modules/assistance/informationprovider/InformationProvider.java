package de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider;

/**
 * This interface defines the method which should provide the current information of a implementing module 
 * @author bjeutter
 *
 */
public interface InformationProvider {
	ModuleInformationCard currentModuleInformationForUserAndDevice(long userId, long deviceId);
}
