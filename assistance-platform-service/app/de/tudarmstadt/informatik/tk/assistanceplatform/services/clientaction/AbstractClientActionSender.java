package de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest.VisibleNotification;
import play.libs.F.Promise;

public abstract class AbstractClientActionSender {
    /**
     * Implement this to do the actual sending which is specific to the used
     * platform.
     *
     * @param receiverIds The receiverIDs are the platform specific receiver tokens.
     * @param data        The data to be sent
     * @return
     */
    public abstract Promise<Boolean> platformSpecificSend(String[] receiverIds,
                                                          VisibleNotification notification, String data);
}
