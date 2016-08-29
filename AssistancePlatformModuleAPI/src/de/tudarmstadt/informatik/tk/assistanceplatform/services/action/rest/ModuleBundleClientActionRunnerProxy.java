package de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest;

import java.util.function.Consumer;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.ModuleBundle;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.IClientActionRunner;

public class ModuleBundleClientActionRunnerProxy implements IClientActionRunner {
  private IClientActionRunner actionRunner;
  private ModuleBundle bundle;

  public ModuleBundleClientActionRunnerProxy(ModuleBundle bundle,
      IClientActionRunner actionRunner) {
    this.actionRunner = actionRunner;
    this.bundle = bundle;
  }

  @Override
  public void showMessage(long userId, long[] deviceIds, String title, String message) {
    actionRunner.showMessage(userId, deviceIds, proxiedNotificationTitle(title), message);
  }

  @Override
  public void sendDataAndMessage(long userId, long[] deviceIds, String title, String message,
      String data, Consumer<Void> onSuccess, Consumer<Void> onError) {
    actionRunner.sendDataAndMessage(userId, deviceIds, proxiedNotificationTitle(title), message,
        data, onSuccess, onError);
  }

  private String proxiedNotificationTitle(String title) {
    if (title == null) {
      return null;
    }

    boolean hasTitle = title.length() > 0;

    // Prepend module name
    String result = bundle.getBundleInformation().englishModuleBundleInformation.name;

    if (hasTitle) {
      result += ": " + title;
    }

    return result;
  }

  @Override
  public void sendTestData(long userId, long[] deviceIds, String data) {
    actionRunner.sendTestData(userId, deviceIds, data);

  }

  @Override
  public void displayInformation() {
    actionRunner.displayInformation();
  }

  @Override
  public void sendMail() {
    actionRunner.sendMail();
  }
}
