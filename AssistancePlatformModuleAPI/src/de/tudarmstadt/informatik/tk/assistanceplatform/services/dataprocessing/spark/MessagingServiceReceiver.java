package de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark;

import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

public class MessagingServiceReceiver<T> extends Receiver<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3001609774073205524L;

	private Channel<T> channel;
	
	private Class<T> type;
	
	/**
	 * Creates the messaging service receiver for Spark streaming purposes.
	 * Note that the messagingServiceSupplier is needed for per-spark-worker-node construction.
	 * @param eventType
	 * @param messagingServiceSupplier
	 */
	public MessagingServiceReceiver(Class<T> eventType) {
		super(StorageLevel.MEMORY_AND_DISK_2());
		this.type = eventType;
	}

	@Override
	public void onStart() {
		MessagingService s = createMessagingService();
		
		this.channel = s.channel(type);
		this.channel.subscribeConsumer(this::channelReceiver);
		
	}
	
	protected MessagingService createMessagingService() {
		return new JmsMessagingService();
	}
	
	private void channelReceiver(Channel<T> channel, T data) {
		store(data);
	}

	@Override
	public void onStop() {
		this.channel.unsubscribeConsumer(this::channelReceiver);
	}
}