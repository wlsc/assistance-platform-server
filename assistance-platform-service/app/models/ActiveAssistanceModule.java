package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This POJO represents an active assistance module. It contains information of modules that work in conjunction with the platform.
 */
@JsonIgnoreProperties("administratorEmail")
public class ActiveAssistanceModule {

	public String name;

	public String id;

	public String logoUrl;

	public String descriptionShort;
	public String descriptionLong;

	public String[] requiredCapabilities;
	public String[] optionalCapabilites;

	public String copyright;
	
	public String administratorEmail;

	public ActiveAssistanceModule(String name, String id, String logoUrl,
			String description_short, String description_long,
			String[] requiredCapabilites, String[] optionalCapabilities,
			String copyright, String administratorEmail) {

		this.name = name;

		this.id = id;

		this.logoUrl = logoUrl;

		this.descriptionShort = description_short;
		this.descriptionLong = description_long;

		this.requiredCapabilities = requiredCapabilites;
		this.optionalCapabilites = optionalCapabilities;

		this.copyright = copyright;
		
		this.administratorEmail = administratorEmail;
	}
}