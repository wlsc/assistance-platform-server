import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Consumer;

import org.junit.Test;

import retrofit.Callback;
import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeper;
import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClient;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClientFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.actions.IGetUserActivationsForModule;
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
	class DoNothingUserActivationPuller implements IGetUserActivationsForModule {
		@Override
		public void getUserActivationsForModule(String moduleId,
				Consumer<long[]> activatedUserIdsCallback) {
			// TODO Auto-generated method stub
			
		}
	}
	
	@Test
	public void moduleSepratationTest() throws Exception {
		DummyMessagingService dummyMs = new DummyMessagingService();

		UserActivationListKeeper keeperToTest1 = new UserActivationListKeeper(
				"test1", dummyMs, new DoNothingUserActivationPuller());
		UserActivationListKeeper keeperToTest2 = new UserActivationListKeeper(
				"test2", dummyMs, new DoNothingUserActivationPuller());

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
		
		IGetUserActivationsForModule cli = new IGetUserActivationsForModule() {
			
			@Override
			public void getUserActivationsForModule(String moduleId,
					Consumer<long[]> activatedUserIdsCallback) {
				activatedUserIdsCallback.accept(new long[] { 1, 2});
			}
		};
		

		UserActivationListKeeper keeperToTest1 = new UserActivationListKeeper(
				"test1", dummyMs, cli);
		
		assertTrue(keeperToTest1.getUserActivationChecker().isActivatedForUser(1));
		assertTrue(keeperToTest1.getUserActivationChecker().isActivatedForUser(2));
		assertFalse(keeperToTest1.getUserActivationChecker().isActivatedForUser(3));
	}
}
