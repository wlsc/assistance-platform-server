package persistency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

import models.Device;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import play.db.DB;

public class DevicePersistency {
	private static final String TABLE_NAME = "devices";

	private static final String ALL_FIELDS = "id, user_id, os, os_version, device_identifier, brand, model, messaging_registration_id, user_defined_name, last_usage";

	/**
	 * Creates the specified device.
	 * If it does not already exists the ID of the object will be set to the created id. 
	 * If it already exists, then the ID will be set to the ID of the existing device.
	 * 
	 * @param device
	 *            The device that should be persisted in the database.
	 */
	public static void createIfNotExists(Device d) {
		long potentialExistingId = DevicePersistency.idOfDeviceWithSpec(d);
		
		if(potentialExistingId != -1) {
			d.id = potentialExistingId;
		} else {
			DevicePersistency.create(d);
		}
	}
	

	/**
	 * Creates the specified device. If creation was successfull then the {id}
	 * property of the device will be set (greater 0).
	 * 
	 * @param device
	 *            The device that should be persisted in the database.
	 */
	private static void create(Device device) {
		DB.withConnection(conn -> {
			PreparedStatement s = conn
					.prepareStatement(
							"INSERT INTO "
									+ TABLE_NAME
									+ " (user_id, os, os_version, device_identifier, brand, model) VALUES (?, ?, ?, ?, ?, ?)",
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
				
				generatedKeys.close();
				s.close();
			}
		});
	}
	

	/**
	 * Updates the device (according to id) with the provided spec.
	 * 
	 * @param d
	 */
	public static void update(Device device) {
		DB.withConnection(conn -> {
			PreparedStatement s = conn
					.prepareStatement("UPDATE "
							+ TABLE_NAME
							+ " SET os = ?, os_version = ?, device_identifier = ?, brand = ?, model = ? WHERE id = ?");
			s.setString(1, device.operatingSystem);
			s.setString(2, device.osVersion);
			s.setString(3, device.deviceIdentifier);
			s.setString(4, device.brand);
			s.setString(5, device.model);
			s.setLong(6, device.id);

			s.executeUpdate();
			
			s.close();
		});
	}

	/**
	 * Checks if the device with ID {id} exists
	 * 
	 * @param id
	 * @return
	 */
	public static boolean doesExist(long id) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement("SELECT id FROM "
					+ TABLE_NAME + " WHERE id = ?");
			s.setLong(1, id);
			ResultSet result = s.executeQuery();

			boolean returnResult = result != null && result.next();
			
			result.close();
			s.close();
			
			return returnResult;
		});
	}

	/**
	 * Checks if the device exists. If a ID is set then only the ID will be
	 * checked, otherwise the whole spec of the device.
	 * 
	 * @param d
	 *            The device object
	 * @return TRUE if the device already exists, otherwise false
	 */
	public static boolean doesExist(Device d) {
		return doesExist(d.id);
	}

	/**
	 * Tries to find the ID of a device by searching for the exact same
	 * specification.
	 * 
	 * @param deviceSpec
	 *            The Specification of the device
	 * @return The ID of the matching device. If the device spec contains an ID,
	 *         then this ID will be returned. If no device is found then -1 is
	 *         returned.
	 */
	public static long idOfDeviceWithSpec(Device deviceSpec) {
		if (deviceSpec.id > 0) {
			return deviceSpec.id;
		}

		return DB.withConnection(conn -> {
			PreparedStatement s = conn
					.prepareStatement("SELECT id FROM "
							+ TABLE_NAME
							+ " WHERE user_id = ? AND os = ? AND os_version = ? AND device_identifier = ? AND brand = ? AND model = ?"
							+ " ORDER BY id ASC");
			
			s.setLong(1, deviceSpec.userId);
			s.setString(2, deviceSpec.operatingSystem);
			s.setString(3, deviceSpec.osVersion);
			s.setString(4, deviceSpec.deviceIdentifier);
			s.setString(5, deviceSpec.brand);
			s.setString(6, deviceSpec.model);
			
			ResultSet result = s.executeQuery();

			long returnResult = -1L;
			
			if (result != null && result.next()) {
				long id = result.getLong(1);
				returnResult = id;
				result.close();
			}
			
			s.close();

			return returnResult;
		});
	}

	/**
	 * Checks if Device with id {deviceId} is owned by user with id {userId}
	 * 
	 * @param deviceId
	 * @param userId
	 * @return TRUE if the specified device is owned by the specified user,
	 *         otherwise FALSE
	 */
	public static boolean ownedByUser(long deviceId, long userId) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement("SELECT id FROM "
					+ TABLE_NAME + " WHERE user_id = ? AND id = ?");
			s.setLong(1, userId);
			s.setLong(2, deviceId);
			ResultSet result = s.executeQuery();

			boolean returnResult = result != null && result.next();
			
			result.close();
			s.close();
			
			return returnResult;
		});
	}

	public static boolean linkDeviceToMessagingService(long deviceId,
			String messagingRegistrationId) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement("UPDATE " + TABLE_NAME
					+ " SET messaging_registration_id = ? WHERE id = ?");
			s.setString(1, messagingRegistrationId);
			s.setLong(2, deviceId);

			boolean result = s.executeUpdate() != 0;
			
			s.close();
			
			return result;
		});
	}

	public static boolean setUserDefinedName(long deviceId, String name) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement("UPDATE " + TABLE_NAME
					+ " SET user_defined_name = ? WHERE id = ?");
			s.setString(1, name);
			s.setLong(2, deviceId);

			boolean result = s.executeUpdate() != 0;
			
			s.close();
			
			return result;
		});
	}

	public static boolean updateLastActivityOfDevice(long deviceId) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement("UPDATE " + TABLE_NAME
					+ " SET last_usage = CURRENT_TIMESTAMP WHERE id = ?");
			s.setLong(1, deviceId);

			boolean result = s.executeUpdate() != 0;
			
			s.close();
			
			return result;
		});
	}

	public static Device[] findDevicesById(long[] deviceIds) {
		String idsAsString = Arrays.toString(deviceIds).replaceAll("\\[", "(")
				.replaceAll("\\]", ")");
		return findDevices("WHERE id IN " + idsAsString, (Object[]) null);
	}

	public static Device[] findDevicesOfUser(long userId) {
		return findDevices("WHERE user_id = ?", userId);
	}

	private static Device[] findDevices(String where, Object... params) {
		return DB.withConnection(conn -> {
			Device[] devices = new QueryRunner()
					.query(conn,
							"SELECT " + ALL_FIELDS + " FROM " + TABLE_NAME
									+ " " + where, new ArrayListHandler(),
							params).stream().map(array -> convertResultArrayToDevice(array)).toArray(Device[]::new);

			return devices;
		});
	}

	private static Device convertResultArrayToDevice(Object[] array) {
		Long id = (Long) array[0];

		Long userId = (Long) array[1];

		String operatingSystem = (String) array[2];

		String osVersion = (String) array[3];

		String deviceIdentifier = (String) array[4];

		String brand = (String) array[5];

		String model = (String) array[6];

		String messagingRegistrationId = (String) array[7];

		String user_defined_name = (String) array[8];

		java.sql.Timestamp timestamp = ((java.sql.Timestamp) array[9]);
		long lastUsage = timestamp == null ? 0 : timestamp.toInstant()
				.getEpochSecond();

		return new Device(id, userId, operatingSystem, osVersion,
				deviceIdentifier, brand, model, messagingRegistrationId,
				user_defined_name, lastUsage);
	}
}