package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response;


/**
 * The response that comes from the platform after asking for the module activations (of the users).
 * @author bjeutter
 */
public class ModuleActivationsResponse {
	public long[] activatedUserIds;

	public ModuleActivationsResponse(long[] activatedUserIds) {
		super();
		this.activatedUserIds = activatedUserIds;
	}
}
