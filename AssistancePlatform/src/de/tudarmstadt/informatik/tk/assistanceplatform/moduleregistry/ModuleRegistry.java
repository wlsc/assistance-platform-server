package de.tudarmstadt.informatik.tk.assistanceplatform.moduleregistry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.tudarmstadt.informatik.tk.assistanceplatform.moduleregistry.exceptions.ModuleRegistrationException;

/**
 * @author bjeutter
 * This class keeps track of registered modules on the central platform.
 */
public class ModuleRegistry {
  /**
   * List of current registrations
   */
  private Map<String, ModuleRegistration> registrations = new HashMap<>();
  
  /**
   * Returns the current registrations as a list
   */
  public Collection<ModuleRegistration> getRegisterdModules() {
	  return registrations.values();
  }
  
  /**
   * Tries to register a module
 * @throws ModuleRegistrationException 
   */
   public void registerModule(ModuleRegistration registration) throws ModuleRegistrationException {
     if(isRegistrationValid(registration)) {
    	 this.registrations.put(registration.getId(), registration);
    	 
    	 // TODO: Setup connection or something like that
     }
   }
  
  /**
   * Checks if a registration is valid
 * @throws ModuleRegistrationException 
   */
   private boolean isRegistrationValid(ModuleRegistration registration) throws ModuleRegistrationException {
     if(isModuleRegistered(registration.getId())) {
       throw new ModuleRegistrationException("Module ID already registered.");
     }
     
     // TODO: CHeck for connectivity or something like that
     
     return true;
  }
   
  /**
   * Deregisters a module
   */
   public void deregisterModule(ModuleRegistration registration) {
     deregisterModule(registration.getId());
   }
   
   
   /**
    * Deregister a module with certain ID
    */
   public void deregisterModule(String id) {
	   registrations.remove(id);
	   // TODO: Remove connection or something like that :D
   }
   
   /**
    * Checks if a module with given ID is registered
    * @return TRUE if the module is registered, otherwise false
    */
   public boolean isModuleRegistered(String id) {
	   return registrations.containsKey(id);
   }
}