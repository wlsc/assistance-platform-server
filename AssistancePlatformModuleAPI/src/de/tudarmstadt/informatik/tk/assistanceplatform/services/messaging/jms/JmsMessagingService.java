package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Consumer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingServiceConfiguration;

/**
 * This class uses the JMS classes to implement a messaging service.
 */
public class JmsMessagingService extends MessagingService {
	private Connection connection;
	
	private Session messageCreationSession;
	
	private Map<Channel, MessageProducer> channelsToProducers = new HashMap<>();
	
	private final static Logger logger = Logger.getLogger(JmsMessagingService.class);
	
	
	public JmsMessagingService() {
		this(createDefaultConnectionFactory());
	}
	
	public JmsMessagingService(MessagingServiceConfiguration config) {
		this(config, createDefaultConnectionFactory());
	}
	
	public JmsMessagingService(ConnectionFactory factory) {
		super();
		
		setupConnection(factory);
	}
	
	public JmsMessagingService(MessagingServiceConfiguration config, ConnectionFactory factory) {
		super(config);
		
		setupConnection(factory);
	}
	
	private static ConnectionFactory createDefaultConnectionFactory() {
		return new ActiveMQConnectionFactory();
	}
	
	private void setupConnection(ConnectionFactory factory) {
		try {
			connection = factory.createConnection();
			connection.start();
			this.messageCreationSession = createSession();
		} catch (JMSException e) {
			logger.error("JMS connection establishement failed", e);
		}
	}
	
	Session createSession() {
		try {
			return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			logger.error("Session creation failed", e);
		}
		
		return null;
	}
	
	@Override
	protected <T> void subscribe(Consumer<T> consumer, Channel<T> channel) {
		MessageConsumer jmsConsumer = createConsumerForChannel(channel);
		try {
			jmsConsumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message msg) {
					
					BytesMessage bm = (BytesMessage)msg;
					
					byte[] buff = new byte[0];
					try {
						buff = new byte[(int)bm.getBodyLength()];
						bm.readBytes(buff);
					} catch (JMSException e) {
						logger.error("JMS message reading failed", e);
					}
					
					T obj = getSerializer().deserialize(buff, channel.getType());

					notifyConsumer(consumer, channel, obj);
				}
			});
		} catch (JMSException e) {
			logger.error("JMS message listener registration failed", e);
		}
	}

	@Override
	protected <T> void unsubscribe(Consumer<T> consumer, Channel<T> channel) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected <T> void publish(Channel<T> channel, T data) {
		MessageProducer producer = getProducerForChannel(channel);
		try {
			byte[] serializedObject = getSerializer().serialize(data);
			
			BytesMessage bm = messageCreationSession.createBytesMessage();
			bm.writeBytes(serializedObject);

			producer.send(bm);
		} catch (JMSException e) {
			logger.error("JMS message publishing failed", e);
		}
	}
	
	private MessageProducer getProducerForChannel(Channel channel) {
		MessageProducer producer = channelsToProducers.get(channel);
		if(producer == null) {
			Topic topic;
			try {
				Session session = createSession();
				
				String channelName = channel.getName();
				
				topic = session.createTopic(channelName);
				producer = session.createProducer(topic);
				channelsToProducers.put(channel, producer);
			} catch (JMSException e) {
				logger.error("JMS channel / topic acitvation failed", e);
			}
		}
		
		return producer;
	}
	
	private MessageConsumer createConsumerForChannel(Channel channel) {
		String channelName = channel.getName();
		
		MessageConsumer consumer = null;
			Topic topic;
			try {
				Session session = createSession();
				topic = session.createTopic(channelName);
				consumer = session.createConsumer(topic);
			} catch (JMSException e) {
				logger.error("JMS consumer creation failed", e);
			}
		
		return consumer;
	}
}