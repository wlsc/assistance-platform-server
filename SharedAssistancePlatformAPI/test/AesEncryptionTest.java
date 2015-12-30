import java.util.Random;

import org.junit.Test;

import de.tudarmstadt.informatik.tk.assistanceplatform.util.AesEncryption;
import static org.junit.Assert.assertEquals;


public class AesEncryptionTest {
	@Test
	public void test() throws Exception {
		String data = "As of Java 8, there is an officially supported API for Base64 encoding and decoding. In time this will probably become the default choice.";
		
		String secret = "supersecret";
		
		assertEquals( AesEncryption.decrypt(AesEncryption.encrypt(data, secret), secret), data );
		assertEquals( AesEncryption.decrypt(AesEncryption.encrypt(data, data), data), data );
	}
	
	@Test
	public void longdatatest() throws Exception {
		String data = "As of Java 8, there is an officially supported API for Base64 encoding and decoding. In time this will probably become the default choice.";
		
		String longdata = data;
		
		for(int i = 0; i < 1000; i++) {
			longdata += data;
		}
		
		String secret = "supersecret";
		
		assertEquals( AesEncryption.decrypt(AesEncryption.encrypt(longdata, secret), secret), longdata );
		assertEquals( AesEncryption.decrypt(AesEncryption.encrypt(longdata, longdata), longdata), longdata );
	}
}
