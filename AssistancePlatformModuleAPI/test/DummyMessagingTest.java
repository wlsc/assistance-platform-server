import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.GeographicPosition;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.dummy.DummyMessagingService;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class DummyMessagingTest {

	@Test
	public void test() throws Exception {
		MessagingService ms = new DummyMessagingService();
		
		GeographicPosition testData = new GeographicPosition(123, 321, 9775, 546);
		
		Channel<GeographicPosition> c = ms.channel("test", GeographicPosition.class);
		
		List<GeographicPosition> receivedData = new ArrayList<>();
		
		c.subscribeConsumer(
				(channel, data) -> {
					if(receivedData.size() == 0) {
						assertTrue(testData.equals(data));
					}
					
					receivedData.add(data);
				}
		);
		
		c.publish(testData);
		
		for(int i = 0; i < 100; i++) {
			c.publish(new GeographicPosition(Math.random() * 100, Math.random() * 100, (long)(Math.random() * Integer.MAX_VALUE), i));
		}
		
		assertEquals(101, receivedData.size());

		Thread.sleep(500);
		
	}

}
