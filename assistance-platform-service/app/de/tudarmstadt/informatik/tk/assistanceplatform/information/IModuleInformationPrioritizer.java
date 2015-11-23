package de.tudarmstadt.informatik.tk.assistanceplatform.information;

import java.util.List;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;

/**
 * This interface can be implemented to provide a strategy for prioritzing a list of module information cards
 * @author bjeutter
 *
 */
public interface IModuleInformationPrioritizer {
	public List<ModuleInformationCard> getPrioritizedInformationList(List<ModuleInformationCard> unsortedInformations);
}