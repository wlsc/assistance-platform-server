package de.tudarmstadt.informatik.tk.assistanceplatform.information;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;

import java.util.List;

/**
 * This interface can be implemented to provide a strategy for prioritzing a list of module information cards
 *
 * @author bjeutter
 */
@FunctionalInterface
public interface IModuleInformationPrioritizer {
    public List<ModuleInformationCard> getPrioritizedInformationList(List<ModuleInformationCard> unsortedInformations);
}