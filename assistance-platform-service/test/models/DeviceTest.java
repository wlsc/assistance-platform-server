package models;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import org.junit.Test;

import persistency.DevicePersistency;

public class DeviceTest {
    @Test
    public void deviceCreationTest() {
    	running(fakeApplication(inMemoryDatabase()), new Runnable() {
    		public void run() {
    			Device d = new Device(123L, "test", "1.0", "xyz", "TEST", "Tester");
    			DevicePersistency.createIfNotExists(d);
    			
    			assertTrue(d.id != 0);
    			
    			Device[] devices = DevicePersistency.findDevicesById(new long[] { d.id });
    			
    			assertTrue(devices[0].equals(d));
    			
    			DevicePersistency.createIfNotExists(d);
    			
    			assertTrue(devices[0].id.equals(d.id));
    		}
    	});
    }
    
    @Test
    public void multiDeviceCreationAndFetchTest() {
    	running(fakeApplication(inMemoryDatabase()), new Runnable() {
    		public void run() {
    			int numOfDevices = 10000;
    			
    			long[] deviceIds = new long[numOfDevices];
    			
    			for(int i = 0; i < numOfDevices; i++) {
    				Device d = new Device((long)123 + i, "test" + i, "1.0" + i, "xyz" + i, "TEST" + i, "Tester" + i);
    				DevicePersistency.createIfNotExists(d);
    				assertTrue(d.id != 0);
    				assertTrue(DevicePersistency.doesExist(d.id));
    				assertFalse(DevicePersistency.doesExist(d.id + numOfDevices * 2));
    				
    				assertTrue(DevicePersistency.ownedByUser(d.id, d.userId));
    				assertFalse(DevicePersistency.ownedByUser(d.id, d.userId - 10));
    				
    				deviceIds[i] = d.id;
    			}
 
    			Device[] devices = DevicePersistency.findDevicesById(deviceIds);
    			
    			assertTrue(devices.length == numOfDevices);
    		}
    	});
    }
    
    @Test
    public void deviceUpdateTest() {
    	running(fakeApplication(inMemoryDatabase()), new Runnable() {
    		public void run() {
    			Device d = new Device(123L, "test", "1.0", "xyz", "TEST", "Tester");
    			DevicePersistency.createIfNotExists(d);
    			
    			d.model = "abc";
    			DevicePersistency.update(d);
    			
    			Device[] devices = DevicePersistency.findDevicesById(new long[] { d.id });
    			
    			assertTrue(devices[0].equals(d));
    		}
    	});
    }
    
    @Test
    public void linkDeviceToMessagingIdTest() {
    	running(fakeApplication(inMemoryDatabase()), new Runnable() {
    		public void run() {
    			Device d = new Device(123L, "test", "1.0", "xyz", "TEST", "Tester");
    			DevicePersistency.createIfNotExists(d);
    			Device[] devices = DevicePersistency.findDevicesById(new long[] { d.id });
    			assertTrue(devices[0].messagingRegistrationId == null);
    			
    			String regId = "regid12345";
    			DevicePersistency.linkDeviceToMessagingService(d.id, regId);
    			devices = DevicePersistency.findDevicesById(new long[] { d.id });
    			assertTrue(devices[0].messagingRegistrationId == regId);
    		}
    	});
    }
}
