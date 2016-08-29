package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.required.services.resources;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.AssistanceModule;

public class ServiceResources {
  private AssistanceModule assistanceModule;

  public ServiceResources(AssistanceModule assiModule) {
    this.assistanceModule = assiModule;
  }

  public AssistanceModule getAssistanceModule() {
    return this.assistanceModule;
  }
}
