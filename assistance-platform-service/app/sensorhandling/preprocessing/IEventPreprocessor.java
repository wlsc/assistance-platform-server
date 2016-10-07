package sensorhandling.preprocessing;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;

/**
 * Implement this interface to provide Event Preprocessing functionality.
 *
 * @param <T> A sensor data type
 * @author bjeutter
 */
public interface IEventPreprocessor<T extends SensorData> {
    public Class<T> eventClassResponsibleFor();

    public T preprocessEvent(T event) throws Exception;
}