package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response;

/**
 * This response comes back from the platform and contains configuration information about several
 * services (e.g. Spark, Cassandra)
 * 
 * @author bjeutter
 *
 */
public class ServiceConfigResponse {
  public String user;
  public String password;
  public String[] address;

  public ServiceConfigResponse(String user, String password, String[] address) {
    super();
    this.user = user;
    this.password = password;
    this.address = address;
  }
}
