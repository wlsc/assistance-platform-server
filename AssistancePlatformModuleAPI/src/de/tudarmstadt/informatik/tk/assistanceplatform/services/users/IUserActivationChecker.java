package de.tudarmstadt.informatik.tk.assistanceplatform.services.users;

/**
 * Simple interface to tell if a user with a given ID is activated (for a module) or not.
 */
public interface IUserActivationChecker {
  boolean isActivatedForUser(long userId);
}
