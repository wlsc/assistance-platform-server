package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;

import play.db.DB;

public class User {

	public Long id = 0L;
	public String email;

	public User(String email) {
		this.email = email;
	}

	public User(Long id, String email) {
		this.id = id;
		this.email = email;
	}

	public static void createAndUpdateIdOnSuccess(User user, String password) {
		String hashedPassword = hashPassword(password);

		if (doesUserWithEmailExist(user.email)) {
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
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		return hashedPassword;
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

	public static boolean authenticateUser(String email, String password) {
		return DB
				.withConnection(conn -> {
					PreparedStatement s = conn
							.prepareStatement("SELECT password FROM users WHERE email = ?");
					s.setString(1, email);
					ResultSet result = s.executeQuery();

					if(result != null && result.next()) {
						String hashedPw = result.getString(1);
						
						return BCrypt.checkpw(password, hashedPw);
					}
					
					return false;
				});
	}

	public static User findUserByEmail(String email) {
		return DB
				.withConnection(conn -> {
					PreparedStatement s = conn
							.prepareStatement("SELECT id, password FROM users WHERE email = ?");
					s.setString(1, email);
					ResultSet result = s.executeQuery();

					if (result != null && result.next()) {
						Long id = result.getLong(1);

						return new User(id, email);
					}

					return null;
				});
	}
}