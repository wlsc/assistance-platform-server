package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ModuleBundleInformation {
	/**
	 * Localized english module bundle information (base)
	 */
	public final LocalizedModuleBundleInformation englishModuleBundleInformation;
	
	/**
	 * Definition of the required capabilites of the client in order to run this module
	 */
	public final Capability[] requiredCapabilities;
	
	/**
	 * Definition of the optional capabilites that can be used to use this module to 100%
	 */
	public final Capability[] optionalCapabilites;

	/**
	 * Who has the copyright for this module?
	 */
	public final String copyright;
	
	public final String administratorEmail;
	
	private Map<String, LocalizedModuleBundleInformation> localizedModuleBundleInformations;
	
	public ModuleBundleInformation(String name, String logoUrl,
			String descriptionShort, String descriptionLong,
			Capability[] requiredCapabilities, Capability[] optionalCapabilites,
			String copyright, String administratorEmail) {
		this.englishModuleBundleInformation = new LocalizedModuleBundleInformation(name, logoUrl, descriptionShort, descriptionLong);
		
		this.requiredCapabilities = requiredCapabilities;
		this.optionalCapabilites = optionalCapabilites;
		this.copyright = copyright;
		
		this.administratorEmail = administratorEmail;
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