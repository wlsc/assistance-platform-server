package de.tudarmstadt.informatik.tk.assistanceplatform.moduleregistry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.moduleregistry.exceptions.ModuleRegistrationException;

public class ModuleRegistryTest {

	private final ModuleRegistry registry = new ModuleRegistry();

	@Test
	public void simpleRegistrationTests() throws ModuleRegistrationException {
		assertEquals(registry.getRegisterdModules().size(), 0);

		ModuleRegistration registration1 = new ModuleRegistration(
				"de.studentassist.xy", "http://serveraddrs", null, null);
		registry.registerModule(registration1);
		assertEquals(1, registry.getRegisterdModules().size());

		ModuleRegistration registration2 = new ModuleRegistration(
				"de.hotzones.xy", "http://serveraddrs", null, null);
		registry.registerModule(registration2);
		assertEquals(2, registry.getRegisterdModules().size());
	}

	@Test(expected = ModuleRegistrationException.class)
	public void testFaultyRegistrationTest() throws ModuleRegistrationException {
		ModuleRegistration registration1 = new ModuleRegistration(
				"de.studentassist.xy", "http://serveraddrs", null, null);
		registry.registerModule(registration1);
		registry.registerModule(registration1);

		assertEquals(1, registry.getRegisterdModules().size());
	}
}
