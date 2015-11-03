package de.tudarmstadt.informatik.tk.assistanceplatform.information;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import models.ActiveAssistanceModule;
import persistency.UserModuleActivationPersistency;
import play.Logger;
import play.libs.F.Callback;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;

public class CurrentModuleInformationAggregator {
	private long userId;
	
	private WSClient ws;
	
	public CurrentModuleInformationAggregator(long userId) {
		this.userId = userId;
		this.ws = WS.client();
	}
	
	public void requestCurrentInformationCards(Callback<List<ModuleInformationCard>> successCallback) {
		// 1. Suche alle aktivierten MOdule von dem User
		ActiveAssistanceModule[] activeModules = getActivatedModuleEndpoints(this.userId);
		
		// 2. Schleife den Request an alle gefundenen Module (bzw. deren REST Server) weiter
		Promise<List<WSResponse>> promiseResults = Promise.sequence(collectResponsePromises(activeModules));
		promiseResults.onRedeem((responseList) -> {
			List<ModuleInformationCard> processedCards = processWebResponses(responseList);
			successCallback.invoke(processedCards);
		});
		
		// 3. Sammle die Resultate
		// 4. Priorisiere die Resultate
	}
	
	private ActiveAssistanceModule[] getActivatedModuleEndpoints(long userId) {
		return UserModuleActivationPersistency.activatedModuleEndpointsForUser(userId);
	}
	
	private Promise<WSResponse>[] collectResponsePromises(ActiveAssistanceModule[] modules) {
		List<Promise<WSResponse>> promises = new LinkedList<>();
		
		for(ActiveAssistanceModule m : modules) {
			promises.add(startRequestToModule(m));
		}
		
		Promise<WSResponse>[] promArray = null;
		return promises.toArray(promArray);
	}
	
	private Promise<WSResponse> startRequestToModule(ActiveAssistanceModule module) {
		return prepareRequestToModule(module).get();
	}
	
	private WSRequest prepareRequestToModule(ActiveAssistanceModule module) {
		WSRequest request = ws.url(getURLForRequest(module));
		
		// TODO: Set request parameters?
		// TODO: Auth against module?
		// TODO: SSL? Probably not because inside cluster which is not reachable from outside
		
		return request;
	}
	
	private String getURLForRequest(ActiveAssistanceModule module) {
		String url = "http://" + module.restContactAddress + "/rest/information/current";
		return url;
	}
	
	private List<ModuleInformationCard> processWebResponses(List<WSResponse> responses) {
		return responses.parallelStream().map((wsr) -> {
			ModuleInformationCard card = null;
			
			if(wsr.getStatus() == 200) {
				card = null; // TODO!!
			} else {
				Logger.error("Errornous response from module REST server: " + wsr.getBody());
			}
			
			return card;
		})
		.filter((c) -> c != null)
		.collect(Collectors.toList());
	}
}
