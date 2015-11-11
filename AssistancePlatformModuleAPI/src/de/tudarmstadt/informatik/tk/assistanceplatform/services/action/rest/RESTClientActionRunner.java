package de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest;

import java.util.function.Consumer;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.IClientActionRunner;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.PlatformClient;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests.SendMessageRequest;

/**
 * Action runner to run actions over the platform via REST Client.
 * @author bjeutter
 *
 */
public class RESTClientActionRunner implements IClientActionRunner {
	private PlatformClient platformClient;
	
	public RESTClientActionRunner(PlatformClient platformClient) {
		this.platformClient = platformClient;
	}
	
	@Override
	public void showMessage(long userId, long[] deviceIds, String title,
			String message) {
		this.sendDataAndMessage(userId, deviceIds, title, message, null, null, null);
		
	}

	@Override
	public void sendTestData(long userId, long[] deviceIds, String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayInformation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendDataAndMessage(long userId, long[] deviceIds, String title,
			String message, String data, Consumer<Void> onSuccess, Consumer<Void> onError) {
		SendMessageRequest request = new SendMessageRequest(userId, deviceIds, new VisibleNotification(title, message), data);
		
		this.platformClient.sendMessage(request, (v) -> {
			if(onSuccess != null) {
				onSuccess.accept(null);
			}
		}, (v) -> {
			if(onError != null) {
				onError.accept(null);
			}
		});
	}
}