package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;

@Path("/example")
public class ExampleService {
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray test() {
		return new JSONArray( Arrays.asList(
			"a",
			"simple",
			"example"
		));
	}
}
