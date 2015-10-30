package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.standard.user;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserModuleActivationService {
	@POST
	@Path("/activate")
	@Produces(MediaType.APPLICATION_JSON)
	public String activate() {
		return "{'user' : 'yes'}";
	}
}
