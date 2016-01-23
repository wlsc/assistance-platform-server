package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.Table;

@Table(name = "sensor_foreground")
public class Foreground extends SensorData  implements Serializable{
	public String packageName = "none";
	public String appName = "none";
	public String className = "none";
	public String activityLabel;
	public String color;
	public String url;
	public String eventType;
	public int keystrokes;
	
	public Foreground() {
		super();
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getActivityLabel() {
		return activityLabel;
	}

	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public int getKeystrokes() {
		return keystrokes;
	}

	public void setKeystrokes(int keystrokes) {
		this.keystrokes = keystrokes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((activityLabel == null) ? 0 : activityLabel.hashCode());
		result = prime * result + ((appName == null) ? 0 : appName.hashCode());
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result
				+ ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + keystrokes;
		result = prime * result
				+ ((packageName == null) ? 0 : packageName.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Foreground other = (Foreground) obj;
		if (activityLabel == null) {
			if (other.activityLabel != null)
				return false;
		} else if (!activityLabel.equals(other.activityLabel))
			return false;
		if (appName == null) {
			if (other.appName != null)
				return false;
		} else if (!appName.equals(other.appName))
			return false;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (eventType == null) {
			if (other.eventType != null)
				return false;
		} else if (!eventType.equals(other.eventType))
			return false;
		if (keystrokes != other.keystrokes)
			return false;
		if (packageName == null) {
			if (other.packageName != null)
				return false;
		} else if (!packageName.equals(other.packageName))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}


}