package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver;

import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * Container for passing a mapping from servlet to path to the module rest server.
 * 
 * @author bjeutter
 *
 */
public class MappedServlet {
  private ServletHolder servletHolder;
  private String path;

  public MappedServlet(String path, Class... jaxifiedServiceClasses) {
    this(new ServletHolder(
        new ServletContainer(new ClassNamesResourceConfig(jaxifiedServiceClasses))), path);
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
