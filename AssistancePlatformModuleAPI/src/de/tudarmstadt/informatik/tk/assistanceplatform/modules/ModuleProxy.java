package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

public abstract class ModuleProxy {

	public String getModuleId() {
		return getClass().getName();
	}

}