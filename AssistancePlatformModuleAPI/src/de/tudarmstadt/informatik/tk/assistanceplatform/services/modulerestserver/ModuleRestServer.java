package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.required.RequiredRestEndpointsFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.required.services.ExampleService;

/**
 * This class provides the basic structure for the module-owned rest server for
 * serving basic requests from the platform. On top servlets can be set for
 * custom endpoints.
 * 
 * @author bjeutter
 *
 */
public class ModuleRestServer {
	private static final int DEFAULT_PORT = 21314;
	
	private Server server;
	private ServletContextHandler context;

	public ModuleRestServer() {
		server = new Server(DEFAULT_PORT);
		
		context = new ServletContextHandler();
		context.setContextPath("/");
		server.setHandler(context);
		
		bindStandardServletsToContext();
	}
	
	public void setCustomServlets(Collection<MappedServlet> customServlets) {
		bindCustomServletsToContext(customServlets);
	}
	
	public int getPort() {
		return ((ServerConnector)  server.getConnectors()[0]).getPort();
	}

	public void start() throws Exception {
		server.start();
	}
	
	private void bindStandardServletsToContext() {
		Collection<MappedServlet> standardServlets = new RequiredRestEndpointsFactory().getRequiredServlets();

		bindServletsBehindPath(context, standardServlets, "/rest");
	}
	
	private void bindCustomServletsToContext(Collection<MappedServlet> customServlets) {
		if(customServlets != null) {
			bindServletsBehindPath(context, customServlets, "/rest/custom");
		}
	}

	private void bindServletsBehindPath(ServletContextHandler context,
			Collection<MappedServlet> servlets, String path) {
		for (MappedServlet s : servlets) {
			s.getServletHolder().setInitParameter(
					"com.sun.jersey.api.json.POJOMappingFeature", "true");

			context.addServlet(s.getServletHolder(), path + s.getPath()
					+ "/*");
		}
	}
}
