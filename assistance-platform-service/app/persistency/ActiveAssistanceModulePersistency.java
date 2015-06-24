package persistency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;



import org.apache.commons.dbutils.QueryRunner;

import org.apache.commons.dbutils.handlers.ArrayListHandler;

import models.ActiveAssistanceModule;
import play.db.DB;

public class ActiveAssistanceModulePersistency {
	private static String TABLE_NAME = "active_modules";
	
	private static String allFields = "id, name, logo_url, description_short, description_long, required_capabilities, optional_capabilities, copyright";
	
	public static boolean create(ActiveAssistanceModule module) {
		if (doesModuleWithIdExist(module.id)) {
			return false;
		}
	
		return DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"INSERT INTO " + TABLE_NAME + " (" + allFields + ") VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?)");
			
			s.setString(1, module.id);
			s.setString(2, module.name);
			s.setString(3, module.logoUrl);
			s.setString(4, module.descriptionShort);
			s.setString(5, module.descriptionLong);
			s.setString(6, String.join(",", module.requiredCapabilities));
			s.setString(7, String.join(",", module.optionalCapabilites));
			s.setString(8, module.copyright);
			
			int affectedRows = s.executeUpdate();
	
			if (affectedRows != 0) {
				return true;
			}
			
			return false;
		});
	}
	
	public static boolean doesModuleWithIdExist(String id) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn
					.prepareStatement("SELECT id FROM " + TABLE_NAME + " WHERE id = ?");
			s.setString(1, id);
			ResultSet result = s.executeQuery();
	
			return result != null && result.next();
		});
	}
	
	public static ActiveAssistanceModule[] list() {
		return DB.withConnection(conn -> {
			//new QueryRunner().
			ActiveAssistanceModule[] modules = new QueryRunner()
			.query(conn, "SELECT " + allFields + " FROM " + TABLE_NAME, new ArrayListHandler())
			.stream()
			.map(array -> {
				String id = (String)array[0];
				String name = (String)array[1];
				String logoUrl = (String)array[2];
				String description_short = (String)array[3];
				String description_long = (String)array[4];
				String[] requiredCapabilities = ((String)array[5]).split(",");
				String[] optionalCapabilities = ((String)array[6]).split(",");
				String copyright = (String)array[7];
				
				return new ActiveAssistanceModule(name, id, logoUrl, description_short, description_long, requiredCapabilities, optionalCapabilities, copyright);
			}).toArray(ActiveAssistanceModule[]::new);

			
			return modules;
		});
	}
}
