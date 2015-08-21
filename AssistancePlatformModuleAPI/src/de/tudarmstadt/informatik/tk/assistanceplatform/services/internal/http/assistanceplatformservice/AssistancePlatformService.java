package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.ModuleLocalizationRequest;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.ModuleRegistrationRequest;

public interface AssistancePlatformService {
	@POST("/modules/register")
	void register(@Body ModuleRegistrationRequest body, Callback<Void> callback);
	
	@POST("/modules/update")
	void update(@Body ModuleRegistrationRequest body, Callback<Void> callback);
	
	@POST("/modules/localize")
	void localize(@Body ModuleLocalizationRequest body, Callback<Void> callback);
}