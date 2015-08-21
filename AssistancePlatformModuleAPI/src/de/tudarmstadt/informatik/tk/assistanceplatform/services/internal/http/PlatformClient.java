package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http;


import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.LocalizedModuleBundleInformation;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.ModuleBundle;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.ModuleBundleInformation;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.AssistancePlatformService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.ModuleLocalizationRequest;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.ModuleRegistrationRequest;

public class PlatformClient {
	private final static Logger logger = Logger.getLogger(PlatformClient.class);
	
	AssistancePlatformService service;
	
	public PlatformClient(String urlAndPort) {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://" + urlAndPort).build();
		
		service = restAdapter.create(AssistancePlatformService.class);
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
				bundleInfo.administratorEmail);
		
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
		return new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
	}
}
