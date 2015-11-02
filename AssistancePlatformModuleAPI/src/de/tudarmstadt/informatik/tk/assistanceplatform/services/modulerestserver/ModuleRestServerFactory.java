package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver;


public class ModuleRestServerFactory {
	private static ModuleRestServer instance;
	
	public static ModuleRestServer getInstance() {
		if (instance == null) {
			return new ModuleRestServer();
		}

		return instance;
	}
}
