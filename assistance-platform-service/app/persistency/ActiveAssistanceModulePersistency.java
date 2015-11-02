package persistency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import models.ActiveAssistanceModule;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import play.db.DB;
import play.libs.Json;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Capability;

public class ActiveAssistanceModulePersistency {
	private static String TABLE_NAME = "active_modules";
	private static String LOCALIZATION_TABLE_NAME = "active_module_localization";
	
	private static String allFields = "id, name, logo_url, description_short, description_long, required_capabilities, optional_capabilities, copyright, administrator_email, support_email, rest_contact_address";
	
	public static boolean create(ActiveAssistanceModule module) {
		if (doesModuleWithIdExist(module.id)) {
			return false;
		}
	
		return DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"INSERT INTO " + TABLE_NAME + " (" + allFields + ") VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			ObjectMapper mapper = new ObjectMapper();
			
			s.setString(1, module.id);
			s.setString(2, module.name);
			s.setString(3, module.logoUrl);
			s.setString(4, module.descriptionShort);
			s.setString(5, module.descriptionLong);
			s.setString(6, mapper.valueToTree(module.requiredCapabilities).toString());
			s.setString(7, mapper.valueToTree(module.optionalCapabilites).toString());
			s.setString(8, module.copyright);
			s.setString(9, module.administratorEmail);
			s.setString(10, module.supportEmail);
			s.setString(11, module.restContactAddress);
			
			int affectedRows = s.executeUpdate();
			
			s.close();
	
			return affectedRows != 0;
		});
	}
	
	public static boolean update(ActiveAssistanceModule module) {
		if (!doesModuleWithIdExist(module.id)) {
			return false;
		}
	
		return DB.withConnection(conn -> {
			String allFieldsForUpdate = "SET " + allFields.replace("id,", "").replaceAll(",", " = ?, ") + " = ?";
			
			PreparedStatement s = conn.prepareStatement(
					"UPDATE " + TABLE_NAME + " " + allFieldsForUpdate + " WHERE id = ?");
			
			ObjectMapper mapper = new ObjectMapper();
			
			s.setString(1, module.name);
			s.setString(2, module.logoUrl);
			s.setString(3, module.descriptionShort);
			s.setString(4, module.descriptionLong);
			s.setString(5, mapper.valueToTree(module.requiredCapabilities).toString());
			s.setString(6, mapper.valueToTree(module.optionalCapabilites).toString());
			s.setString(7, module.copyright);
			s.setString(8, module.administratorEmail);
			s.setString(9, module.supportEmail);
			s.setString(10, module.restContactAddress);
			s.setString(11, module.id);
			
			int affectedRows = s.executeUpdate();
			
			s.close();
	
			return affectedRows != 0;
		});
	}
	
	public static boolean setIsAlive(String moduleId) {
		if (!doesModuleWithIdExist(moduleId)) {
			return false;
		}
	
		return DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"UPDATE " + TABLE_NAME + " SET is_alive = TRUE, last_alive_message = CURRENT_TIMESTAMP WHERE id = ?");
			
			s.setString(1, moduleId);
			
			int affectedRows = s.executeUpdate();
			
			s.close();
	
			return affectedRows != 0;
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
			
			tmpDelete.close();
			
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
			
			s.close();
	
			return affectedRows != 0;
		});
	}
	
	public static boolean doesModuleWithIdExist(String id) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn
					.prepareStatement("SELECT id FROM " + TABLE_NAME + " WHERE id = ?");
			s.setString(1, id);
			
			ResultSet result = s.executeQuery();
			boolean returnResult = result != null && result.next();
			
			s.close();
			result.close();
			
			return returnResult;
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
				Capability[] requiredCapabilities = Json.fromJson(Json.parse(requiredCapsRaw), Capability[].class);
				
				
				String optionalCapsRaw = (String)array[6];
				Capability[] optionalCapabilities = Json.fromJson(Json.parse(optionalCapsRaw), Capability[].class);
				
				String copyright = (String)array[7];
				
				String administratorEmail = (String)array[8];
				
				String supportEmail = (String)array[9];
				
				String restAddress = (String)array[9];
				
				return new ActiveAssistanceModule(name, id, logoUrl, description_short, description_long, requiredCapabilities, optionalCapabilities, copyright, administratorEmail, supportEmail, restAddress);
			}).toArray(ActiveAssistanceModule[]::new);

			
			return modules;
		});
	}
}
