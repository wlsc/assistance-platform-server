package de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle;

public class LocalizedModuleBundleInformation {
	public LocalizedModuleBundleInformation(String name, String logoUrl,
			String descriptionShort, String descriptionLong) {
		this.name = name;
		this.logoUrl = logoUrl;
		this.descriptionShort = descriptionShort;
		this.descriptionLong = descriptionLong;
	}

	public final String name;
	
	public final String logoUrl;
	
	public final String descriptionShort;
	
	public final String descriptionLong;
}