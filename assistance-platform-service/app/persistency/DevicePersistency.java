package persistency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import models.Device;
import play.db.DB;

public class DevicePersistency {
	private static String TABLE_NAME = "devices";
	
	/**
	 * Creates the specified device. If creation was successfull then the {id} property of the device will be set (greater 0).
	 * @param device The device that should be persisted in the database.
	 */
	public static void create(Device device) {
		DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"INSERT INTO " + TABLE_NAME + " (user_id, os, os_version, device_identifier, brand, model, messaging_registration_id) VALUES (?, ?, ?, ?, ?, ?)",
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
			
			s.setLong(7, device.id);
			
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
					.prepareStatement("SELECT id FROM " + TABLE_NAME + " WHERE user_id = ?");
			s.setLong(1, userId);
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
}