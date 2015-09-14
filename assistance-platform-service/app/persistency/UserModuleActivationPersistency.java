package persistency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Capability;
import models.ActiveAssistanceModule;
import models.UserModuleActivation;
import play.db.DB;
import play.libs.Json;

public class UserModuleActivationPersistency {
	private static String TABLE_NAME = "users_modules";
	
	public static boolean create(UserModuleActivation activation) {
		Long userId = activation.userId;
		String moduleId = activation.moduleId;
		
		if (doesActivationExist(userId, moduleId)) {
			return true;
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
	
	public static String[] activatedModuleIdsForUser(long userId) {
		return DB.withConnection(conn -> {

			String[] modules = new QueryRunner()
			.query(conn, "SELECT module_id FROM " + TABLE_NAME + " WHERE user_id = ?", new ArrayListHandler(), userId)
			.stream()
			.map(array -> {
				String module = (String)array[0];
				
				return module;
			}).toArray(String[]::new);

			
			return modules;
		});
	}
}
