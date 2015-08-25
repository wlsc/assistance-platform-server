package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests;

import com.google.gson.annotations.Expose;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Capability;

public class ModuleRegistrationRequest {
	@Expose
	private String id;
	@Expose
	private String name;
	@Expose
	private String logoUrl;
	@Expose
	private String descriptionShort;
	@Expose
	private String descriptionLong;
	@Expose
	private Capability[] requiredCaps;
	@Expose
	private Capability[] optionalCaps;
	@Expose
	private String copyright;
	@Expose
	private String administratorEmail;
	
	public ModuleRegistrationRequest(String id, String name, String logoUrl,
			String descriptionShort, String descriptionLong,
			Capability[] requiredCaps, Capability[] optionalCaps, String copyright,
			String administratoEmail) {
		super();
		this.id = id;
		this.name = name;
		this.logoUrl = logoUrl;
		this.descriptionShort = descriptionShort;
		this.descriptionLong = descriptionLong;
		this.requiredCaps = requiredCaps;
		this.optionalCaps = optionalCaps;
		this.copyright = copyright;
		this.administratorEmail = administratoEmail;
	}
}