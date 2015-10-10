package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "sensor_motionactivity")
public class MotionActivity extends SensorData {	
	public int walking;
	public int running;
	public int driving;
	public int stationary;
	public int unknown;
	
	 @JsonProperty(value = "onFoot")
	public int onFootOptional;
	 
	 @JsonProperty(value = "tilting")
	public int tiltingOptional;
	
	public MotionActivity() {
		super();
	}

	public int getWalking() {
		return walking;
	}

	public void setWalking(int walking) {
		this.walking = walking;
	}

	public int getRunning() {
		return running;
	}

	public void setRunning(int running) {
		this.running = running;
	}

	public int getDriving() {
		return driving;
	}

	public void setDriving(int driving) {
		this.driving = driving;
	}

	public int getStationary() {
		return stationary;
	}

	public void setStationary(int stationary) {
		this.stationary = stationary;
	}

	public int getUnknown() {
		return unknown;
	}

	public void setUnknown(int unknown) {
		this.unknown = unknown;
	}

	public int getOnFootOptional() {
		return onFootOptional;
	}

	public void setOnFootOptional(int onFootOptional) {
		this.onFootOptional = onFootOptional;
	}

	public int getTiltingOptional() {
		return tiltingOptional;
	}

	public void setTiltingOptional(int tiltingOptional) {
		this.tiltingOptional = tiltingOptional;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + driving;
		result = prime * result + onFootOptional;
		result = prime * result + running;
		result = prime * result + stationary;
		result = prime * result + tiltingOptional;
		result = prime * result + unknown;
		result = prime * result + walking;
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
