package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms;

import java.io.ByteArrayOutputStream;
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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.GeographicPosition;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Consumer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;

public class JmsMessagingService extends MessagingService {
	private Connection connection;
	
	private Session messageCreationSession;
	
	private Map<Channel, MessageProducer> channelsToProducers = new HashMap<>();
	
	private Kryo kryo = new Kryo();
	
	
	public JmsMessagingService() {
		this(new ActiveMQConnectionFactory());
	}
	
	public JmsMessagingService(ConnectionFactory factory) {
		super();
		
		try {
			connection = factory.createConnection();
			connection.start();
			this.messageCreationSession = createSession();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Session createSession() {
		try {
			return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
					Input input = new Input(buff);
					
					T obj = (T)kryo.readObject(input, channel.getType());

					notifyConsumer(consumer, channel, obj);
				}
			});
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			Output output = new Output(outputStream);
			
			kryo.writeObject(output, data);
			
			BytesMessage bm = messageCreationSession.createBytesMessage();
			bm.writeBytes(output.getBuffer());

			producer.send(bm);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // TODO: Proper Serialization!!!!!111!!
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
				Session session = createSession();
				topic = session.createTopic(channelName);
				consumer = session.createConsumer(topic);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return consumer;
	}
}