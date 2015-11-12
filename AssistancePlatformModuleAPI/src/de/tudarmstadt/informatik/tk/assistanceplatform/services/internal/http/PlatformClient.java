package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http;


import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.LocalizedModuleBundleInformation;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.ModuleBundle;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.ModuleBundleInformation;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.AssistancePlatformService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.ModuleLocalizationRequest;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.ModuleRegistrationRequest;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.SendMessageRequest;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.CassandraServiceConfigResponse;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.response.ModuleActivationsResponse;

public class PlatformClient {
	private final static Logger logger = Logger.getLogger(PlatformClient.class);
	
	AssistancePlatformService service;
	
	public PlatformClient(String urlAndPort) {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://" + urlAndPort).build();
		
		service = restAdapter.create(AssistancePlatformService.class);
	}
	
	public PlatformClient(AssistancePlatformService service) {
		this.service = service;
	}
	
	public void sendMessage(SendMessageRequest request, Consumer<Void> onSuccess, Consumer<Void> onError) {
		Callback<Void> callback = new Callback<Void>() {

			@Override
			public void failure(RetrofitError error) {
				logger.warn("Failed to send action request " + errorFromRetrofit(error));
				onError.accept(null);
			}

			@Override
			public void success(Void arg0, Response arg1) {
				logger.info("Successfully registered module on platform");
				
				onSuccess.accept(null);
			}
		};
		
		service.sendMessage(request, callback);
	}
	
	public void registerModule(ModuleBundle bundle, Consumer<Void> onSuccess, Consumer<Void> onError, boolean tryUpdateOnFailure) {		
		Callback<Void> callback = new Callback<Void>() {

			@Override
			public void failure(RetrofitError error) {
				logger.warn("Failed to register module on platform " + errorFromRetrofit(error));
				
				if(tryUpdateOnFailure) {
					logger.info("-> Try update module information");
					
					updateModule(bundle, onSuccess, onError);
				} else {
					onError.accept(null);
				}
			}

			@Override
			public void success(Void arg0, Response arg1) {
				logger.info("Successfully registered module on platform");
				
				onSuccess.accept(null);
			}
		};
		
		service.register(bundleToRequest(bundle), callback);
	}
	
	public void updateModule(ModuleBundle bundle, Consumer<Void> onSuccess, Consumer<Void> onError) {
		Callback<Void> callback = new Callback<Void>() {

			@Override
			public void failure(RetrofitError error) {
				logger.warn("Failed to update module information on platform " + errorFromRetrofit(error));
				
				onError.accept(null);
			}

			@Override
			public void success(Void arg0, Response arg1) {
				logger.info("Successfully updated module information on platform");
				
				onSuccess.accept(null);
			}
		};
		
		service.update(bundleToRequest(bundle), callback);
	}
	
	public void localizeModule(ModuleBundle bundle, Consumer<Void> onError) {
		Callback<Void> callback = new Callback<Void>() {

			@Override
			public void failure(RetrofitError error) {
				logger.warn("Failed to localize module information on platform " + errorFromRetrofit(error));
				
				onError.accept(null);
			}

			@Override
			public void success(Void arg0, Response arg1) {
				logger.info("Successfully localized module information on platform");
			}
		};
		
		for(ModuleLocalizationRequest locRequest : bundleLocalizationRequests(bundle)) {
			service.localize(locRequest, callback);
		}
	}
	
	public void getUserActivationsForModule(String moduleId, Consumer<long[]> activatedUserIdsCallback) {
		Callback<ModuleActivationsResponse> callback = new Callback<ModuleActivationsResponse>() {

			@Override
			public void failure(RetrofitError error) {
				logger.warn("Failed to pull user who activated the module " + errorFromRetrofit(error));
			}

			@Override
			public void success(ModuleActivationsResponse arg0, Response arg1) {
				logger.info("Pulled " + arg0.activatedUserIds.length + " user activations.");
				
				activatedUserIdsCallback.accept(arg0.activatedUserIds);
			}
		};
		
		service.getModuleActivationsByUsers(moduleId , callback);
	}
	
	/**
	 * BLOCKING! Get Service Call for Database Configuration
	 * @param moduleId
	 * @return
	 */
	public CassandraServiceConfigResponse getDatabaseService(String moduleId) {		
		return service.getCassandraServiceConfig(moduleId);
	}
	
	private ModuleRegistrationRequest bundleToRequest(ModuleBundle bundle) {
		String id = bundle.getModuleId();
		ModuleBundleInformation bundleInfo = bundle.getBundleInformation();
		
		ModuleRegistrationRequest request = new ModuleRegistrationRequest(id, 
				bundleInfo.englishModuleBundleInformation.name,
				bundleInfo.englishModuleBundleInformation.logoUrl, 
				bundleInfo.englishModuleBundleInformation.descriptionShort, 
				bundleInfo.englishModuleBundleInformation.descriptionLong, 
				bundleInfo.requiredCapabilities, 
				bundleInfo.optionalCapabilites, 
				bundleInfo.copyright, 
				bundleInfo.administratorEmail,
				bundleInfo.supportEmail,
				bundle.getRestContactAddress()
				);
		
		return request;
	}
	
	private Set<ModuleLocalizationRequest> bundleLocalizationRequests(ModuleBundle bundle) {
		Set<Entry<String, LocalizedModuleBundleInformation>> localizations = bundle.getBundleInformation().getLocalizedModuleBundleInformations();
		
		Set<ModuleLocalizationRequest> requests = new HashSet<>();
		
		if(localizations != null) {
			for(Entry<String, LocalizedModuleBundleInformation> localization : localizations) {
				String languageCode = localization.getKey();
				LocalizedModuleBundleInformation loc = localization.getValue();
				requests.add(new ModuleLocalizationRequest(
						languageCode, 
						bundle.getModuleId(), 
						loc.name, 
						loc.logoUrl, 
						loc.descriptionShort, 
						loc.descriptionLong));
			}
		}
		
		return requests;
	}
	
	private String errorFromRetrofit(RetrofitError error) {
		if(error.getResponse() != null) {
			return new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
		}
		
		return "unknown error";
	}
}
