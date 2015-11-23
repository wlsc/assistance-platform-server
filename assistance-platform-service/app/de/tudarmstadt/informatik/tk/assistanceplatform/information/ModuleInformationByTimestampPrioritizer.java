package de.tudarmstadt.informatik.tk.assistanceplatform.information;

import java.util.List;
import java.util.stream.Collectors;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;

/**
 * This information card prioritizer implementation sorts the module information card by their timestamp / defines the priority be the timestamp.
 * @author bjeutter
 *
 */
public class ModuleInformationByTimestampPrioritizer implements
		IModuleInformationPrioritizer {
	@Override
	public List<ModuleInformationCard> getPrioritizedInformationList(List<ModuleInformationCard> unsortedInformations) {
		return unsortedInformations.parallelStream()
				.sorted((c1, c2) -> c2.timestamp.compareTo(c1.timestamp))
				.collect(Collectors.toList());
	}
}