package de.tudarmstadt.informatik.tk.assistanceplatform.information;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import models.ActiveAssistanceModule;
import play.Logger;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;

public class CurrentModuleInformationAggregator {
	private long userId;
	
	private long deviceId;
	
	private ActiveAssistanceModule[] activeModules;
	
	private WSClient ws;
	
	public CurrentModuleInformationAggregator(long userId, long deviceId, ActiveAssistanceModule[] activeModules) {
		this.userId = userId;
		this.deviceId = deviceId;
		this.activeModules = activeModules;
		this.ws = WS.client();
	}
	
	public Promise<List<ModuleInformationCard>> requestCurrentInformationCards() {
		// 2. Schleife den Request an alle gefundenen Module (bzw. deren REST Server) weiter
		Promise<List<WSResponse>> promiseResults = Promise
				.sequence(collectResponsePromises(activeModules));
		
		// 3. Sammle die Resultate
		Promise<List<ModuleInformationCard>> result = promiseResults		
		.map((responseList) -> {
			List<ModuleInformationCard> processedCards = processWebResponses(responseList);
			return processedCards;
		});
		
		return result;
		
		
		// 4. Priorisiere die Resultate
	}
	
	private List<Promise<WSResponse>> collectResponsePromises(ActiveAssistanceModule[] modules) {
		List<Promise<WSResponse>> promises = new LinkedList<>();
		
		for(ActiveAssistanceModule m : modules) {
			Promise<WSResponse> response = startRequestToModule(m);
			
			promises.add(response);
		}
		
		return promises;
	}
	
	private Promise<WSResponse> startRequestToModule(ActiveAssistanceModule module) {
		return prepareRequestToModule(module).get().recover((t) -> null);
	}
	
	private WSRequest prepareRequestToModule(ActiveAssistanceModule module) {
		WSRequest request = ws.url(getURLForRequest(module));
		
		
		request.setRequestTimeout(1);
		// TODO: Set request parameters?
		// TODO: Auth against module?
		// TODO: SSL? Probably not because inside cluster which is not reachable from outside
		
		return request;
	}
	
	private String getURLForRequest(ActiveAssistanceModule module) {
		String url = "http://" + module.restContactAddress + "/rest/information/current/user:" + userId + "/device:" + deviceId;
		return url;
	}
	
	private List<ModuleInformationCard> processWebResponses(List<WSResponse> responses) {
		ObjectMapper objMapper = new ObjectMapper();
		
		return responses.parallelStream()
			.filter((w) -> w != null)
			.map((wsr) -> {
			ModuleInformationCard card = null;

			if(wsr.getStatus() == 200) {
				JsonNode json = wsr.asJson();
				try {
					card = objMapper.treeToValue(json, ModuleInformationCard.class);
				} catch (Exception e) {

					Logger.error("Couldn't parse response from module REST server. Expected JSON but got: " + json, e);
				}
			} else {
				Logger.error("Errornous response from module REST server: " + wsr.getBody());
			}
			
			return card;
		})
		.filter((c) -> c != null)
		.collect(Collectors.toList());
	}
}
