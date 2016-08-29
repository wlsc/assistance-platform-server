package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.assistanceplatformservice.requests;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest.VisibleNotification;


/**
 * Simple POJO for a request to the platform which contains information about receiver and data etc.
 */
public class SendMessageRequest {
  public long userId;
  public long[] deviceIds;
  public VisibleNotification visibleNotification;
  public String data;

  public SendMessageRequest() {}

  public SendMessageRequest(long userId, long[] deviceIds, VisibleNotification visibleNotification,
      String data) {
    super();
    this.userId = userId;
    this.deviceIds = deviceIds;
    this.visibleNotification = visibleNotification;
    this.data = data;
  }
}
