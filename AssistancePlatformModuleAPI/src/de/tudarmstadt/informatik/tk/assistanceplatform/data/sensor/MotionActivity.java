package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "sensor_motionactivity")
public class MotionActivity extends SensorData {	
	public boolean walking;
	public boolean running;
	public boolean driving;
	public boolean stationary;
	public boolean unknown;
	public int accuracy;
	
	 @JsonProperty(value = "onFoot")
	public boolean onFootOptional;
	 
	 @JsonProperty(value = "tilting")
	public boolean tiltingOptional;
	
	public MotionActivity() {
		super();
	}

	
	public boolean isWalking() {
		return walking;
	}


	public void setWalking(boolean walking) {
		this.walking = walking;
	}


	public boolean isRunning() {
		return running;
	}


	public void setRunning(boolean running) {
		this.running = running;
	}


	public boolean isDriving() {
		return driving;
	}


	public void setDriving(boolean driving) {
		this.driving = driving;
	}


	public boolean isStationary() {
		return stationary;
	}


	public void setStationary(boolean stationary) {
		this.stationary = stationary;
	}


	public boolean isUnknown() {
		return unknown;
	}


	public void setUnknown(boolean unknown) {
		this.unknown = unknown;
	}


	public int getAccuracy() {
		return accuracy;
	}


	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}


	public boolean isOnFootOptional() {
		return onFootOptional;
	}


	public void setOnFootOptional(boolean onFootOptional) {
		this.onFootOptional = onFootOptional;
	}


	public boolean isTiltingOptional() {
		return tiltingOptional;
	}


	public void setTiltingOptional(boolean tiltingOptional) {
		this.tiltingOptional = tiltingOptional;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + accuracy;
		result = prime * result + (driving ? 1231 : 1237);
		result = prime * result + (onFootOptional ? 1231 : 1237);
		result = prime * result + (running ? 1231 : 1237);
		result = prime * result + (stationary ? 1231 : 1237);
		result = prime * result + (tiltingOptional ? 1231 : 1237);
		result = prime * result + (unknown ? 1231 : 1237);
		result = prime * result + (walking ? 1231 : 1237);
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
		MotionActivity other = (MotionActivity) obj;
		if (accuracy != other.accuracy)
			return false;
		if (driving != other.driving)
			return false;
		if (onFootOptional != other.onFootOptional)
			return false;
		if (running != other.running)
			return false;
		if (stationary != other.stationary)
			return false;
		if (tiltingOptional != other.tiltingOptional)
			return false;
		if (unknown != other.unknown)
			return false;
		if (walking != other.walking)
			return false;
		return true;
	}
}
