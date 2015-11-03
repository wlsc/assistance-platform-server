package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * Container for passing a mapping from servlet to path to the module rest server.
 * @author bjeutter
 *
 */
public class MappedServlet {
	private ServletHolder servletHolder;
	private String path;

	public MappedServlet(String path, Class... jaxifiedServiceClasses) {
		//this(new ServletHolder(new ServletContainer(
			//	new PackagesResourceConfig(jaxifiedServiceClass
				//		.getPackage().getName()))), path);
		
		this(new ServletHolder(new ServletContainer( new ClassNamesResourceConfig(jaxifiedServiceClasses) )), path);
	}
	
	/*private Application getApplication() {
		Application app = new Application();
		
	}*/

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