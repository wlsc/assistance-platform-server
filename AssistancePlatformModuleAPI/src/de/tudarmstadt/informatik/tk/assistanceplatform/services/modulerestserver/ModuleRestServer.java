package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * This class provides the basic structure for the module-owned rest server for
 * serving basic requests from the platform. On top servlets can be set for
 * custom endpoints.
 * 
 * @author bjeutter
 *
 */
public class ModuleRestServer {
	private Server server;

	public ModuleRestServer(Collection<MappedServlet> customServlets) {
		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");

		server = new Server(21314);
		server.setHandler(context);

		combineServletsAndAddToContext(context, customServlets);
	}

	public void start() throws Exception {
		server.start();
		server.join();
	}

	private void combineServletsAndAddToContext(ServletContextHandler context,
			Collection<MappedServlet> customServlets) {
		Collection<MappedServlet> standardServlets = createStandardServlets();

		bindServletsBehindPath(context, standardServlets, "/rest");

		if (customServlets != null) {
			bindServletsBehindPath(context, customServlets, "/rest/custom");
		}
	}

	private void bindServletsBehindPath(ServletContextHandler context,
			Collection<MappedServlet> servlets, String path) {
		for (MappedServlet s : servlets) {
			s.getServletHolder().setInitParameter(
					"com.sun.jersey.api.json.POJOMappingFeature", "true");

			System.out.println(path + s.getPath());
			context.addServlet(s.getServletHolder(), path + s.getPath()
					+ "/*");
		}
	}

	private Collection<MappedServlet> createStandardServlets() {
		List<MappedServlet> mappedServlets = new LinkedList<>();
		mappedServlets.add(new MappedServlet(ExampleService.class, "/example"));
		return mappedServlets;
	}
}
