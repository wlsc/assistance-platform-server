package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http;


import java.util.function.Consumer;
import java.util.function.Supplier;

import retrofit.Callback;

import org.apache.log4j.Logger;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.ModuleBundle;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.ModuleBundleInformation;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.AssistancePlatformService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.ModuleRegistrationRequest;

public class PlatformClient {
	private final static Logger logger = Logger.getLogger(PlatformClient.class);
	
	AssistancePlatformService service;
	
	public PlatformClient() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://localhost:9000").build();
		
		service = restAdapter.create(AssistancePlatformService.class);
	}
	
	public void registerModule(ModuleBundle bundle, Consumer<Void> onError, boolean tryUpdateOnFailure) {
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
		
		Callback<Void> callback = new Callback<Void>() {

			@Override
			public void failure(RetrofitError error) {
				logger.warn("Failed to register module on platform " + errorFromRetrofit(error));
				
				if(tryUpdateOnFailure) {
					logger.info("-> Try update module information");
					
					updateModule(bundle, onError);
				} else {
					onError.accept(null);
				}
			}

			@Override
			public void success(Void arg0, Response arg1) {
				logger.info("Successfully registered module on platform");
			}
		};
		
		service.register(bundleToRequest(bundle), callback);
	}
	
	public void updateModule(ModuleBundle bundle, Consumer<Void> onError) {
		Callback<Void> callback = new Callback<Void>() {

			@Override
			public void failure(RetrofitError error) {
				logger.warn("Failed to update module information on platform " + errorFromRetrofit(error));
				
				onError.accept(null);
			}

			@Override
			public void success(Void arg0, Response arg1) {
				logger.info("Successfully updated module information on platform");
			}
		};
		
		service.update(bundleToRequest(bundle), callback);
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
	
	private String errorFromRetrofit(RetrofitError error) {
		return new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
	}
}
