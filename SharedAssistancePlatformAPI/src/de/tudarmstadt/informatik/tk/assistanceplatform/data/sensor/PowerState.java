package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "sensor_powerstate")
public class PowerState extends SensorData {
	public boolean isCharging;
	public float percent;
	
    @JsonProperty(value = "chargingState")
    public int charingStateOptional;
    
    @JsonProperty(value = "charingMode")
    public int charingModeOptional;
    
    @JsonProperty(value = "powerSaveMode")
    public boolean powerSaveModeOptional;

	public boolean getIsCharging() {
		return isCharging;
	}

	public void setIsCharging(boolean isCharging) {
		this.isCharging = isCharging;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public int getCharingStateOptional() {
		return charingStateOptional;
	}

	public void setCharingStateOptional(int charingStateOptional) {
		this.charingStateOptional = charingStateOptional;
	}

	public int getCharingModeOptional() {
		return charingModeOptional;
	}

	public void setCharingModeOptional(int charingModeOptional) {
		this.charingModeOptional = charingModeOptional;
	}

	public boolean getPowerSaveModeOptional() {
		return powerSaveModeOptional;
	}

	public void setPowerSaveModeOptional(boolean powerSaveModeOptional) {
		this.powerSaveModeOptional = powerSaveModeOptional;
	}
}
