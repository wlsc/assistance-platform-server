package de.tudarmstadt.informatik.tk.assistanceplatform.modules.exceptions;

/**
 * This exception is thrown when bundle informations are not provided / implemented.
 */
public class ModuleBundleInformationMissingException extends Exception {
	public ModuleBundleInformationMissingException(String s) {
		super(s);
	}
}