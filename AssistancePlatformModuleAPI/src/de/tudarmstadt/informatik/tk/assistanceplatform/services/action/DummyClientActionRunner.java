package de.tudarmstadt.informatik.tk.assistanceplatform.services.action;

import org.apache.log4j.Logger;

public class DummyClientActionRunner implements IClientActionRunner {

	@Override
	public void showMessage(long userId, long deviceId, String message) {
		Logger.getLogger(this.getClass()).info( String.format("CLIENT ACTION: Send message to device %d of user %d", deviceId, userId) );
		
	}

	@Override
	public void displayInformation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMail() {
		// TODO Auto-generated method stub
		
	}

}