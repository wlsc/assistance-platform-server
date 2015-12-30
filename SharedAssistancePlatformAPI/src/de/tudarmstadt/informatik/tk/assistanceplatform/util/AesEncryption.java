package de.tudarmstadt.informatik.tk.assistanceplatform.util;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

public class AesEncryption {
	private static final String ALGO = "AES";

	public static String encrypt(String data, String secret) throws Exception {
		Key key = generateKey(secret);
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(data.getBytes());
		String encryptedValue = Base64.getEncoder().encodeToString(encVal);
		return encryptedValue;
	}

	public static String decrypt(String encryptedData, String secret)
			throws Exception {
		Key key = generateKey(secret);
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);

		byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}

	private static Key generateKey(String secret) throws Exception {
		byte[] secretAsBytes = secret.getBytes();
		
		MessageDigest sha = MessageDigest.getInstance("SHA1");
		secretAsBytes = sha.digest(secretAsBytes);
		
		// nur die ersten 128 bit nutzen
		secretAsBytes = Arrays.copyOf(secretAsBytes, 16); 

		return new SecretKeySpec(secretAsBytes, ALGO);
	}
}
