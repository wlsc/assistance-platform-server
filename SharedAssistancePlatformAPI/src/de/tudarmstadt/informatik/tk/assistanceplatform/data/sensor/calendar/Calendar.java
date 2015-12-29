package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.calendar;

import java.util.Date;
import java.util.Set;

import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.SensorData;

@Table(name = "sensor_calendar")
public class Calendar extends SensorData {
	public String eventId;
	public String calendarId;
	public Boolean allDay;
	public Integer availability; // -1: NotSupported, 0: Busy, 1: Free, 2: Tentative und 3: Unavailable.
	public String description;
	public Date startDate;
	public Date endDate; 
	public String location;
	public Integer status; // -1: none, 0: Tentative, 1: Confirmed, 2: Canceled,
	public String title;
	public String recurrenceRule;
	
	@Frozen
	public Set<Alarm> alarms; 

	public Boolean isDeleted;
	public String created;

	// Optional
	
	@JsonProperty(value = "URL")
	public String URLOptional;
	
	@JsonProperty(value = "isDetached")
	public Boolean isDetachedOptional;
	
	@JsonProperty(value = "lastModifiedDate")
	public String lastModifiedDateOptional;
	
	@JsonProperty(value = "duration")
	public String durationOptional;
	
	@JsonProperty(value = "originalAllDay")
	public Boolean originalAllDayOptional;
	
	@JsonProperty(value = "originalId")
	public String originalIdOptional;
	
	@JsonProperty(value = "originalInstanceTime")
	public Long originalInstanceTimeOptional;
	
	@JsonProperty(value = "recurrenceExceptionDate")
	public String recurrenceExceptionDateOptional;
	
	@JsonProperty(value = "recurrenceExceptionRule")
	public String recurrenceExceptionRuleOptional;
	
	@JsonProperty(value = "lastDate")
	public Long lastDateOptional;
	
	@JsonProperty(value = "recurrenceDate")
	public String recurrenceDateOptional;
	
	public Calendar() {}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	public Boolean getAllDay() {
		return allDay;
	}

	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}

	public Integer getAvailability() {
		return availability;
	}

	public void setAvailability(Integer availability) {
		this.availability = availability;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRecurrenceRule() {
		return recurrenceRule;
	}

	public void setRecurrenceRule(String recurrenceRule) {
		this.recurrenceRule = recurrenceRule;
	}

	public Set<Alarm> getAlarms() {
		return alarms;
	}

	public void setAlarms(Set<Alarm> alarms) {
		this.alarms = alarms;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getURLOptional() {
		return URLOptional;
	}

	public void setURLOptional(String uRLOptional) {
		URLOptional = uRLOptional;
	}

	public Boolean getIsDetachedOptional() {
		return isDetachedOptional;
	}

	public void setIsDetachedOptional(Boolean isDetachedOptional) {
		this.isDetachedOptional = isDetachedOptional;
	}

	public String getLastModifiedDateOptional() {
		return lastModifiedDateOptional;
	}

	public void setLastModifiedDateOptional(String lastModifiedDateOptional) {
		this.lastModifiedDateOptional = lastModifiedDateOptional;
	}

	public String getDurationOptional() {
		return durationOptional;
	}

	public void setDurationOptional(String durationOptional) {
		this.durationOptional = durationOptional;
	}

	public Boolean getOriginalAllDayOptional() {
		return originalAllDayOptional;
	}

	public void setOriginalAllDayOptional(Boolean originalAllDayOptional) {
		this.originalAllDayOptional = originalAllDayOptional;
	}

	public String getOriginalIdOptional() {
		return originalIdOptional;
	}

	public void setOriginalIdOptional(String originalIdOptional) {
		this.originalIdOptional = originalIdOptional;
	}

	public Long getOriginalInstanceTimeOptional() {
		return originalInstanceTimeOptional;
	}

	public void setOriginalInstanceTimeOptional(Long originalInstanceTimeOptional) {
		this.originalInstanceTimeOptional = originalInstanceTimeOptional;
	}

	public String getRecurrenceExceptionDateOptional() {
		return recurrenceExceptionDateOptional;
	}

	public void setRecurrenceExceptionDateOptional(
			String recurrenceExceptionDateOptional) {
		this.recurrenceExceptionDateOptional = recurrenceExceptionDateOptional;
	}

	public String getRecurrenceExceptionRuleOptional() {
		return recurrenceExceptionRuleOptional;
	}

	public void setRecurrenceExceptionRuleOptional(
			String recurrenceExceptionRuleOptional) {
		this.recurrenceExceptionRuleOptional = recurrenceExceptionRuleOptional;
	}

	public Long getLastDateOptional() {
		return lastDateOptional;
	}

	public void setLastDateOptional(Long lastDateOptional) {
		this.lastDateOptional = lastDateOptional;
	}

	public String getRecurrenceDateOptional() {
		return recurrenceDateOptional;
	}

	public void setRecurrenceDateOptional(String recurrenceDateOptional) {
		this.recurrenceDateOptional = recurrenceDateOptional;
	}
}