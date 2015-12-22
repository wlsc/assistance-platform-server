package de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark;

import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClientFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.ConfiguredJmsServiceFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Consumer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;

public class MessagingServiceReceiver<T> extends Receiver<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3001609774073205524L;

	private Channel<T> channel;
	
	private Class<T> type;
	
	private Consumer<T> consumerForDeregistration;
	
	/**
	 * Needed for creating the correct JMS Messaging Service
	 */
	protected final String usingModuleId;

	/**
	 * Needed for creating the correct JMS Messaging Service
	 */
	protected final String platformUrlAndPort;
	
	/**
	 * Creates the messaging service receiver for Spark streaming purposes.
	 * Note that the messagingServiceSupplier is needed for per-spark-worker-node construction.
	 * @param eventType
	 * @param messagingServiceSupplier
	 */
	public MessagingServiceReceiver(String usingModuleId, String platformUrlAndPort, Class<T> eventType) {
		super(StorageLevel.MEMORY_AND_DISK_2());

		this.usingModuleId = usingModuleId;
		this.platformUrlAndPort = platformUrlAndPort;
		
		this.type = eventType;
	}

	@Override
	public void onStart() {
		MessagingService s = createMessagingService();
		
		consumerForDeregistration = this::channelReceiver;
		
		this.channel = s.channel(type);
		this.channel.subscribeConsumer(consumerForDeregistration);
	}
	
	protected MessagingService createMessagingService() {
		PlatformClientFactory.createInstance(platformUrlAndPort);
		return (MessagingService)ConfiguredJmsServiceFactory.createJmsInstance(usingModuleId);
	}
	
	private void channelReceiver(Channel<T> channel, T data) {
		store(data);
	}

	@Override
	public void onStop() {
		this.channel.unsubscribeConsumer(consumerForDeregistration);
	}
}