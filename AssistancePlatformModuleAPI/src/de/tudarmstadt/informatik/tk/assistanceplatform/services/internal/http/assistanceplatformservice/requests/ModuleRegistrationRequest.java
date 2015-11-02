package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Capability;

/**
 * Request data that is sent to the platform to register a module.
 * @author bjeutter
 *
 */
public class ModuleRegistrationRequest {
	public String id;
	public String name;
	public String logoUrl;
	public String descriptionShort;
	public String descriptionLong;
	public Capability[] requiredCaps;
	public Capability[] optionalCaps;
	public String copyright;
	public String administratorEmail;
	public String supportEmail;
	/**
	 * This can be something like IP:PORT or just PORT. In the last case the platform will resolve by getting the requestors IP and appending the Port.
	 */
	public String restContactAddress;
	
	public ModuleRegistrationRequest(String id, String name, String logoUrl,
			String descriptionShort, String descriptionLong,
			Capability[] requiredCaps, Capability[] optionalCaps, String copyright,
			String administratoEmail, String supportEmail, String restContactAddress) {
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
		this.supportEmail = supportEmail;
		this.restContactAddress = restContactAddress;
	}
}