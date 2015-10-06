package de.tudarmstadt.informatik.tk.assistanceplatform.data.virtualsensor.labels;

import java.util.Set;

import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.Table;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.typemapping.TypeNameForAssistance;

@Table(name = "sensor_labelaction")
@TypeNameForAssistance(name = "label")
public class Label extends SensorData {
	public String UUID;
	public String startTime;
	public String endTime;
	
	@Frozen
	public Location startLocation;
	
	@Frozen
	public Location endLocation;
	
	public Set<String> tags;
	
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
	
	public Label() {
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Location getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}

	public Location getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(Location endLocation) {
		this.endLocation = endLocation;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public long getLabelClickTime() {
		return labelClickTime;
	}

	public void setLabelClickTime(long labelClickTime) {
		this.labelClickTime = labelClickTime;
	}

	public int getLabelClickIndex() {
		return labelClickIndex;
	}

	public void setLabelClickIndex(int labelClickIndex) {
		this.labelClickIndex = labelClickIndex;
	}

	public String getLabelFirstTime() {
		return labelFirstTime;
	}

	public void setLabelFirstTime(String labelFirstTime) {
		this.labelFirstTime = labelFirstTime;
	}

	public String getLabelTime() {
		return labelTime;
	}

	public void setLabelTime(String labelTime) {
		this.labelTime = labelTime;
	}

	public boolean getIsStationary() {
		return isStationary;
	}

	public void setIsStationary(boolean isStationary) {
		this.isStationary = isStationary;
	}

	public boolean getIsModified() {
		return isModified;
	}

	public void setIsModified(boolean isModified) {
		this.isModified = isModified;
	}

	public boolean getIsRemoved() {
		return isRemoved;
	}

	public void setIsRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public boolean getIsUserCreated() {
		return isUserCreated;
	}

	public void setIsUserCreated(boolean isUserCreated) {
		this.isUserCreated = isUserCreated;
	}

	public String getReferenceSplitUUID() {
		return referenceSplitUUID;
	}

	public void setReferenceSplitUUID(String referenceSplitUUID) {
		this.referenceSplitUUID = referenceSplitUUID;
	}

	public String getReferenceMergeUUID() {
		return referenceMergeUUID;
	}

	public void setReferenceMergeUUID(String referenceMergeUUID) {
		this.referenceMergeUUID = referenceMergeUUID;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
}