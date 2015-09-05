package controllers;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.ClientActionSenderFactory;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.PlatformNotSupportedException;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction.VisibleNotification;
import play.mvc.Result;

/**
 * This controller is responsible for handling requests by module to provide the way back to the user / client.
 * @author bjeutter
 */
public class ClientActionController extends RestController {
	public Result sendMessageToDevices() {
		// TODO: User + Device + Message auslesen
		long userId = -1;
		long[] deviceIds = new long[] { };
		
		// TODO: Prüfen ob User + Device existent
		
		VisibleNotification visibleNotification = null; // TODO: Daten / Nachricht auslesen
		String data = null;
		
		String platformOfDevice = null; // TODO: Platform des Devices auslesen
		
		// TODO: In Zukunft ggf. empfangen, von welchem Modul diese Anfrage kommt
		
		ClientActionSenderFactory actionSenderFactory = new ClientActionSenderFactory();
		try {
			// TODO: Ggf. Akka Actor verwenden?
			boolean sendResult = actionSenderFactory.getClientSender(platformOfDevice).sendDataToUserDevices(userId, deviceIds, visibleNotification, data);
			
			if(sendResult) {
				return ok();
			} else {
				// TODO: erorr zurückgegeben
			}
		} catch (PlatformNotSupportedException e) {
			// TODO: error zurückgeben
		}
		
		return TODO;
	}
}
