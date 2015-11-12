package models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.util.Arrays;

import org.junit.Test;

import persistency.ActiveAssistanceModulePersistency;
import persistency.UserModuleActivationPersistency;

public class UserModuleActivationTest {
	@Test
	public void activationCreationTest() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				assertTrue(UserModuleActivationPersistency
						.create(new UserModuleActivation(10L, "xyz")));
				assertTrue(UserModuleActivationPersistency
						.create(new UserModuleActivation(10L, "xyz")));
			}
		});
	}

	@Test
	public void removeTest() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				UserModuleActivation activation = new UserModuleActivation(10L,
						"xyz");
				assertTrue(UserModuleActivationPersistency.create(activation));
				assertTrue(UserModuleActivationPersistency.remove(activation));
				assertFalse(UserModuleActivationPersistency.remove(activation));
				assertTrue(UserModuleActivationPersistency.create(activation));
			}
		});
	}

	@Test
	public void activatedModulesTest() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				ActiveAssistanceModule module = new ActiveAssistanceModule(
						"name", "id", "x", "x", "x", null, null, "x", "x",
						"x", "ip:port");
				ActiveAssistanceModule module2 = new ActiveAssistanceModule(
						"name2", "id2", "x", "x", "x", null, null, "x",
						"x", "x", "ip2:port");
				ActiveAssistanceModule module3 = new ActiveAssistanceModule(
						"name3", "id3", "x", "x", "x", null, null, "x",
						"x", "x", "ip3:port");

				ActiveAssistanceModulePersistency.create(module);
				ActiveAssistanceModulePersistency.create(module2);
				ActiveAssistanceModulePersistency.create(module3);

				UserModuleActivation activation = new UserModuleActivation(1,
						"id");
				UserModuleActivation activation2 = new UserModuleActivation(1,
						"id3");
				UserModuleActivation activation3 = new UserModuleActivation(2,
						"id2");

				UserModuleActivationPersistency.create(activation);
				UserModuleActivationPersistency.create(activation2);
				UserModuleActivationPersistency.create(activation3);

				ActiveAssistanceModule[] activationsForUser1 = UserModuleActivationPersistency
						.activatedModuleEndpointsForUser(1);
				ActiveAssistanceModule[] activationsForUser2 = UserModuleActivationPersistency
						.activatedModuleEndpointsForUser(2);

				boolean user1registeredForModule1 = Arrays
						.asList(activationsForUser1)
						.stream()
						.filter((a) -> (a.id.equals("id") && a.restContactAddress
								.equals("ip:port"))).count() == 1;
				boolean user1registeredForModule2 = Arrays
						.asList(activationsForUser1)
						.stream()
						.filter((a) -> (a.id.equals("id2") && a.restContactAddress
								.equals("ip2:port"))).count() == 1;

				boolean user1registeredForModule3 = Arrays
						.asList(activationsForUser1)
						.stream()
						.filter((a) -> (a.id.equals("id3") && a.restContactAddress
								.equals("ip3:port"))).count() == 1;
				boolean user2registeredForModule2 = Arrays
						.asList(activationsForUser2)
						.stream()
						.filter((a) -> (a.id.equals("id2") && a.restContactAddress
								.equals("ip2:port"))).count() == 1;
				boolean user2registeredForModule1 = Arrays
						.asList(activationsForUser2)
						.stream()
						.filter((a) -> (a.id.equals("id") && a.restContactAddress
								.equals("ip:port"))).count() == 1;

				assertTrue(user1registeredForModule1);
				assertFalse(user1registeredForModule2);
				assertTrue(user1registeredForModule3);
				assertTrue(user2registeredForModule2);
				assertFalse(user2registeredForModule1);
			}
		});
	}
	
	@Test
	public void getModuleEndpointByIdTest() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				ActiveAssistanceModule module = new ActiveAssistanceModule(
						"name", "id", "x", "x", "x", null, null, "x", "x",
						"x", "ip:port");
				ActiveAssistanceModule module2 = new ActiveAssistanceModule(
						"name2", "id2", "x", "x", "x", null, null, "x",
						"x", "x", "ip2:port");
				ActiveAssistanceModule module3 = new ActiveAssistanceModule(
						"name3", "id3", "x", "x", "x", null, null, "x",
						"x", "x", "ip3:port");
				
				ActiveAssistanceModulePersistency.create(module);
				ActiveAssistanceModulePersistency.create(module2);
				ActiveAssistanceModulePersistency.create(module3);

				ActiveAssistanceModule[] justModule1 = UserModuleActivationPersistency
						.activatedModuleEndpointsForUser(new String[] { "id" });
				
				assertTrue(justModule1.length == 1);
				
				assertTrue(justModule1[0].id.equals("id"));
				assertTrue(justModule1[0].restContactAddress.equals("ip:port"));
				
				ActiveAssistanceModule[] justModule12 = UserModuleActivationPersistency
						.activatedModuleEndpointsForUser(new String[] {  "id", "id2" });
				
				
				assertTrue(justModule12.length == 2);
				
				ActiveAssistanceModule[] justModule123 = UserModuleActivationPersistency
						.activatedModuleEndpointsForUser(new String[] { "id", "id2", "id3" });
				
				assertTrue(justModule123.length == 3);
				
				ActiveAssistanceModule[] noModules = UserModuleActivationPersistency
						.activatedModuleEndpointsForUser(new String[] { "unknown" });

				assertTrue(noModules.length == 0);
			}
		});
	}
}
