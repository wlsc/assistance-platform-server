package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.social.tucan;

import java.security.Key;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import de.tudarmstadt.informatik.tk.assistanceplatform.util.AesEncryption;

public class TucanCredentialSecurity {
	public static TucanCredentials encrpytCredentials(
			TucanCredentials clearText, String secret) throws Exception {
		String encryptedUsername = AesEncryption.encrypt(clearText.username, secret);
		
		String encryptedPassword = AesEncryption.encrypt(clearText.password, secret);
		
		return new TucanCredentials(encryptedUsername, encryptedPassword);
	}

	public static TucanCredentials decrpytCredentials(
			TucanCredentials cryptedCredentials, String secret) throws Exception {
		String decryptedUsername = AesEncryption.decrypt(cryptedCredentials.username, secret);
		
		String decryptedPassword = AesEncryption.decrypt(cryptedCredentials.password, secret);
		
		return new TucanCredentials(decryptedUsername, decryptedPassword);
	}
}