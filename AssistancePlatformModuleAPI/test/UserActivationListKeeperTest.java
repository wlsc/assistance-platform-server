import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import retrofit.Callback;
import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeper;
import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClient;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClientFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.AssistancePlatformService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.ModuleLocalizationRequest;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.ModuleRegistrationRequest;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.SendMessageRequest;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.CassandraServiceConfigResponse;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.ModuleActivationsResponse;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.ServiceConfigResponse;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.dummy.DummyMessagingService;

public class UserActivationListKeeperTest {
	@Test
	public void moduleSepratationTest() throws Exception {
		DummyMessagingService dummyMs = new DummyMessagingService();

		UserActivationListKeeper keeperToTest1 = new UserActivationListKeeper(
				"test1", dummyMs);
		UserActivationListKeeper keeperToTest2 = new UserActivationListKeeper(
				"test2", dummyMs);

		Channel<UserRegistrationInformationEvent> regInfoChannel = dummyMs
				.channel(UserRegistrationInformationEvent.class);
		
		regInfoChannel.publish(new UserRegistrationInformationEvent(1, "test1", true));
		
		assertTrue(keeperToTest1.getUserActivationChecker().isActivatedForUser(1));
		assertFalse(keeperToTest1.getUserActivationChecker().isActivatedForUser(2));
		assertFalse(keeperToTest2.getUserActivationChecker().isActivatedForUser(1));
		
		regInfoChannel.publish(new UserRegistrationInformationEvent(1, "test1", false));
		
		assertFalse(keeperToTest1.getUserActivationChecker().isActivatedForUser(1));
		
		regInfoChannel.publish(new UserRegistrationInformationEvent(2, "test2", true));
		
		assertTrue(keeperToTest2.getUserActivationChecker().isActivatedForUser(2));
		assertFalse(keeperToTest1.getUserActivationChecker().isActivatedForUser(2));
	}
	
	@Test
	public void initialPullTest() throws Exception {
		DummyMessagingService dummyMs = new DummyMessagingService();
		
		PlatformClient cli = new PlatformClient(new AssistancePlatformService() {
			
			@Override
			public void update(ModuleRegistrationRequest body, Callback<Void> callback) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void sendMessage(SendMessageRequest body, Callback<Void> callback) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void register(ModuleRegistrationRequest body, Callback<Void> callback) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void localize(ModuleLocalizationRequest body, Callback<Void> callback) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void getModuleActivationsByUsers(String moduleId,
					Callback<ModuleActivationsResponse> callback) {
				callback.success(new ModuleActivationsResponse(new long[] { 1, 2}), null);
			}

			@Override
			public CassandraServiceConfigResponse getCassandraServiceConfig(
					String moduleId) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ServiceConfigResponse getServiceConfig(String moduleId,
					String service) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		PlatformClientFactory.setInstance(cli);

		UserActivationListKeeper keeperToTest1 = new UserActivationListKeeper(
				"test1", dummyMs);
		
		assertTrue(keeperToTest1.getUserActivationChecker().isActivatedForUser(1));
		assertTrue(keeperToTest1.getUserActivationChecker().isActivatedForUser(2));
		assertFalse(keeperToTest1.getUserActivationChecker().isActivatedForUser(3));
	}
}
