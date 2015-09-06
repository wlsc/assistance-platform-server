package de.tudarmstadt.informatik.tk.assistanceplatform.services.action;

import java.util.Arrays;

import org.apache.log4j.Logger;

public class DummyClientActionRunner implements IClientActionRunner {

	@Override
	public void displayInformation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMail() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void showMessage(long userId, long[] deviceIds, String title,
			String message) {
		Logger.getLogger(this.getClass()).info( String.format("CLIENT ACTION: Send message to devices %s of user %d", Arrays.toString(deviceIds), userId) );
	}

	@Override
	public void sendTestData(long userId, long[] deviceIds, String data) {
		Logger.getLogger(this.getClass()).info( String.format("CLIENT ACTION: Send data to devices %s of user %d", Arrays.toString(deviceIds), userId) );
	}
}