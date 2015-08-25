package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

public class Capability {
	/**
	 * The type of this capability (e.g. position)
	 */
	public String type;
	
	/**
	 * The frequency of needed event readings of this type (per second)
	 */
	public double frequency;

	public Capability() {
	}
	
	public Capability(String type, double frequency) {
		super();
		this.type = type;
		this.frequency = frequency;
	}
}