package requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple POJO for a device registration (for messaging service) request
 */
public class RegisterDeviceForMessagingRequest {
    @JsonProperty(value = "device_id")
    public long deviceId;

    @JsonProperty(value = "messaging_registration_id")
    public String messagingRegistrationId;
}
