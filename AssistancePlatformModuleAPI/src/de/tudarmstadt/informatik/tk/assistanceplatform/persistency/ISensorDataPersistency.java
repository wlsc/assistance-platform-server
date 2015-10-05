package de.tudarmstadt.informatik.tk.assistanceplatform.persistency;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;

public interface ISensorDataPersistency {
	public boolean pesist(SensorData data);
}