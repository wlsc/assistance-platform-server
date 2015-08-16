import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Position;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.dummy.DummyMessagingService;

public class DummyMessagingTest {

	@Test
	public void test() throws Exception {
		MessagingService ms = new DummyMessagingService();
		
		Position testData = new Position(1, 123, 321, 9775, 546);
		
		Channel<Position> c = ms.channel("test", Position.class);
		
		List<Position> receivedData = new ArrayList<>();
		
		c.subscribeConsumer(
				(channel, data) -> {
					if(receivedData.size() == 0) {
						assertTrue(testData.equals(data));
					}
					
					receivedData.add(data);
				}
		);
		
		c.publish(testData);
		
		int events = 10000;
		
		for(int i = 0; i < events; i++) {
			c.publish(new Position((long)(Math.random() * 100), (long)(Math.random() * 100), (long)(Math.random() * 100), (long)(Math.random() * Integer.MAX_VALUE), i));
		}
		
		assertEquals(events + 1, receivedData.size());

		Thread.sleep(500);
		
	}

}
