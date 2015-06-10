package models;

import org.mindrot.jbcrypt.BCrypt;

import persistency.UserPersistency;


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
	
	public static boolean authenticate(String mail, String password) {
		String encryptedPwOfUser = UserPersistency.getPasswordFromUserWithMail(mail);
		return encryptedPwOfUser.length() != 0 && BCrypt.checkpw(password, encryptedPwOfUser);
	}
}