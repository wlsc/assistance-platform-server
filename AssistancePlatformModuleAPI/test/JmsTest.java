import java.io.IOException;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.GeographicPosition;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

public class JmsTest {

	@Test
	public void test() {
		ConnectionFactory factory = new ActiveMQConnectionFactory();
		MessagingService<GeographicPosition> ms = new JmsMessagingService<>(
				factory);

		MessagingService<GeographicPosition> subService = new JmsMessagingService<>(
				factory);
		subService.channel("test", GeographicPosition.class).subscribeConsumer(
				(channel, data) -> System.out.println("Channel: "
						+ channel.getName() + " Data: " + data.toString()));

		ms.channel("test", GeographicPosition.class).publish(new GeographicPosition(123, 321, 9775, 546));

		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
