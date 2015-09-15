package de.tudarmstadt.informatik.tk.assistanceplatform.services.data.spark;

import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.Channel;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.MessagingService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.jms.JmsMessagingService;

public class MessagingServiceReceiver<T> extends Receiver<T> {
	private Channel<T> channel;
	
	private Class<T> type;
	
	public MessagingServiceReceiver(Class<T> type) {
		super(StorageLevel.MEMORY_AND_DISK_2());
		this.type = type;
	}

	@Override
	public void onStart() {
		MessagingService s = new JmsMessagingService();
		
		this.channel = s.channel(type);
		this.channel.subscribeConsumer(this::channelReceiver);
		
	}
	
	private void channelReceiver(Channel<T> channel, T data) {
		store(data);
	}

	@Override
	public void onStop() {
		this.channel.unsubscribeConsumer(this::channelReceiver);
	}
}