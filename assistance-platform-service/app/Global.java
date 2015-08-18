import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;
import periodic.ModuleAliveChecker;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;

public class Global extends GlobalSettings {
	@Override
	public void onStart(Application app) {
		schedulePeriodicModuleCheck();
	}

	private void schedulePeriodicModuleCheck() {
		    ModuleAliveChecker aliveChecker = new ModuleAliveChecker();
		    
		    Akka.system().scheduler().schedule(Duration.create(0, TimeUnit.MILLISECONDS), //Initial delay 0 milliseconds
		    	    Duration.create(30, TimeUnit.MINUTES),     //Frequency 30 minutes
		    	    new Runnable() {
	                    @Override
	                    public void run() {
	                        aliveChecker.checkModulesAliveStati();
	                    }
	                },
	                Akka.system().dispatcher()
		    	);F
	  }
}