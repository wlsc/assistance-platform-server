package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import org.junit.Test;

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
    			String[] requiredCaps = new String[] { "1", "2", "3" };
    			String[] optCaps = new String[] { "o1", "o2" };
    			String copyright = "Test Copyright";
    			String administratorEmail = "bennet@test.de";
    			
    			ActiveAssistanceModule module = new ActiveAssistanceModule(name, id, testUrl, testDescrShort, testDescrLong, requiredCaps, optCaps, copyright, administratorEmail);

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
    			String[] requiredCaps = new String[] { "1", "2", "3" };
    			String[] optCaps = new String[] { "o1", "o2" };
    			String copyright = "Test Copyright";
    			String administratorEmail = "bennet@test.de";
    			
    			ActiveAssistanceModule module = new ActiveAssistanceModule(name, id, testUrl, testDescrShort, testDescrLong, requiredCaps, optCaps, copyright, administratorEmail);

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
	    			String[] requiredCaps = new String[] { "1"+i, "2"+i, "3"+i };
	    			String[] optCaps = new String[] { "o1"+i, "o2"+i };
	    			String copyright = "Test Copyright"+i;
	    			String administratorEmail = "bennet"+i+"@test.de";
	    			
	    			ActiveAssistanceModule module = new ActiveAssistanceModule(name, id, testUrl, testDescrShort, testDescrLong, requiredCaps, optCaps, copyright, administratorEmail);
	
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
    			ActiveAssistanceModule unlocalized = new ActiveAssistanceModule("English", "id", "lgoo", "descr", "descr long", new String[] { }, new String[] { }, "xyz", "bla@bla.de");
    		
    			ActiveAssistanceModulePersistency.create(unlocalized);
    			
    			ActiveAssistanceModule[] modules = ActiveAssistanceModulePersistency.list("de"); // Query for german localization (which should not exist) -> Fallback to english (unlocalized)
    			
    			compareModule(unlocalized, modules[0]);
    			
    			// Now create an localization of the previous module
    			
    			ActiveAssistanceModule localizedDe = new ActiveAssistanceModule("Deutsch", unlocalized.id, "lgoode", "descrde", "descr long de", unlocalized.optionalCapabilites, unlocalized.requiredCapabilities, "xyz", "bla@bla.de");
    			
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
		assertEquals(expected.requiredCapabilities, actualModul.requiredCapabilities);
		assertEquals(expected.optionalCapabilites, actualModul.optionalCapabilites);
		assertEquals(expected.copyright, actualModul.copyright);
		assertEquals(expected.administratorEmail, actualModul.administratorEmail);
    }
}
