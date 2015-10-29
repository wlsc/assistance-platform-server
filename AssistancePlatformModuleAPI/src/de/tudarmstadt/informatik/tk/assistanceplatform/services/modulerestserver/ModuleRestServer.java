package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.google.common.collect.Iterables;

/**
 * This class provides the basic structure for the module-owned rest server for serving basic requests from the platform.
 * On top servlets can be set for custom endpoints.
 * @author bjeutter
 *
 */
public class ModuleRestServer {
	private Server server;
	
	public ModuleRestServer(Collection<MappedServlet> customServlets) {
		server = new Server(21314);
		
		ServletContextHandler contextHandler = new ServletContextHandler(server, "/rest");
		
		combineServletsAndAddToContext(contextHandler, customServlets);
	}
	
	public void start() throws Exception {
		server.start();
		
		//System.out.println(((ServerConnector) server.getConnectors()[0]).getLocalPort());
	}
	
	private void combineServletsAndAddToContext(ServletContextHandler context, Collection<MappedServlet> customServlets) {
		Collection<MappedServlet> standardServlets = createStandardServlets();
		
		Iterable<MappedServlet> combined = customServlets != null ? Iterables.concat(standardServlets, customServlets) : standardServlets;
		
		for(MappedServlet s : combined) {
			context.addServlet(s.getServletHolder(), s.getPath());
		}
	}
	
	private Collection<MappedServlet> createStandardServlets() {
		List<MappedServlet> mappedServlets = new LinkedList<>();
		mappedServlets.add(createUserManagementServlet());
		return mappedServlets;
	}
	
	private MappedServlet createUserManagementServlet() {
		ServletHolder holder = new ServletHolder(UserManagementServlet.class);
		return new MappedServlet(holder, "/user");
	}
	
	public class MappedServlet {
		private ServletHolder servletHolder;
		private String path;
		
		public MappedServlet(ServletHolder servletHolder, String path) {
			super();
			this.servletHolder = servletHolder;
			this.path = path;
		}
		
		public ServletHolder getServletHolder() {
			return servletHolder;
		}

		public String getPath() {
			return path;
		}
	}
}
