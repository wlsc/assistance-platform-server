package de.tudarmstadt.informatik.tk.assistanceplatform.services.action;

import java.util.function.Consumer;

/**
 * Describes the functions that can be run against clients, like sending messages etc.
 * 
 * @author bjeutter
 *
 */
public interface IClientActionRunner {
  void showMessage(long userId, long deviceIds[], String title, String message);

  void sendDataAndMessage(long userId, long[] deviceIds, String title, String message, String data,
      Consumer<Void> onSuccess, Consumer<Void> onError);

  /**
   * FOR TESTING ONLY!!! NOT FOR PRODUCTION MODE!
   * 
   * @param userId
   * @param deviceId
   * @param title
   * @param message
   */
  void sendTestData(long userId, long deviceIds[], String data);

  void displayInformation();

  void sendMail();
}
