package models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import org.junit.Test;

public class UserTest {    
    @Test
    public void userRegistrationTest() {
    	running(fakeApplication(inMemoryDatabase()), new Runnable() {
    		public void run() {
    			User u = new User("test@test.de"); 
    			User.createAndUpdateIdOnSuccess(u, "12345678");
    			
    			assertTrue(u.id != 0);
    			
    			User u2 = new User("test@test.de"); 
    			User.createAndUpdateIdOnSuccess(u, "12345678");
    			
    			assertTrue(u2.id == 0);
    		}
    	});
    }
    
    @Test
    public void userLoginTest() {
    	running(fakeApplication(inMemoryDatabase()), new Runnable() {
    		public void run() {
    			String email = "test@test.de";
    			String password = "12345678";
    			User u = new User(email); 
    			User.createAndUpdateIdOnSuccess(u, password);    			
    			assertTrue(User.authenticateUser(email, password));
    			assertFalse(User.authenticateUser(email, ""));
    			assertFalse(User.authenticateUser(email, password + "13"));
    			assertFalse(User.authenticateUser("test@test.com", password));
    		}
    	});
    }
}
