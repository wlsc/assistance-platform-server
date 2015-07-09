package models;

import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;

import persistency.UserPersistency;


public class User {
	public Long id = 0L;
	public String email;
	
	public String firstName;
	public String lastName;
	
	public LocalDateTime joinedSince;
	public LocalDateTime lastLogin;

	public User(String email) {
		this.email = email;
	}

	public User(Long id, String email) {
		this.id = id;
		this.email = email;
	}
	
	public User(Long id, String email, String firstName, String lastName, LocalDateTime joined_since, LocalDateTime last_login) {
		this(id, email);
		this.firstName = firstName;
		this.lastName = lastName;
		this.joinedSince = joined_since;
		this.lastLogin = last_login;
	}
	
	public static boolean authenticate(String mail, String password) {
		String encryptedPwOfUser = UserPersistency.getPasswordFromUserWithMail(mail);
		return encryptedPwOfUser.length() != 0 && BCrypt.checkpw(password, encryptedPwOfUser);
	}
}