package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.required.services;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.ModuleInformationCard;

@Path("/information")
public class CurrentInformationService {
	@GET
	@Path("/current/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String test(@PathParam("userid") long userId ) {
		ModuleInformationCard card = new ModuleInformationCard();
		card.moduleId = "test";
		card.timestamp = new Date();
		
		return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create().toJson(card);
	}
}
