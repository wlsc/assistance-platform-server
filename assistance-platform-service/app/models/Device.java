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
	
	public String brand;
	
	public String model;
	
	public String messagingRegistrationId;
	
	public String userDefinedName;
	
	public long lastUsage;
	
	public Device() {
	}
	
	public Device(Long id, Long userId, String operatingSystem,
			String osVersion, String device_identifier, String brand,
			String model, String messagingRegistrationId, String userDefinedName, long lastUsage) {
		this.id = id;
		this.userId = userId;
		this.operatingSystem = operatingSystem;
		this.osVersion = osVersion;
		this.deviceIdentifier = device_identifier;
		this.brand = brand;
		this.model = model;
		this.messagingRegistrationId = messagingRegistrationId;
		this.userDefinedName = userDefinedName;
		this.lastUsage = lastUsage;
	}
	
	public Device(Long userId, String operatingSystem,
			String osVersion, String device_identifier, String brand,
			String model) {
		this(0L, userId, operatingSystem, osVersion, device_identifier, brand, model, null, null, 0);
	}
	
	public Device(Long id, Long userId, String operatingSystem,
			String osVersion, String device_identifier, String brand,
			String model) {
		this(userId, operatingSystem, osVersion, device_identifier, brand, model);
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime
				* result
				+ ((deviceIdentifier == null) ? 0 : deviceIdentifier.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (int) (lastUsage ^ (lastUsage >>> 32));
		result = prime
				* result
				+ ((messagingRegistrationId == null) ? 0
						: messagingRegistrationId.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result
				+ ((operatingSystem == null) ? 0 : operatingSystem.hashCode());
		result = prime * result
				+ ((osVersion == null) ? 0 : osVersion.hashCode());
		result = prime * result
				+ ((userDefinedName == null) ? 0 : userDefinedName.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (deviceIdentifier == null) {
			if (other.deviceIdentifier != null)
				return false;
		} else if (!deviceIdentifier.equals(other.deviceIdentifier))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastUsage != other.lastUsage)
			return false;
		if (messagingRegistrationId == null) {
			if (other.messagingRegistrationId != null)
				return false;
		} else if (!messagingRegistrationId
				.equals(other.messagingRegistrationId))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (operatingSystem == null) {
			if (other.operatingSystem != null)
				return false;
		} else if (!operatingSystem.equals(other.operatingSystem))
			return false;
		if (osVersion == null) {
			if (other.osVersion != null)
				return false;
		} else if (!osVersion.equals(other.osVersion))
			return false;
		if (userDefinedName == null) {
			if (other.userDefinedName != null)
				return false;
		} else if (!userDefinedName.equals(other.userDefinedName))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}