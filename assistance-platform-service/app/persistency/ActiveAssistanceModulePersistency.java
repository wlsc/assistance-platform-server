package persistency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


import models.ActiveAssistanceModule;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;


import play.Logger;
import play.db.DB;

public class ActiveAssistanceModulePersistency {
	private static String TABLE_NAME = "active_modules";
	private static String LOCALIZATION_TABLE_NAME = "active_module_localization";
	
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
			s.setString(6, module.requiredCapabilities == null ? "" : String.join(",", module.requiredCapabilities));
			s.setString(7, module.optionalCapabilites == null ? "" : String.join(",", module.optionalCapabilites));
			s.setString(8, module.copyright);
			
			int affectedRows = s.executeUpdate();
	
			if (affectedRows != 0) {
				return true;
			}
			
			return false;
		});
	}
	
	public static boolean localize(String languageCode, ActiveAssistanceModule module) {
		if (!doesModuleWithIdExist(module.id)) {
			return false;
		}
	
		return DB.withConnection(conn -> {
			
			PreparedStatement tmpDelete = conn.prepareStatement("DELETE FROM " + LOCALIZATION_TABLE_NAME + " WHERE module_id = ? AND language_code = ?");
			
			tmpDelete.setString(1, module.id);
			tmpDelete.setString(2, languageCode);
			
			tmpDelete.executeUpdate();
			
			PreparedStatement s = conn.prepareStatement(
					"INSERT INTO " + LOCALIZATION_TABLE_NAME + " (module_id, language_code, name, logo_url, description_short, description_long) VALUES "
					+ "(?, ?, ?, ?, ?, ?)");
			
			s.setString(1, module.id);
			s.setString(2, languageCode);
			s.setString(3, module.name);
			s.setString(4, module.logoUrl);
			s.setString(5, module.descriptionShort);
			s.setString(6, module.descriptionLong);
			
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
		return list("en");
	}
	
	public static ActiveAssistanceModule[] list(String language) {
		return DB.withConnection(conn -> {
			String fields = ActiveAssistanceModulePersistency.allFields
			.replace("name", "COALESCE(l.name, m.name)")
			.replace("logo_url", "COALESCE(l.logo_url, m.logo_url)")
			.replace("description_short", "COALESCE(l.description_short, m.description_short)")
			.replace("description_long", "COALESCE(l.description_long, m.description_long)");
			
			ActiveAssistanceModule[] modules = new QueryRunner()
			.query(conn, "SELECT " + fields + " FROM " + TABLE_NAME + " m LEFT JOIN " + LOCALIZATION_TABLE_NAME + " AS l ON l.module_id = m.id AND l.language_code = ?", new ArrayListHandler(), language)
			.stream()
			.map(array -> {
				String id = (String)array[0];
				String name = (String)array[1];
				String logoUrl = (String)array[2];
				String description_short = (String)array[3];
				String description_long = (String)array[4];
				
				String requiredCapsRaw = (String)array[5];
				String[] requiredCapabilities = requiredCapsRaw.length() == 0 ? new String[] { } : requiredCapsRaw.split(",");
				
				String optionalCapsRaw = (String)array[6];
				String[] optionalCapabilities = optionalCapsRaw.length() == 0 ? new String[] { } : optionalCapsRaw.split(",");
				
				String copyright = (String)array[7];
				
				return new ActiveAssistanceModule(name, id, logoUrl, description_short, description_long, requiredCapabilities, optionalCapabilities, copyright);
			}).toArray(ActiveAssistanceModule[]::new);

			
			return modules;
		});
	}
}
