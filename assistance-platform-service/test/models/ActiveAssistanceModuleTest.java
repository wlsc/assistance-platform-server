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
    			
    			ActiveAssistanceModule module = new ActiveAssistanceModule(name, id, testUrl, testDescrShort, testDescrLong, requiredCaps, optCaps, copyright);

    			assertTrue(ActiveAssistanceModulePersistency.create(module));
    			assertFalse(ActiveAssistanceModulePersistency.create(module));
    			
    			ActiveAssistanceModule[] modules = ActiveAssistanceModulePersistency.list();
    			ActiveAssistanceModule firstModule = modules[0];
    			
    			compareModule(module, firstModule);
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
	    			
	    			ActiveAssistanceModule module = new ActiveAssistanceModule(name, id, testUrl, testDescrShort, testDescrLong, requiredCaps, optCaps, copyright);
	
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
}
