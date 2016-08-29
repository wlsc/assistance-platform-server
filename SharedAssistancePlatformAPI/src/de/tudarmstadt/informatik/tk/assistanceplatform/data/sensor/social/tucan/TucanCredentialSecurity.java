package de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.social.tucan;

import de.tudarmstadt.informatik.tk.assistanceplatform.util.AesEncryption;

public class TucanCredentialSecurity {
  public static TucanCredentials encrpytCredentials(TucanCredentials clearText, String secret)
      throws Exception {
    String encryptedUsername = AesEncryption.encrypt(clearText.username, secret);

    String encryptedPassword = AesEncryption.encrypt(clearText.password, secret);

    // TODO: Maybe do proper cloning?
    TucanCredentials result = clearText;
    result.username = encryptedUsername;
    result.password = encryptedPassword;

    return result;
  }

  public static TucanCredentials decrpytCredentials(TucanCredentials cryptedCredentials,
      String secret) throws Exception {
    String decryptedUsername = AesEncryption.decrypt(cryptedCredentials.username, secret);

    String decryptedPassword = AesEncryption.decrypt(cryptedCredentials.password, secret);

    // TODO: Maybe do proper cloning?
    TucanCredentials result = cryptedCredentials;
    result.username = decryptedUsername;
    result.password = decryptedPassword;

    return result;
  }
}
