package models;

public class AvailableAssistanceModule {

	public String name;

	public String id;

	public String logoUrl;

	public String description_short;
	public String description_long;

	public String[] requiredCapabilities;
	public String[] optionalCapabilites;

	public String copyright;

	public AvailableAssistanceModule(String name, String id, String logoUrl,
			String description_short, String description_long,
			String[] requiredCapabilites, String[] optionalCapabilities,
			String copyright) {

		this.name = name;

		this.id = id;

		this.logoUrl = logoUrl;

		this.description_short = description_short;
		this.description_long = description_long;

		this.requiredCapabilities = requiredCapabilites;
		this.optionalCapabilites = optionalCapabilities;

		this.copyright = copyright;
	}
}