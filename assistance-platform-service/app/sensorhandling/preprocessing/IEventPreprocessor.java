package sensorhandling.preprocessing;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;

/**
 * Implement this interface to provide Event Preprocessing functionality.
 * @author bjeutter
 *
 * @param <T> A sensor data type
 */
public interface IEventPreprocessor<T extends SensorData> {
	public Class<T> eventClassResponsibleFor();
	
	public T preprocessEvent(T event); 
}