import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Position;
import de.tudarmstadt.informatik.tk.assistanceplatform.platform.data.UserRegistrationInformationEvent;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

public class JmsTest {

	@Test
	public void test() throws Exception {
		BasicConfigurator.configure();
		
		ConnectionFactory factory = new ActiveMQConnectionFactory();
		MessagingService msForConsumer = new JmsMessagingService(factory);
		
		Position testData = new Position(123, 321,  1,  9775, 546);
		
		Channel<Position> c = msForConsumer.channel("test", Position.class);
		
		List<Position> receivedData = new ArrayList<>();
		
		c.subscribeConsumer(
				(channel, data) -> {

				//	Logger.getLogger(JmsTest.class).info("Channel: " + channel.getName() + " Data: " + data.toString());
					if(receivedData.size() == 0) {
						assertTrue(testData.equals(data));
					}
					
					receivedData.add(data);
				}
		);
		
		MessagingService msForPub = new JmsMessagingService(factory);
		Channel<Position> channelForPub = msForPub.channel("test", Position.class);
		
		channelForPub.publish(testData);
		
		for(int i = 0; i < 1000; i++) {
			c.publish(new Position((long)(Math.random() * 100), (long)(Math.random() * 100),  i, (long)(Math.random() * Integer.MAX_VALUE), i));
		}
		
		Thread.sleep(1000);
		
		assertEquals(1001, receivedData.size());

		Thread.sleep(500);
	}
	
	@Test
	public void userRegTest() throws Exception {
		BasicConfigurator.configure();
		
		ConnectionFactory factory = new ActiveMQConnectionFactory();
		MessagingService msForConsumer = new JmsMessagingService(factory);
		
		UserRegistrationInformationEvent testData = new UserRegistrationInformationEvent(123L, true);
		
		Channel<UserRegistrationInformationEvent> c = msForConsumer.channel("test2", UserRegistrationInformationEvent.class);
		
		List<UserRegistrationInformationEvent> receivedData = new ArrayList<>();
		
		c.subscribeConsumer(
				(channel, data) -> {

					//Logger.getLogger(JmsTest.class).info("Channel: " + channel.getName() + " Data: " + data.toString());
					if(receivedData.size() == 0) {
						assertTrue(testData.equals(data));
					}
					
					receivedData.add(data);
				}
		);
		
		MessagingService msForPub = new JmsMessagingService(factory);
		Channel<UserRegistrationInformationEvent> channelForPub = msForPub.channel("test2", UserRegistrationInformationEvent.class);
		
		channelForPub.publish(testData);
		
		for(int i = 0; i < 1000; i++) {
			c.publish(new UserRegistrationInformationEvent((long)(Math.random() * 100), true));
		}
		
		Thread.sleep(1000);
		
		assertEquals(1001, receivedData.size());

		Thread.sleep(500);
	}

}
