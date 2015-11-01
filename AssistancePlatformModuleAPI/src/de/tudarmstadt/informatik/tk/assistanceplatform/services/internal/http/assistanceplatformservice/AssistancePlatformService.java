package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.ModuleLocalizationRequest;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.ModuleRegistrationRequest;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.SendMessageRequest;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.ModuleActivationsResponse;

public interface AssistancePlatformService {
	@POST("/modules/register")
	void register(@Body ModuleRegistrationRequest body, Callback<Void> callback);
	
	@POST("/modules/update")
	void update(@Body ModuleRegistrationRequest body, Callback<Void> callback);
	
	@POST("/modules/localize")
	void localize(@Body ModuleLocalizationRequest body, Callback<Void> callback);
	
	@POST("/action/sendmessage")
	void sendMessage(@Body SendMessageRequest body, Callback<Void> callback);
	
	@GET("/modules/activations/{moduleId}")
	void getModuleActivationsByUsers(@Path("moduleId") String moduleId, Callback<ModuleActivationsResponse> callback);
}