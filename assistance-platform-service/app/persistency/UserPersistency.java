package persistency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.Consumer;

import models.User;

import org.mindrot.jbcrypt.BCrypt;

import play.Logger;
import play.db.DB;

public class UserPersistency {
	private static String TABLE_NAME = "users";
	
	public static void createAndUpdateIdOnSuccess(User user, String password) {
		String hashedPassword = hashPassword(password);
	
		if (UserPersistency.doesUserWithEmailExist(user.email)) {
			return;
		}
	
		DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"INSERT INTO " + TABLE_NAME + " (email, password, joined_since) VALUES (?, ?, CURRENT_TIMESTAMP)",
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
	
	public static void updateLastLogin(Long id) {
		DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"UPDATE " + TABLE_NAME + " SET last_login = CURRENT_TIMESTAMP");
			s.executeUpdate();
		});
	}
	
	public static void updateProfile(User u) {
		DB.withConnection(conn -> {
			PreparedStatement s = conn.prepareStatement(
					"UPDATE " + TABLE_NAME + " SET firstname = ?, lastname = ? WHERE id = ?");
			s.setString(1, u.firstName);
			s.setString(2, u.lastName);
			s.setLong(3, u.id);
			s.executeUpdate();
		});
	}

	private static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public static boolean doesUserWithEmailExist(String email) {
		return DB.withConnection(conn -> {
			PreparedStatement s = conn
					.prepareStatement("SELECT id FROM " + TABLE_NAME + " WHERE email = ?");
			s.setString(1, email);
			ResultSet result = s.executeQuery();
	
			return result != null && result.next();
		});
	}
	
	public static String getPasswordFromUserWithMail(String email) {
		return DB
				.withConnection(conn -> {
					PreparedStatement s = conn
							.prepareStatement("SELECT password FROM " + TABLE_NAME + " WHERE email = ?");
					s.setString(1, email);
					ResultSet result = s.executeQuery();
	
					if(result != null && result.next()) {
						String hashedPw = result.getString(1);
						return hashedPw;
					}
					
					return "";
				});
	}

	public static User findUserByEmail(String email, boolean fullProfile) {
		Consumer<PreparedStatement> parameterSetter = (p) -> { 
			try {
				p.setString(1, email);
			} catch (SQLException e) {
				Logger.error("Some SQL Exception in findUserByEmail", e);
			}
		};
		
		return findUser("email = ?", parameterSetter, fullProfile);
	}
	
	public static User findUserById(Long id, boolean fullProfile) {
		Consumer<PreparedStatement> parameterSetter = (p) -> { 
			try {
				p.setLong(1, id);
			} catch (SQLException e) {
				Logger.error("Some SQL Exception in findUserById", e);
			}
		};
		
		return findUser("id = ?", parameterSetter, fullProfile);
	}
	
	private static User findUser(String whereForPreparedStatement, Consumer<PreparedStatement> parameterSetter, boolean fullProfile) {
		return DB
				.withConnection(conn -> {
					PreparedStatement s = getPreparedStatementForFindUser(conn, whereForPreparedStatement, fullProfile);
					
					parameterSetter.accept(s);
					
					ResultSet result = s.executeQuery();
	
					if (result != null && result.next()) {
						Long id = result.getLong("id");
						String email = result.getString("email");
						
						if(fullProfile) {
							String firstName = result.getString("firstname");
							String lastName = result.getString("lastname");
							
							LocalDateTime joinedSince = result.getTimestamp("joined_since").toLocalDateTime();

							Timestamp lastLoginTimestamp = result.getTimestamp("last_login");
							LocalDateTime lastLogin = lastLoginTimestamp != null ? result.getTimestamp("last_login").toLocalDateTime() : null;
							
							return new User(id, email, firstName, lastName, joinedSince, lastLogin);
						}
	
						return new User(id, email);
					}
	
					return null;
				});
	}
	
	private static PreparedStatement getPreparedStatementForFindUser(final Connection conn, final String whereCondition, final boolean fullProfile) throws SQLException {
		String fieldsToRetreive = "id, email";
		
		if(fullProfile) {
			fieldsToRetreive += ", firstname, lastname, joined_since, last_login";
		}
		
		PreparedStatement s = conn
				.prepareStatement("SELECT " + fieldsToRetreive + " FROM " + TABLE_NAME + " WHERE " + whereCondition);
		
		return s;
	}

}
