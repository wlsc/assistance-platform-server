import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistance.model.client.feedback.ContentDto;
import de.tudarmstadt.informatik.tk.assistance.model.client.feedback.ContentFactory;
import de.tudarmstadt.informatik.tk.assistance.model.client.feedback.item.ButtonDto;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.IInformationCardCustomizer;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationProvider;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.IModuleBundleIdProvider;


public class ModuleInformationCardPipelineTest {
	@Test
	public void test() throws Exception {
		long userId = 1;
		long deviceId = 1;
		
		ContentDto testPayload = ContentFactory.createButton("test", "target");
		String testId = "testid";	
		
		ModuleInformationProvider prov = new ModuleInformationProvider(new IModuleBundleIdProvider() {
			
			@Override
			public String getModuleId() {
				return testId;
			}
		}, new IInformationCardCustomizer() {
			
			@Override
			public void customizeModuleInformationCard(ModuleInformationCard card,
					long userId, long deviceId) {
				card.setContent(ContentFactory.createButton("test", "target"));
			}
		});
		
		ModuleInformationCard receivedCard = prov.currentModuleInformationForUserAndDevice(userId, deviceId);
		
		assertTrue(receivedCard.getContent().toString().equals(testPayload.toString()));
		assertTrue(receivedCard.getModuleId().equals(testId));		
	}
}
