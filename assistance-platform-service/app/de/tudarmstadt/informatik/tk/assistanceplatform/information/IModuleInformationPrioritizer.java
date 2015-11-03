package de.tudarmstadt.informatik.tk.assistanceplatform.information;

import java.util.List;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;

public interface IModuleInformationPrioritizer {
	public List<ModuleInformationCard> getPrioritizedInformationList();
}
