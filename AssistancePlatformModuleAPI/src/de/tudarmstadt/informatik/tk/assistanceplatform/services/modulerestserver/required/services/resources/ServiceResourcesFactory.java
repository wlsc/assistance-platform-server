package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.required.services.resources;

public class ServiceResourcesFactory {
  private static ServiceResources serviceResources;

  public static ServiceResources getInstance() {
    return serviceResources;
  }

  public static void setInstance(ServiceResources sr) throws Exception {
    setInstance(sr, false);
  }

  public static void setInstance(ServiceResources sr, boolean override) throws Exception {
    if (serviceResources != null && !override) {
      throw new Exception(
          "Instance is already set! If you are sure, then use the override parameter.");
    }

    serviceResources = sr;
  }
}
