package token;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;

public class TokenDeserializerImpl implements TokenDeserializer {
	private JWSVerifier verifier;
	
	private final String secret;
	
	public TokenDeserializerImpl(String secret) {
		this.secret = secret;
	}
	
	@Override
	public String deserialize(String token) {
		if(verifier == null) {
			this.verifier = new MACVerifier(secret);
		}
		
		try {
			JWSObject jwsObject = JWSObject.parse(token);
			jwsObject.verify(verifier);
			return jwsObject.getPayload().toString();
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

}
