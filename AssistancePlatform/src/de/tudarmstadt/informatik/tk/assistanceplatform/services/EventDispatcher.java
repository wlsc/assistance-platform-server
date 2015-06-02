package de.tudarmstadt.informatik.tk.assistanceplatform.services;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.Event;
import de.tudarmstadt.informatik.tk.assistanceplatform.moduleregistry.ModuleRegistry;

public class EventDispatcher {
	private ModuleRegistry moduleRegistry;
	
	public EventDispatcher(ModuleRegistry registry) {
		this.moduleRegistry = registry;
	}
	
	public void dispatchEvent(Event event) {
		
	}
}