package de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.Event;

public class SparkService implements ISparkService {	
	private final String appName;
	private final String master;
	private final String[] jars;
	private final String moduleId;
	
	private SparkConf sparkConfInstance;
	
	public SparkService(String moduleId, String appName, String master, String[] jars) {
		this.appName = appName;
		this.master = master;
		this.jars = jars;
		this.moduleId = moduleId;
	}
	
	public JavaStreamingContext createStreamingContext(Duration batchDuration) {
		return new JavaStreamingContext(getSparkConf(),
				Durations.seconds(10));
	}
	
	public JavaSparkContext createContext() {
		return new JavaSparkContext(getSparkConf());
	}

	public SparkConf getSparkConf() {
		// Lazily initialize the configuration
		if(sparkConfInstance == null) {
			sparkConfInstance = createSparkConf(appName, master, jars);
		}
		return sparkConfInstance;
	}
	
	private SparkConf createSparkConf(String appName, String master, String[] jars) {
		return new SparkConf()
		.setAppName(appName)
		.setMaster(master)
		.setJars(jars)
		.set("spark.serializer",
				"org.apache.spark.serializer.KryoSerializer");
	}

	@Override
	public <T extends Event> JavaDStream<T> getEventReceiverStream(JavaStreamingContext sc, Class<T> eventType) {		
		MessagingServiceReceiver<T> messagingReceiver = new UserFilteredMessagingServiceReceiver<T>(moduleId, eventType);
		
		JavaDStream<T> stream = sc.receiverStream(messagingReceiver);
		
		return stream;
	}
}