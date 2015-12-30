package sensorhandling.preprocessing;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import scala.actors.threadpool.Arrays;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;

/**
 * This class is responsible for proxying prepocessing of sensor data to special
 * IEventProcessor handlers.
 * 
 * @author bjeutter
 */
public class SpecialEventPreprocessor {
	private HashMap<Class, IEventPreprocessor> mappedPreprocessors;

	public SpecialEventPreprocessor() {
		this(
				Arrays.asList(new IEventPreprocessor[] { new TucanTokenEventPreprocessor() }));
	}

	public SpecialEventPreprocessor(Collection<IEventPreprocessor> preprocessors) {
		preparePreprocessors(preprocessors);
	}

	private void preparePreprocessors(
			Collection<IEventPreprocessor> preprocessors) {
		mappedPreprocessors = new HashMap<>();

		Iterator<IEventPreprocessor> preprocessorIterator = preprocessors
				.iterator();

		while (preprocessorIterator.hasNext()) {
			IEventPreprocessor p = preprocessorIterator.next();

			mappedPreprocessors.put(p.eventClassResponsibleFor(), p);
		}
	}

	public <T extends SensorData> T preprocess(T data) {
		IEventPreprocessor p = mappedPreprocessors.get(data.getClass());

		if (p == null) {
			return data;
		}

		return p.preprocessEvent(data);
	}
}
