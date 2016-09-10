import java.util.concurrent.TimeUnit;

import com.datastax.driver.core.exceptions.AuthenticationException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;

import periodic.ModuleAliveChecker;
import persistency.cassandra.CassandraSessionProxyFactory;
import persistency.cassandra.ConfiguredSensorPersistencyProxy;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import scala.concurrent.duration.Duration;
import controllers.RestController;
import de.tudarmstadt.informatik.tk.assistanceplatform.persistency.cassandra.CassandraSessionProxy;
import errors.APIError;

public class Global extends GlobalSettings {
	@Override
	public void onStart(Application app) {
		if (!play.Play.isTest()) {
			schedulePeriodicModuleCheck();
			while (proxySessionCheck() == null);
		}
	}

	private CassandraSessionProxy proxySessionCheck() {
		CassandraSessionProxy proxy = null;

		try {
			proxy = CassandraSessionProxyFactory.getSessionProxy();
		} catch (NoHostAvailableException e) {
			Logger.warn("Cassandra not up, waiting 2 seconds... ");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} catch (AuthenticationException e) {
			Logger.warn("Waiting 2secs for Cassandra to setup auth... ");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		return proxy;
	}

	private void schedulePeriodicModuleCheck() {
		ModuleAliveChecker aliveChecker = new ModuleAliveChecker();

		Akka.system().scheduler()
				.schedule(Duration.create(0, TimeUnit.MILLISECONDS), // Initial
																		// delay
																		// 0
																		// milliseconds
						Duration.create(30, TimeUnit.MINUTES), // Frequency 30
																// minutes
						(Runnable) aliveChecker::checkModulesAliveStati, Akka.system().dispatcher());
	}

	@Override
	public Promise<play.mvc.Result> onError(RequestHeader request, Throwable t) {
		return F.Promise.promise(() -> play.mvc.Results.internalServerError(RestController
                .errorInJson(new APIError(0, t.getMessage()))));
	}

	@Override
	public Promise<play.mvc.Result> onHandlerNotFound(RequestHeader request) {
		return F.Promise.promise(() -> play.mvc.Results.badRequest(RestController
                .errorInJson(new APIError(0,
                        "Handler not found. Invalid route."))));
	}

	@Override
	public Promise<play.mvc.Result> onBadRequest(RequestHeader request,
			String error) {
		return F.Promise.promise(() -> play.mvc.Results.badRequest(RestController
                .errorInJson(new APIError(0, error))));
	}
}