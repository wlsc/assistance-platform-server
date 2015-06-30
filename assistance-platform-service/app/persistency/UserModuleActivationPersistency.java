package persistency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import models.UserModuleActivation;
import play.db.DB;

public class UserModuleActivationPersistency {
	private static String TABLE_NAME = "users_modules";
	
	public static boolean create(UserModuleActivation activation) {
		Long userId = activation.userId;
		String moduleId = activation.moduleId;
		
		if (doesActivationExist(userId, moduleId)) {
			return false;
		}
	
		return DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"INSERT INTO " + TABLE_NAME + " (user_id, module_id, creation_time) VALUES "
					+ "(?, ?, CURRENT_TIMESTAMP)");
			
			s.setLong(1, userId);
			s.setString(2, moduleId);
			
			int affectedRows = s.executeUpdate();
	
			if (affectedRows != 0) {
				return true;
			}
			
			return false;
		});
	}
	
	public static boolean remove(UserModuleActivation activation) {
		Long userId = activation.userId;
		String moduleId = activation.moduleId;
		
		return DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"DELETE FROM " + TABLE_NAME + " WHERE user_id = ? AND module_id = ?");
			
			s.setLong(1, userId);
			s.setString(2, moduleId);
			
			int affectedRows = s.executeUpdate();
	
			if (affectedRows != 0) {
				return true;
			}
			
			return false;
		});
	}
	
	public static boolean doesActivationExist(Long userId, String moduleId) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn
					.prepareStatement("SELECT user_id FROM " + TABLE_NAME + " WHERE user_id = ? AND module_id = ?");
			s.setLong(1, userId);
			s.setString(2, moduleId);
			ResultSet result = s.executeQuery();
	
			return result != null && result.next();
		});
	}
	
	public static boolean doesActivationExist(UserModuleActivation activation) {
		return doesActivationExist(activation.userId, activation.moduleId);
	}
}
