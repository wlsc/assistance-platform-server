package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_runningservice")
public class RunningService extends SensorData implements Serializable {
	public String packageName;
	public String className;
	
	public RunningService() {
		super();
	}
	
	public RunningService(String packageName, String className) {
		super();
		this.packageName = packageName;
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
