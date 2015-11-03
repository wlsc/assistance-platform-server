package de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.required;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.MappedServlet;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.required.services.CurrentInformationService;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.required.services.ExampleService;

public class RequiredRestEndpointsFactory {
	public RequiredRestEndpointsFactory() {
	}

	public Collection<MappedServlet> getRequiredServlets() {
		List<MappedServlet> mappedServlets = new LinkedList<>();

		mappedServlets.add(new MappedServlet("", ExampleService.class,
				CurrentInformationService.class));

		return mappedServlets;
	}
}