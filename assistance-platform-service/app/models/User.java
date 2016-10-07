package models;

import org.mindrot.jbcrypt.BCrypt;
import persistency.UserPersistency;

import java.time.LocalDateTime;

/**
 * This class represents the platform user.
 */
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
        return !encryptedPwOfUser.isEmpty() && BCrypt.checkpw(password, encryptedPwOfUser);
    }
}