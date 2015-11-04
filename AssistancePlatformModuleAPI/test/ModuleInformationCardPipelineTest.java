import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.IInformationCardCustomizer;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationProvider;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.IModuleBundleIdProvider;


public class ModuleInformationCardPipelineTest {
	@Test
	public void test() throws Exception {
		long userId = 1;
		long deviceId = 1;
		
		String testPayload = "test" + userId + "" + deviceId;
		String testId = "testid";	
		
		ModuleInformationProvider prov = new ModuleInformationProvider(new IModuleBundleIdProvider() {
			
			@Override
			public String getModuleId() {
				return testId;
			}
		}, new IInformationCardCustomizer() {
			
			@Override
			public void customizeModuleInformationcard(ModuleInformationCard card,
					long userId, long deviceId) {
				card.payload = testPayload;
			}
		});
		
		ModuleInformationCard receivedCard = prov.currentModuleInformationForUserAndDevice(userId, deviceId);
		
		assertTrue(receivedCard.payload.equals(testPayload));
		assertTrue(receivedCard.getModuleId().equals(testId));		
	}
}
