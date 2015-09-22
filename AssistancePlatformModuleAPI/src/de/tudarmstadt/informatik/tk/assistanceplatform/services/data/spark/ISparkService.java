package de.tudarmstadt.informatik.tk.assistanceplatform.services.data.spark;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.Event;

public interface ISparkService {
	public JavaStreamingContext createStreamingContext(Duration batchDuration);
	
	public JavaSparkContext createContext();
	
	public <T extends Event> JavaDStream<T> getEventReceiverStream(JavaStreamingContext sc, Class<T> eventType);
}