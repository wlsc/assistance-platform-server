package sensorhandling;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.util.Arrays;
import java.util.Collections;

import models.ActiveAssistanceModule;

import org.junit.Test;

import persistency.ActiveAssistanceModulePersistency;
import sensorhandling.preprocessing.IEventPreprocessor;
import sensorhandling.preprocessing.SpecialEventPreprocessor;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.Position;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.social.tucan.TucanCredentials;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Capability;

public class EventPreprocessorTest {
    @Test
    public void test() {
    	IEventPreprocessor switchCredentialFieldPreprocessor = new IEventPreprocessor<TucanCredentials>() {

			@Override
			public Class<TucanCredentials> eventClassResponsibleFor() {
				return TucanCredentials.class;
			}

			@Override
			public TucanCredentials preprocessEvent(TucanCredentials event) {
				return new TucanCredentials(event.password, event.username);
			}
		};	
		
		SpecialEventPreprocessor preprocessorProxy = new SpecialEventPreprocessor(Arrays.asList(new IEventPreprocessor[] {
			switchCredentialFieldPreprocessor
		}));
		
		Position testPos = new Position();
		
		assertEquals(testPos, preprocessorProxy.preprocess(testPos));
		
		TucanCredentials testCred = new TucanCredentials("user", "password");
		TucanCredentials preprocessedCred = (TucanCredentials) preprocessorProxy.preprocess(testCred);
		
		
		assertNotEquals(testCred, preprocessedCred);
		
		assertEquals(testCred.getPassword(), preprocessedCred.getUsername());
		assertEquals(testCred.getUsername(), preprocessedCred.getPassword());
    }
}