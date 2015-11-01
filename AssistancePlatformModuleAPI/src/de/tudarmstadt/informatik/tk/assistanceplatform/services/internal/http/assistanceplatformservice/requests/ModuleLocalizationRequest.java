package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests;

import com.google.gson.annotations.Expose;

/**
 * Request data fired to tell the platform information in another language.
 * @author bjeutter
 */
public class ModuleLocalizationRequest {
	@Expose
	private String languageCode;
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
	
	public ModuleLocalizationRequest(String languageCode, String id,
			String name, String logoUrl, String descriptionShort,
			String descriptionLong) {
		super();
		this.languageCode = languageCode;
		this.id = id;
		this.name = name;
		this.logoUrl = logoUrl;
		this.descriptionShort = descriptionShort;
		this.descriptionLong = descriptionLong;
	}
}