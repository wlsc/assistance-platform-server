package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ModuleBundleInformation {
	public final LocalizedModuleBundleInformation englishModuleBundleInformation;
	
	public final String[] requiredCapabilities;
	public final String[] optionalCapabilites;

	public final String copyright;
	
	private Map<String, LocalizedModuleBundleInformation> localizedModuleBundleInformations;
	
	public ModuleBundleInformation(String name, String logoUrl,
			String descriptionShort, String descriptionLong,
			String[] requiredCapabilities, String[] optionalCapabilites,
			String copyright) {
		this.englishModuleBundleInformation = new LocalizedModuleBundleInformation(name, logoUrl, descriptionShort, descriptionLong);
		
		this.requiredCapabilities = requiredCapabilities;
		this.optionalCapabilites = optionalCapabilites;
		this.copyright = copyright;
	}
	
	/**
	 * Localizes this module information
	 * @param languageCode ISO 639-1 Code
	 * @param localization The object which contains niformation about the localiaztion
	 */
	public void localize(String languageCode, LocalizedModuleBundleInformation localization) {
		if(localizedModuleBundleInformations == null) {
			localizedModuleBundleInformations = new HashMap<>();
		}
		
		localizedModuleBundleInformations.put(languageCode, localization);
	}
	
	public Set<Entry<String, LocalizedModuleBundleInformation>> getLocalizedModuleBundleInformations() {
		return localizedModuleBundleInformations.entrySet();
	}
}