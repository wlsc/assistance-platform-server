package de.tudarmstadt.informatik.tk.assistanceplatform.data.virtualsensor.labels;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;

public class LabelAction extends SensorData {
	public String UUID;
	public String startTime;
	public String endTime;
	
	public Location startLocation;
	
	public Location endLocation;
	
	public String[] tags;
	
	public String label;
	public long labelClickTime;
	public int labelClickIndex;
	public String labelFirstTime;
	public String labelTime;
	public boolean isStationary;
	public boolean isModified;
	public boolean isRemoved;
	public boolean isUserCreated;
	public String referenceSplitUUID;
	public String referenceMergeUUID;
	
	public LabelAction() {
	}
}