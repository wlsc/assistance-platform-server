package de.tudarmstadt.informatik.tk.assistanceplatform.persistency;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;

/**
 * Describes the interface needed for persisting sensor data
 * @author bjeutter
 */
public interface ISensorDataPersistency {
	public boolean pesist(SensorData data);
}