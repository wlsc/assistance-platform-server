package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor;

import com.datastax.driver.mapping.annotations.Table;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.typemapping.TypeNameForAssistance;

@Table(name = "sensor_call_log")
@TypeNameForAssistance(name = "call_log")
public class CallLogEntry extends SensorData {
	public long callId;
	public int callType;
	public String name;
	public String number;
	public long date;
	public long duration;
	public boolean isNew;
	public boolean isUpdated;
	public boolean isDeleted;
	
	public CallLogEntry() {}

	public CallLogEntry(long callId, int callType, String name, String number,
			long date, long duration, boolean isNew, boolean isUpdated,
			boolean isDeleted) {
		super();
		this.callId = callId;
		this.callType = callType;
		this.name = name;
		this.number = number;
		this.date = date;
		this.duration = duration;
		this.isNew = isNew;
		this.isUpdated = isUpdated;
		this.isDeleted = isDeleted;
	}



	public long getCallId() {
		return callId;
	}

	public void setCallId(long callId) {
		this.callId = callId;
	}

	public int getCallType() {
		return callType;
	}

	public void setCallType(int callType) {
		this.callType = callType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public boolean isUpdated() {
		return isUpdated;
	}

	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
