package periodic;

import play.Logger;

public class ModuleAliveChecker {
	public ModuleAliveChecker() {
	}
	
	public void checkModulesAliveStati() {
		Logger.info("Check modules alive stati ... ");
		
		// TODO: Find modules where last alive message is older than XY
		
		// TODO: if isAlive was true and the module didn't give an alive message in the last XY minutes,
		// TODO: then send an email to the administrator and set isAlive = false
	}
}
