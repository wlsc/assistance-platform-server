package de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider;

public interface IInformationCardCustomizer {
	/**
	 * OVerride this method to push your module specific information into the card
	 * @param card
	 */
	void customizeModuleInformationCard(ModuleInformationCard card, long userId, long deviceId);
}
