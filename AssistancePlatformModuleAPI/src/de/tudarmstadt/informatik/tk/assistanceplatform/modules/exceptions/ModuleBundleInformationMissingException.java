package de.tudarmstadt.informatik.tk.assistanceplatform.modules.exceptions;

/**
 * This exception is thrown when bundle informations are not provided / implemented.
 */
public class ModuleBundleInformationMissingException extends Exception {
  private static final long serialVersionUID = -5287998614962883514L;

  public ModuleBundleInformationMissingException(String s) {
    super(s);
  }
}
