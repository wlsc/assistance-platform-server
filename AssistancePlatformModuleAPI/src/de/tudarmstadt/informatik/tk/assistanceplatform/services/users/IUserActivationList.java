package de.tudarmstadt.informatik.tk.assistanceplatform.services.users;

public interface IUserActivationList {
  void addActivationByUser(long userId);

  void removeActivationFromUser(long userId);
}
