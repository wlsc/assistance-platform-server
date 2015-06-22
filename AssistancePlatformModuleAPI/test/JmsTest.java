import static org.junit.Assert.*;

import java.io.IOException;

import javax.jms.ConnectionFactory;

import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Consumer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

import org.apache.activemq.ActiveMQConnectionFactory;


public class JmsTest {

	@Test
	public void test() {
		ConnectionFactory factory = new ActiveMQConnectionFactory();
		MessagingService ms = new JmsMessagingService(factory);
		
		MessagingService subService = new JmsMessagingService(factory);
		subService.channel("test").subscribeConsumer(new Consumer() {
			
			@Override
			public void consumeDataOfChannel(Channel channel, Object data) {
				System.out.println("Channel: " + channel.getName()+ " Data: " + data.toString());
				
			}
		});
		
		ms.channel("test").publish("Test123");
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
