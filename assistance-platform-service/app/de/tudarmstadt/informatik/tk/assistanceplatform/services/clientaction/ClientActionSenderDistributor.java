package de.tudarmstadt.informatik.tk.assistanceplatform.services.clientaction;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.action.rest.VisibleNotification;
import models.Device;
import persistency.DevicePersistency;
import play.Logger;
import play.libs.F.Promise;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ClientActionSenderDistributor {

    public Promise<Boolean> sendDataToUserDevices(long userId,
                                                  long[] deviceIds, VisibleNotification notification, String data) {
        // Find the devices
        Device[] devices = DevicePersistency.findDevicesById(deviceIds);

        // Divide / group the devices to the platforms
        Map<String, List<Device>> devicesToPlatforms = groupDevicesPerPlatform(devices);

        // // Use action sender factory to distirbute to the platform
        ClientActionSenderFactory factory = new ClientActionSenderFactory();
        List<Promise<Boolean>> sendPromises = new LinkedList<>();

        for (Entry<String, List<Device>> e : devicesToPlatforms.entrySet()) {
            String[] receiverIds = e.getValue().parallelStream().map((d) -> d.messagingRegistrationId).toArray(String[]::new);

            try {
                sendPromises.add(factory.getClientSender(e.getKey())
                        .platformSpecificSend(receiverIds, notification, data));
            } catch (PlatformNotSupportedException e1) {
                Logger.warn("Platform not supported!", e1);
            }
        }

        // TODO: Falls erforderlich, könnte man noch einen "Report" erstellen,
        // bie welchen Geräten es nicht funktioniert hat und es zurückgeben

        return Promise.sequence(sendPromises).flatMap(
                (resultList) -> {
                    Boolean reducedResult = resultList.stream()
                            .reduce((a, b) -> a & b).orElse(true);

                    return Promise.pure(reducedResult);
                });
    }

    private Map<String, List<Device>> groupDevicesPerPlatform(Device[] devices) {
        Map<String, List<Device>> devicesToPlatforms = new HashMap<>();

        for (Device d : devices) {
            if (!devicesToPlatforms.containsKey(d.operatingSystem)) {
                devicesToPlatforms.put(d.operatingSystem, new LinkedList<>());
            }

            devicesToPlatforms.get(d.operatingSystem).add(d);
        }

        return devicesToPlatforms;
    }

    public Promise<Boolean> sendDataToAllUserDevices(long userId,
                                                     VisibleNotification notification, String data) {
        long[] deviceIds = {};

        return this
                .sendDataToUserDevices(userId, deviceIds, notification, data);
    }

    public Promise<Boolean> sendDataToUserDevices(long userId, long[] deviceIds,
                                                  String data) {
        return this.sendDataToUserDevices(userId, deviceIds, null, data);
    }

    public Promise<Boolean> sendDataToAllUserDevices(long userId, String data) {
        return this.sendDataToUserDevices(userId, null, data);
    }

}
