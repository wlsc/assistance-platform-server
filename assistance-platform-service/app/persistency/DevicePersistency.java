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
}