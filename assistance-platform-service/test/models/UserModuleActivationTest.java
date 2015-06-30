package models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import org.junit.Test;

import persistency.UserModuleActivationPersistency;

public class UserModuleActivationTest {
    @Test
    public void activationCreationTest() {
    	running(fakeApplication(inMemoryDatabase()), new Runnable() {
    		public void run() {
    			assertTrue(UserModuleActivationPersistency.create(new UserModuleActivation(10L, "xyz")));
    			assertFalse(UserModuleActivationPersistency.create(new UserModuleActivation(10L, "xyz")));
    		}
    	});
    }
    
    @Test
    public void removeTest() {
    	running(fakeApplication(inMemoryDatabase()), new Runnable() {
    		public void run() {
    			UserModuleActivation activation = new UserModuleActivation(10L, "xyz");
    			assertTrue(UserModuleActivationPersistency.create(activation));
    			assertTrue(UserModuleActivationPersistency.remove(activation));
    			assertFalse(UserModuleActivationPersistency.remove(activation));
    			assertTrue(UserModuleActivationPersistency.create(activation));
    		}
    	});
    }
}
