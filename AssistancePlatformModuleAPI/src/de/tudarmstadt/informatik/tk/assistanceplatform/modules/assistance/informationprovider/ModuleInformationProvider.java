package de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.IModuleBundleIdProvider;

public class ModuleInformationProvider implements IInformationProvider {
	private final String moduleId;
	
	private final IInformationCardCustomizer customizer;
	
	public ModuleInformationProvider(IModuleBundleIdProvider bundleIdProvider, IInformationCardCustomizer customizer) {
		this.moduleId = bundleIdProvider.getModuleId();
		this.customizer = customizer;
	}
	
	@Override
	public final ModuleInformationCard currentModuleInformationForUserAndDevice(
			long userId, long deviceId) {
		ModuleInformationCard card = new ModuleInformationCard(moduleId);
		this.customizer.customizeModuleInformationcard(card, userId, deviceId);
		return card;
	}
}
