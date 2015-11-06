package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

public class Capability {
	/**
	 * The type of this capability (e.g. position)
	 */
	public String type;
	
	/**
	 * The frequency of needed event readings of this type (per second). Readings can be cached on the client.
	 */
	public double collection_frequency;
	
	/**
	 * The frequency in which at least {@link #min_required_readings_on_update} have to be sent to the platform.
	 */
	public double required_update_frequency;
	
	/**
	 * The minimum number that has to be sent in {@link #required_update_frequency} so the module can keep up with processing.
	 */
	public int min_required_readings_on_update;

	public Capability() {
	}

	public Capability(String type, double collection_frequency,
			double required_update_frequency,
			int min_required_readings_on_update) {
		super();
		this.type = type;
		this.collection_frequency = collection_frequency;
		this.required_update_frequency = required_update_frequency;
		this.min_required_readings_on_update = min_required_readings_on_update;
	}
}