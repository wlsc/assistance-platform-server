package de.tudarmstadt.informatik.tk.assistanceplatform.information;

import java.util.List;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;

public class ModuleInformationPrioritizerImpl implements IModuleInformationPrioritizer {
	private List<ModuleInformationCard> unsortedInformations;
	
	public ModuleInformationPrioritizerImpl(List<ModuleInformationCard> unsortedInformations) {
		this.unsortedInformations = unsortedInformations;
	}
	
	@Override
	public List<ModuleInformationCard> getPrioritizedInformationList() {
		// TODO: Do some meaningful prioritization!
		
		return unsortedInformations;
	}
}
