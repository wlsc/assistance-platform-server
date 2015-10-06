import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Position;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.UserFilteredMessagingServiceDecorator;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.dummy.DummyMessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.users.UserActivationList;


public class UserFilteredMessagingServiceTests {


	@Test
	public void test() throws Exception {
		UserActivationList activationList = new UserActivationList();
		
		activationList.addActivationByUser(1L); // Activate user 1 for this test
		
		MessagingService userFiltered = new UserFilteredMessagingServiceDecorator(new DummyMessagingService(), activationList);

		Channel<Position> c = userFiltered.channel("test", Position.class);
		
		List<Position> receivedData = new ArrayList<>();
		
		c.subscribeConsumer(
				(channel, data) -> {
					receivedData.add(data);
				}
		);
		
		int events = 10000;
		
		for(int i = 0; i < events; i++) {
			Long userId = i % 2 == 0 ? 2L : 1L; // Every second message is for user 1
			c.publish(new Position(userId, userId, Calendar.getInstance().getTime(), Math.random(), Math.random()));
		}
		
		assertEquals(events / 2, receivedData.size());

		Thread.sleep(500);
		
	}
}
