package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This POJO represents a user device.
 * @author bjeutter
 *
 */
public class Device {
	public Long id = 0L;
	
	public Long userId = 0L;

	@JsonProperty(value = "os")
	public String operatingSystem;
	
	@JsonProperty(value = "os_version")
	public String osVersion;
	
	@JsonProperty(value = "device_identifier")
	public String deviceIdentifier;
	
	@JsonProperty(value = "messaging_registration_id")
	public String messagingRegistrationId;
	
	public String brand;
	
	public String model;
	
	public Device() {
	}
	
	public Device(Long userId, String operatingSystem,
			String osVersion, String device_identifier, String brand,
			String model) {
		this.userId = userId;
		this.operatingSystem = operatingSystem;
		this.osVersion = osVersion;
		this.deviceIdentifier = device_identifier;
		this.brand = brand;
		this.model = model;
	}
	
	public Device(Long id, Long userId, String operatingSystem,
			String osVersion, String device_identifier, String brand,
			String model) {
		this(userId, operatingSystem, osVersion, device_identifier, brand, model);
		this.id = id;
	}
}