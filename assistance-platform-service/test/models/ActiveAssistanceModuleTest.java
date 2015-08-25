package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Capability;
import persistency.ActiveAssistanceModulePersistency;

public class ActiveAssistanceModuleTest {
    @Test
    public void moduleCreationTest() {
    	running(fakeApplication(inMemoryDatabase() ), new Runnable() {
    		public void run() {
    			String name = "Test";
    			String id = "TestID";
    			String testUrl = "TestURL";
    			String testDescrShort = "Test descr short";
    			String testDescrLong = "Test descr long";
    			Capability[] requiredCaps = new Capability[] { 
    					new Capability("cap_1", 1),
    					new Capability("cap_2", 0.1)
    			};
    			Capability[] optCaps = new Capability[] {
    					new Capability("cap_3", 0.01)
    			};
    			String copyright = "Test Copyright";
    			String administratorEmail = "bennet@test.de";
    			String supportEmail = "support@test.de";
    			
    			ActiveAssistanceModule module = new ActiveAssistanceModule(name, id, testUrl, testDescrShort, testDescrLong, requiredCaps, optCaps, copyright, administratorEmail, supportEmail);

    			assertTrue(ActiveAssistanceModulePersistency.create(module));
    			assertFalse(ActiveAssistanceModulePersistency.create(module));
    			
    			ActiveAssistanceModule[] modules = ActiveAssistanceModulePersistency.list();
    			ActiveAssistanceModule firstModule = modules[0];
    			
    			compareModule(module, firstModule);
    		}
    	});
    }
    
    @Test
    public void moduleUpdateTest() {
    	running(fakeApplication(inMemoryDatabase() ), new Runnable() {
    		public void run() {
    			String name = "Test";
    			String id = "TestID";
    			String testUrl = "TestURL";
    			String testDescrShort = "Test descr short";
    			String testDescrLong = "Test descr long";
    			Capability[] requiredCaps = new Capability[] { 
    					new Capability("cap_1", 1),
    					new Capability("cap_2", 0.1)
    			};
    			Capability[] optCaps = new Capability[] {
    					new Capability("cap_3", 0.01)
    			};
    			String copyright = "Test Copyright";
    			String administratorEmail = "bennet@test.de";
    			String supportEmail = "support@test.de";
    			
    			ActiveAssistanceModule module = new ActiveAssistanceModule(name, id, testUrl, testDescrShort, testDescrLong, requiredCaps, optCaps, copyright, administratorEmail, supportEmail);

    			assertFalse(ActiveAssistanceModulePersistency.update(module));
    			
    			ActiveAssistanceModulePersistency.create(module);
    			
    			module.logoUrl = "testtest";
    			
    			assertTrue(ActiveAssistanceModulePersistency.update(module));
    			
    			ActiveAssistanceModule[] modules = ActiveAssistanceModulePersistency.list();
    			ActiveAssistanceModule firstModule = modules[0];
    			
    			compareModule(module, firstModule);
    		}
    	});
    }
    
    @Test
    public void multiCreationTest() {
    	running(fakeApplication(inMemoryDatabase() ), new Runnable() {
    		public void run() {
    			int numOfCreations = 100;
    			ActiveAssistanceModule[] desiredData = new ActiveAssistanceModule[numOfCreations];
    			
    			for(int i = 0; i < numOfCreations; i++) {
	    			String name = "Test"+i;
	    			String id = "TestID"+i;
	    			String testUrl = "TestURL"+i;
	    			String testDescrShort = "Test descr short"+i;
	    			String testDescrLong = "Test descr long"+i;
	    			Capability[] requiredCaps = new Capability[] { 
	    					new Capability("cap_1" +i , 1),
	    					new Capability("cap_2" +i, 0.1)
	    			};
	    			Capability[] optCaps = new Capability[] {
	    					new Capability("cap_3" +i, 0.01)
	    			};
	    			String copyright = "Test Copyright"+i;
	    			String administratorEmail = "bennet"+i+"@test.de";
	    			String supportEmail = "support@test.de";
	    			
	    			ActiveAssistanceModule module = new ActiveAssistanceModule(name, id, testUrl, testDescrShort, testDescrLong, requiredCaps, optCaps, copyright, administratorEmail, supportEmail);
	
	    			assertTrue(ActiveAssistanceModulePersistency.create(module));
	    			assertFalse(ActiveAssistanceModulePersistency.create(module));
	    			
	    			desiredData[i] = module;
    			}
    			
    			ActiveAssistanceModule[] modules = ActiveAssistanceModulePersistency.list();
    			
    			for(int i = 0; i < numOfCreations; i++) {
    				compareModule(desiredData[i], modules[i]);
    			}
    		}
    	});
    }
    
    
    @Test
    public void localizationTest() {
    	running(fakeApplication(inMemoryDatabase() ), new Runnable() {
    		public void run() {
    			
    			// Create an unlocalized Module
    			ActiveAssistanceModule unlocalized = new ActiveAssistanceModule("English", "id", "lgoo", "descr", "descr long", new Capability[] {}, new Capability[] {}, "xyz", "bla@bla.de", "support@sup.de");
    		
    			ActiveAssistanceModulePersistency.create(unlocalized);
    			
    			ActiveAssistanceModule[] modules = ActiveAssistanceModulePersistency.list("de"); // Query for german localization (which should not exist) -> Fallback to english (unlocalized)
    			
    			compareModule(unlocalized, modules[0]);
    			
    			// Now create an localization of the previous module
    			
    			ActiveAssistanceModule localizedDe = new ActiveAssistanceModule("Deutsch", unlocalized.id, "lgoode", "descrde", "descr long de", unlocalized.optionalCapabilites, unlocalized.requiredCapabilities, "xyz", "bla@bla.de", "support@sup.de");
    			
    			ActiveAssistanceModulePersistency.localize("de", localizedDe);
    			
    			modules = ActiveAssistanceModulePersistency.list("de"); // Query for german localization (which should exist now)
    			
    			compareModule(localizedDe, modules[0]);
    			
    			// Check if the english version still can be retreived
    			modules = ActiveAssistanceModulePersistency.list("en"); // Query for german localization (which should exist now)
    			
    			compareModule(unlocalized, modules[0]);
    		}
    	});
    }
    
    private void compareModule(ActiveAssistanceModule expected, ActiveAssistanceModule actualModul) {
		assertEquals(expected.name, actualModul.name);
		assertEquals(expected.id, actualModul.id);
		assertEquals(expected.logoUrl, actualModul.logoUrl);
		assertEquals(expected.descriptionShort, actualModul.descriptionShort);
		assertEquals(expected.descriptionLong, actualModul.descriptionLong);
		
		if(expected.requiredCapabilities != null) {
			int i = 0;
			for(Capability c : expected.requiredCapabilities) {
				c.equals(actualModul.requiredCapabilities[i]);
				i++;
			}
		} else {
			assertTrue(actualModul.requiredCapabilities == null);
		}
		
		if(expected.optionalCapabilites != null) {
			int i = 0;
			for(Capability c : expected.optionalCapabilites) {
				c.equals(actualModul.optionalCapabilites[i]);
				i++;
			}
		} else {
			assertTrue(actualModul.optionalCapabilites == null);
		}
		
		assertEquals(expected.copyright, actualModul.copyright);
		assertEquals(expected.administratorEmail, actualModul.administratorEmail);
    }
}
