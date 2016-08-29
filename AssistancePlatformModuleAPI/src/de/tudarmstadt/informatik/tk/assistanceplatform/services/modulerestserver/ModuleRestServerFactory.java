package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver;


public class ModuleRestServerFactory {
  private static ModuleRestServer instance;

  public static ModuleRestServer getInstance() {
    return instance;
  }

  public static ModuleRestServer createInstance(int port) {
    if (instance == null) {
      instance = new ModuleRestServer(port);
    }

    return instance;
  }
}
