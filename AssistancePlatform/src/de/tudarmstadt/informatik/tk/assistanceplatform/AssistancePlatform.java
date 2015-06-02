package de.tudarmstadt.informatik.tk.assistanceplatform;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.GeographicPosition;
import de.tudarmstadt.informatik.tk.assistanceplatform.moduleregistry.ModuleRegistry;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.EventDispatcher;

public class AssistancePlatform {
	public static void main(String[] args) {
		//// Schematischer Ablauf
		
		// Module regsitry erstellen
		ModuleRegistry moduleRegistry = new ModuleRegistry();
		
		//moduleRegistry.registerModule(registration);
		
		// Generate event
		GeographicPosition gps = new GeographicPosition(-1, 10, 11);
		
		// Event an platform schicken
		EventDispatcher eventDispatcher = new EventDispatcher(moduleRegistry);
		
		eventDispatcher.dispatchEvent(gps);
		
		// ---> HotPlacesDataModule ---> HotPlacesAssistanceModule ---> Zurück an Client
	}
}