import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.GeographicPosition;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

public class JmsTest {

	@Test
	public void test() throws Exception {
		ConnectionFactory factory = new ActiveMQConnectionFactory();
		MessagingService msForConsumer = new JmsMessagingService(factory);
		
		GeographicPosition testData = new GeographicPosition(123, 321, 9775, 546);
		
		Channel<GeographicPosition> c = msForConsumer.channel("test", GeographicPosition.class);
		
		List<GeographicPosition> receivedData = new ArrayList<>();
		
		c.subscribeConsumer(
				(channel, data) -> {

					System.out.println("Channel: " + channel.getName() + " Data: " + data.toString());
					if(receivedData.size() == 0) {
						assertTrue(testData.equals(data));
					}
					
					receivedData.add(data);
				}
		);
		
		MessagingService msForPub = new JmsMessagingService(factory);
		Channel<GeographicPosition> channelForPub = msForPub.channel("test", GeographicPosition.class);
		
		channelForPub.publish(testData);
		
		for(int i = 0; i < 1000; i++) {
			channelForPub.publish(new GeographicPosition(Math.random() * 100, Math.random() * 100, (long)(Math.random() * Integer.MAX_VALUE), i));
		}
		
		Thread.sleep(1000);
		
		assertEquals(1001, receivedData.size());

		Thread.sleep(500);
	}

}
