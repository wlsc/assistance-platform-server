package de.tudarmstadt.informatik.tk.assistanceplatform.moduleregistry;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.ModuleProxy;

/**
 * This class holds information about a module registration
 * @author bjeutter
 */
public class ModuleRegistration {
	private DeviceCapability[] requiredCapabilites;
	private DeviceCapability[] optionalCapabilites;
	private ModuleProxy moduleProxy;
	
	public ModuleRegistration(ModuleProxy proxy) {
		this(null, null);
	}
	
	public ModuleRegistration(DeviceCapability[] requiredCapabilites, DeviceCapability[] optionalCapabilites) {
	//	this.moduleProxy = proxy;
		this.requiredCapabilites = requiredCapabilites;
		this.optionalCapabilites = optionalCapabilites;
	}
	
	public String getId() {
		return moduleProxy.getModuleId();
	}
	
	public ModuleProxy getProxy() {
		return moduleProxy;
	}
	
	public DeviceCapability[] getRequiredCapabilites() {
		return requiredCapabilites;
	}
	
	public DeviceCapability[] getOptionalCapabilites() {
		return optionalCapabilites;
	}
}