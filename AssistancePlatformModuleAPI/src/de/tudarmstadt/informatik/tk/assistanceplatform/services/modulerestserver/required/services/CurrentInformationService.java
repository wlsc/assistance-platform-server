package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.required.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.GsonBuilder;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.IInformationProvider;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.required.services.resources.ServiceResourcesFactory;

@Path("/information")
public class CurrentInformationService {
	@GET
	@Path("/current/user:{userid}/device:{deviceid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String current(@PathParam("userid") long userId, @PathParam("deviceid") long deviceId ) {
		ModuleInformationCard card = getCurrentInformationCard(userId, deviceId);
		
		String result = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create().toJson(card);

		return result;
	}
	
	private ModuleInformationCard getCurrentInformationCard(long userId, long deviceId) {
		return informationProvider().currentModuleInformationForUserAndDevice(userId, deviceId);
	}
	
	private IInformationProvider informationProvider() {
		return ServiceResourcesFactory.getInstance().getAssistanceModule().getInformationProvider();
	}
}
