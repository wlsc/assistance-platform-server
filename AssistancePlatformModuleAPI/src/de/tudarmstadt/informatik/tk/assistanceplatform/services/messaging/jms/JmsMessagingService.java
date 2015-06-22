package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms;

import java.util.HashMap;
import java.util.Map;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Consumer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;

import javax.jms.*;

public class JmsMessagingService extends MessagingService {
	private Connection connection;
	
	private Session session;
	
	private Map<Channel, MessageProducer> channelsToProducers = new HashMap<>();
	
	public JmsMessagingService(ConnectionFactory factory) {
		super();
		
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void subscribe(Consumer consumer, Channel channel) {
		MessageConsumer jmsConsumer = createConsumerForChannel(channel);
		try {
			jmsConsumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message msg) {
					consumer.consumeDataOfChannel(channel, (TextMessage)msg);
					
				}
			});
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void unsubscribe(Consumer consumer, Channel channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void publish(Channel channel, Object data) {
		MessageProducer producer = getProducerForChannel(channel);
		try {
			Message msg = session.createTextMessage(data.toString());
			producer.send(msg);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // TODO: Proper Serialization!!!!!111!!
	}
	
	private MessageProducer getProducerForChannel(Channel channel) {
		String channelName = channel.getName();
		
		MessageProducer producer = channelsToProducers.get(channelName);
		if(producer == null) {
			Topic topic;
			try {
				topic = session.createTopic(channelName);
				producer = session.createProducer(topic);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return producer;
	}
	
	private MessageConsumer createConsumerForChannel(Channel channel) {
		String channelName = channel.getName();
		
		MessageConsumer consumer = null;
			Topic topic;
			try {
				topic = session.createTopic(channelName);
				consumer = session.createConsumer(topic);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return consumer;
	}
}