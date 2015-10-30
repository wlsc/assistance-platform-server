package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver;

import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class MappedServlet {
	private ServletHolder servletHolder;
	private String path;

	public MappedServlet(Class jaxifiedServiceClass, String path) {
		this(new ServletHolder(new ServletContainer(
				new PackagesResourceConfig(jaxifiedServiceClass
						.getPackage().getName()))), path);
	}

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