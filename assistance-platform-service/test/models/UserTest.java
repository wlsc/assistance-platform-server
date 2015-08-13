package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.time.LocalDateTime;

import org.junit.Test;

import persistency.UserPersistency;

public class UserTest {    
    @Test
    public void userRegistrationTest() {
    	running(fakeApplication(inMemoryDatabase()), new Runnable() {
    		public void run() {
    			User u = new User("test@test.de"); 
    			UserPersistency.createAndUpdateIdOnSuccess(u, "12345678");
    			
    			assertTrue(u.id != 0);
    			
    			User u2 = new User("test@test.de"); 
    			UserPersistency.createAndUpdateIdOnSuccess(u, "12345678");
    			
    			assertTrue(u2.id == 0);
    		}
    	});
    }
    
    @Test
    public void userRegistrationJoinedSinceTest() {
    	running(fakeApplication(inMemoryDatabase()), new Runnable() {
    		public void run() {
    			LocalDateTime beforeCreation = LocalDateTime.now();
    			
    			User u = new User("test@test.de"); 
    			UserPersistency.createAndUpdateIdOnSuccess(u, "12345678");
    			u = UserPersistency.findUserById(u.id, true);
    			
    			LocalDateTime afterCreation = LocalDateTime.now();
    			
    			assertTrue(beforeCreation.isBefore(u.joinedSince) && afterCreation.isAfter(u.joinedSince));
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
    			UserPersistency.createAndUpdateIdOnSuccess(u, password);    			
    			assertTrue(User.authenticate(email, password));
    			assertFalse(User.authenticate(email, ""));
    			assertFalse(User.authenticate(email, password + "13"));
    			assertFalse(User.authenticate("test@test.com", password));
    		}
    	});
    }
    
    @Test
    public void userLoginLastLoginTest() {
    	running(fakeApplication(inMemoryDatabase()), new Runnable() {
    		public void run() {
    			String email = "test@test.de";
    			String password = "12345678";
    			User u = new User(email); 
    			UserPersistency.createAndUpdateIdOnSuccess(u, password);    			
    			assertTrue(User.authenticate(email, password));
    			UserPersistency.updateLastLogin(u.id);
    			
    			User fullU = UserPersistency.findUserById(u.id, true);
    			LocalDateTime firstLogin = fullU.lastLogin;
    			
    			try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			User.authenticate(email, password);
    			UserPersistency.updateLastLogin(u.id);
    			
    			fullU = UserPersistency.findUserById(u.id, true);
    			LocalDateTime lastLogin = fullU.lastLogin;
    			
    			assertTrue(lastLogin.isAfter(firstLogin) );
    		}
    	});
    }
    
    
    @Test
    public void userUpdateProfile() {
    	running(fakeApplication(inMemoryDatabase()), new Runnable() {
    		public void run() {
    			String email = "test@test.de";
    			String password = "12345678";
    			User u = new User(email); 
    			UserPersistency.createAndUpdateIdOnSuccess(u, password);   
    			
    			User fullU = UserPersistency.findUserById(u.id, true);
    			assertEquals(null, fullU.firstName);
    			assertEquals(null, fullU.lastName);
    			
    			u.firstName = "Test";
    			u.lastName = "Test2";
    			
    			UserPersistency.updateProfile(u);
    			
    			fullU = UserPersistency.findUserById(u.id, true);
    			
    			assertEquals(u.firstName, fullU.firstName);
    			assertEquals(u.lastName, fullU.lastName);
    		}
    	});
    }
}
