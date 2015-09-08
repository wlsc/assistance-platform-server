package persistency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Capability;
import models.ActiveAssistanceModule;
import models.Device;
import play.Logger;
import play.db.DB;
import play.libs.Json;

public class DevicePersistency {
	private static final String TABLE_NAME = "devices";
	
	private static final String ALL_FIELDS = "id, user_id, os, os_version, device_identifier, brand, model, messaging_registration_id";
	
	/**
	 * Creates the specified device. If creation was successfull then the {id} property of the device will be set (greater 0).
	 * @param device The device that should be persisted in the database.
	 */
	public static void create(Device device) {
		DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"INSERT INTO " + TABLE_NAME + " (user_id, os, os_version, device_identifier, brand, model) VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			s.setLong(1, device.userId);
			s.setString(2, device.operatingSystem);
			s.setString(3, device.osVersion);
			s.setString(4, device.deviceIdentifier);
			s.setString(5, device.brand);
			s.setString(6, device.model);
			int affectedRows = s.executeUpdate();
	
			if (affectedRows != 0) {
				ResultSet generatedKeys = s.getGeneratedKeys();
				generatedKeys.next();
				device.id = generatedKeys.getLong(1);
			}
		});
	}
	
	/**
	 * Updates the device (according to id) with the provided spec.
	 * @param d
	 */
	public static void update(Device device) {
		DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"UPDATE " + TABLE_NAME + " SET os = ?, os_version = ?, device_identifier = ?, brand = ?, model = ? WHERE id = ?");
			s.setString(1, device.operatingSystem);
			s.setString(2, device.osVersion);
			s.setString(3, device.deviceIdentifier);
			s.setString(4, device.brand);
			s.setString(5, device.model);
			s.setLong(6, device.id);
			
			s.executeUpdate();
		});
	}
	
	/**
	 * Checks if the device with ID {id} exists
	 * @param id
	 * @return
	 */
	public static boolean doesExist(long id) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn
					.prepareStatement("SELECT id FROM " + TABLE_NAME + " WHERE id = ?");
			s.setLong(1, id);
			ResultSet result = s.executeQuery();
	
			return result != null && result.next();
		});
	}
	
	public static boolean doesExist(Device d) {
		return doesExist(d.id);
	}
	
	/**
	 * Checks if Device with id {deviceId} is owned by user with id {userId}
	 * @param deviceId
	 * @param userId
	 * @return TRUE if the specified device is owned by the specified user, otherwise FALSE
	 */
	public static boolean ownedByUser(long deviceId, long userId) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn
					.prepareStatement("SELECT id FROM " + TABLE_NAME + " WHERE user_id = ? AND id = ?");
			s.setLong(1, userId);
			s.setLong(2, deviceId);
			ResultSet result = s.executeQuery();
	
			return result != null && result.next();
		});
	}
	
	public static boolean linkDeviceToMessagingService(long deviceId, String messagingRegistrationId) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"UPDATE " + TABLE_NAME + " SET messaging_registration_id = ? WHERE id = ?");
			s.setString(1, messagingRegistrationId);
			s.setLong(2, deviceId);
			
			return s.executeUpdate() != 0;
		});
	}
	
	
	
	public static Device[] findDevicesById(long[] deviceIds) {
		String idsAsString = Arrays.toString(deviceIds).replaceAll("\\[", "(").replaceAll("\\]", ")");
		return findDevices("WHERE id IN " + idsAsString, null);
	}
	
	public static Device[] findDevicesOfUser(long userId) {
		return findDevices("WHERE user_id = ?", userId);
	}
	
	private static Device[] findDevices(String where, Object... params) {
		return DB.withConnection(conn -> {
			Device[] devices = new QueryRunner()
			.query(conn, "SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME + " " + where, new ArrayListHandler(), params)
			.stream()
			.map(array -> {
				return convertResultArrayToDevice(array);
			}).toArray(Device[]::new);
			
			return devices;
		});
	}
	
	private static Device convertResultArrayToDevice(Object[] array) {
		Long id = (Long)array[0];
		
		Long userId = (Long)array[1];
		
		String operatingSystem = (String)array[2];
		
		String osVersion = (String)array[3];
		
		String deviceIdentifier = (String)array[4];
		
		String brand = (String)array[5];
		
		String model = (String)array[6];
		
		String messagingRegistrationId = (String)array[7];
		
		return new Device(id, userId, operatingSystem, osVersion, deviceIdentifier, brand, model, messagingRegistrationId);
	}
}