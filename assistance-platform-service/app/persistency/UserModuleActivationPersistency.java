package persistency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import org.apache.commons.lang.ArrayUtils;

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
			
			boolean result = s.executeUpdate() != 0;
			
			s.close();
			
			return result;
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
			
			boolean result = s.executeUpdate() != 0;
			
			s.close();
			
			return result;
		});
	}
	
	public static boolean doesActivationExist(Long userId, String moduleId) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn
					.prepareStatement("SELECT user_id FROM " + TABLE_NAME + " WHERE user_id = ? AND module_id = ?");
			s.setLong(1, userId);
			s.setString(2, moduleId);
			ResultSet result = s.executeQuery();
	
			boolean returnResult = result != null && result.next();
			
			result.close();
			s.close();
			
			return returnResult;
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
	
	public static long[] userActivationsForModule(String moduleId) {
		return DB.withConnection(conn -> {

			Long[] userIds = new QueryRunner()
			.query(conn, "SELECT user_id FROM " + TABLE_NAME + " WHERE module_id = ?", new ArrayListHandler(), moduleId)
			.stream()
			.map(array -> {
				long id = (long)array[0];
				
				return id;
			}).toArray(Long[]::new);

			
			return ArrayUtils.toPrimitive( userIds );
		});
	}
}
