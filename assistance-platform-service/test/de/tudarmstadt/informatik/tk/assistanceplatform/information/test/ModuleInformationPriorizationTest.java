package de.tudarmstadt.informatik.tk.assistanceplatform.information.test;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.information.IModuleInformationPrioritizer;
import de.tudarmstadt.informatik.tk.assistanceplatform.information.ModuleInformationByTimestampPrioritizer;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;


public class ModuleInformationPriorizationTest {
	@Test
	public void prioTest() {
		Date newest = new Date(2);
		Date middle = new Date(1);
		Date oldest = new Date(0);
		
		ModuleInformationCard c1 = new ModuleInformationCard("id1", oldest);
		ModuleInformationCard c2 = new ModuleInformationCard("id2", middle);
		ModuleInformationCard c3 = new ModuleInformationCard("id3", newest);
		
		IModuleInformationPrioritizer prioritizer = new ModuleInformationByTimestampPrioritizer();
		
		List<ModuleInformationCard> prioritizedCards = prioritizer.getPrioritizedInformationList(Arrays.asList(c2, c1, c3));
		
		assertTrue(prioritizedCards.size() == 3);
		
		assertTrue(prioritizedCards.get(0) == c3);
		assertTrue(prioritizedCards.get(1) == c2);
		assertTrue(prioritizedCards.get(2) == c1);
	}
}
