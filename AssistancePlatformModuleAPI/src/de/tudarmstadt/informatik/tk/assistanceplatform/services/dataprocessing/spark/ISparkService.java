package de.tudarmstadt.informatik.tk.assistanceplatform.services.dataprocessing.spark;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.Event;

public interface ISparkService {
  /**
   * Creates a Spark Streaming Context
   * 
   * @param batchDuration The duration in which the events hould be batched
   * @return
   */
  JavaStreamingContext createStreamingContext(Duration batchDuration);

  /**
   * Creates a "general" Apache Spark context
   * 
   * @return
   */
  JavaSparkContext createContext();

  /**
   * Creates and returns a event receiver stream (streams events from the messaging system [e.g.
   * ActiveMQ]). Note that this can be prefiltered (e.g. user registration filtered)
   * 
   * @param sc
   * @param eventType
   * @return
   */
  <T extends Event> JavaDStream<T> getEventReceiverStream(JavaStreamingContext sc,
      Class<T> eventType);
}
