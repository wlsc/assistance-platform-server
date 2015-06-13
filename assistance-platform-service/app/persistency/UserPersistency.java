package persistency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import models.User;

import org.mindrot.jbcrypt.BCrypt;

import play.db.DB;

public class UserPersistency {

	public static void createAndUpdateIdOnSuccess(User user, String password) {
		String hashedPassword = hashPassword(password);
	
		if (UserPersistency.doesUserWithEmailExist(user.email)) {
			return;
		}
	
		DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"INSERT INTO users (email, password) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			s.setString(1, user.email);
			s.setString(2, hashedPassword);
			int affectedRows = s.executeUpdate();
	
			if (affectedRows != 0) {
				ResultSet generatedKeys = s.getGeneratedKeys();
				generatedKeys.next();
				user.id = generatedKeys.getLong(1);
			}
		});
	}

	private static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public static boolean doesUserWithEmailExist(String email) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn
					.prepareStatement("SELECT id FROM users WHERE email = ?");
			s.setString(1, email);
			ResultSet result = s.executeQuery();
	
			return result != null && result.next();
		});
	}
	
	public static String getPasswordFromUserWithMail(String email) {
		return DB
				.withConnection(conn -> {
					PreparedStatement s = conn
							.prepareStatement("SELECT password FROM users WHERE email = ?");
					s.setString(1, email);
					ResultSet result = s.executeQuery();
	
					if(result != null && result.next()) {
						String hashedPw = result.getString(1);
						return hashedPw;
					}
					
					return "";
				});
	}

	public static User findUserByEmail(String email) {
		return DB
				.withConnection(conn -> {
					PreparedStatement s = conn
							.prepareStatement("SELECT id FROM users WHERE email = ?");
					s.setString(1, email);
					ResultSet result = s.executeQuery();
	
					if (result != null && result.next()) {
						Long id = result.getLong(1);
	
						return new User(id, email);
					}
	
					return null;
				});
	}
	
	public static User findUserById(Long id) {
		return DB
				.withConnection(conn -> {
					PreparedStatement s = conn
							.prepareStatement("SELECT email FROM users WHERE id = ?");
					s.setLong(1, id);
					ResultSet result = s.executeQuery();
	
					if (result != null && result.next()) {
						String email = result.getString(1);
	
						return new User(id, email);
					}
	
					return null;
				});
	}

}
