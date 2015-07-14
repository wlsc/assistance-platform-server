import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.platform.UserActivationListKeeper;
import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.dummy.DummyMessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.IUserActivationChecker;


public class UserActivationListKeeperTest {


	@Test
	public void test() throws Exception {
		MessagingService ms = new DummyMessagingService();
		
		UserActivationListKeeper activationkeeper = new UserActivationListKeeper(ms);
		
		IUserActivationChecker activationChecker = activationkeeper.getUserActivationChecker();
		
		ms.channel(UserRegistrationInformationEvent.class).publish(new UserRegistrationInformationEvent(1L, false));
		
		assertFalse( activationChecker.isActivatedForUser(1L));
		assertFalse( activationChecker.isActivatedForUser(2L));
		
		ms.channel(UserRegistrationInformationEvent.class).publish(new UserRegistrationInformationEvent(1L, true));
		
		assertTrue( activationChecker.isActivatedForUser(1L));
		assertFalse( activationChecker.isActivatedForUser(2L));
		
		ms.channel(UserRegistrationInformationEvent.class).publish(new UserRegistrationInformationEvent(2L, true));
		
		assertTrue( activationChecker.isActivatedForUser(1L));
		assertTrue( activationChecker.isActivatedForUser(2L));
		
		ms.channel(UserRegistrationInformationEvent.class).publish(new UserRegistrationInformationEvent(2L, false));
		
		assertTrue( activationChecker.isActivatedForUser(1L));
		assertFalse( activationChecker.isActivatedForUser(2L));
	}
}
