package de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.Device;
import persistency.DevicePersistency;
import play.Logger;

public class ClientActionSenderDistributor {

	public boolean sendDataToUserDevices(long userId, long[] deviceIds,
			VisibleNotification notification, String data) {
		// Find the devices
		Device[] devices = DevicePersistency.findDevicesById(deviceIds);
		
		// Divide / group the devices to the platforms
		Map<String, List<Device>> devicesToPlatforms = groupDevicesPerPlatform(devices);
		
		//// Use action sender factory to distirbute to the platforms
		boolean cummulatedResult = true;
		ClientActionSenderFactory factory = new ClientActionSenderFactory();
		
		for(Entry<String, List<Device>> e : devicesToPlatforms.entrySet()) {
			String[] receiverIds = e.getValue().parallelStream().map((d) -> {
				return d.messagingRegistrationId;
			}).toArray(String[]::new);
			
			try {
				cummulatedResult &= factory.getClientSender(e.getKey()).platformSpecificSend(receiverIds, notification, data);
			} catch (PlatformNotSupportedException e1) {
				Logger.warn("Platform not supported!", e1);
			}
		}
		
		// TODO: Falls erforderlich, könnte man noch einen "Report" erstellen, bie welchen Geräten es nicht funktioniert hat und es zurückgeben
		return cummulatedResult;
	}

	private Map<String, List<Device>> groupDevicesPerPlatform(Device[] devices) {
		Map<String, List<Device>> devicesToPlatforms = new HashMap<>();
		
		for(Device d : devices) {
			if(!devicesToPlatforms.containsKey(d.operatingSystem)) {
				devicesToPlatforms.put(d.operatingSystem, new LinkedList<>());
			}
			
			devicesToPlatforms.get(d.operatingSystem).add(d);
		}
		
		return devicesToPlatforms;
	}

	public boolean sendDataToAllUserDevices(long userId,
			VisibleNotification notification, String data) {
		long[] deviceIds = new long[] {};

		return this
				.sendDataToUserDevices(userId, deviceIds, notification, data);
	}

	public boolean sendDataToUserDevices(long userId, long[] deviceIds,
			String data) {
		return this.sendDataToUserDevices(userId, deviceIds, null, data);
	}

	public boolean sendDataToAllUserDevices(long userId, String data) {
		return this.sendDataToUserDevices(userId, null, data);
	}

}
