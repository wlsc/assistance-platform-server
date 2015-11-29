package de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * POJO for providing the current (worthy) information for a user provided by a
 * module.
 * 
 * @author bjeutter
 *
 */
public class ModuleInformationCard {
	@Expose
	private String moduleId;

	public Date timestamp;

	public String payload;

	/**
	 * Just for serialization purpose!
	 */
	public ModuleInformationCard() {
		timestamp = new Date(0);
	}

	public ModuleInformationCard(String moduleId, Date timestamp) {
		this.moduleId = moduleId;
		this.timestamp = timestamp;
	}

	public ModuleInformationCard(String moduleId) {
		this(moduleId, new Date());
	}

	public String getModuleId() {
		return moduleId;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}