package models;

public class AvailableAssistanceModule {
	public String name;
	public String id;
	public String[] requiredCapabilities;
	public String[] optionalCapabilites;
	
	public AvailableAssistanceModule(String name, String id, String[] requiredCapabilites, String[] optionalCapabilities) {
		this.name = name;
		this.id = id;
		this.requiredCapabilities = requiredCapabilites;
		this.optionalCapabilites = optionalCapabilities;
	}
}