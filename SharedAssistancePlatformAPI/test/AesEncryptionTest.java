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
	
	@Test
	public void decrypttest() throws Exception {
		String data = "hR9WEkRH0We8jenKD2x7hQ==";
		
		String longdata = data;
		
		for(int i = 0; i < 1000; i++) {
			longdata += data;
		}
		
		String secret = "9#WcTaken=d3P=?h!G@=dpLY_F_fVTt=yn=ACs_4-P7yS+H&MeJ^B&yWneCy_==kbNggeTBtc9#6MHD2cH$5Y=aPcX!ZmH##%fQTmttn^BSD!Z$!NNA&NXLbPz3DqEab";
		
		assertEquals("user", AesEncryption.decrypt(data, secret ));
	}
}
