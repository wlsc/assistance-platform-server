import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeper;
import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.dummy.DummyMessagingService;

public class UserActivationListKeeperTest {
	@Test
	public void test() throws Exception {
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
}
