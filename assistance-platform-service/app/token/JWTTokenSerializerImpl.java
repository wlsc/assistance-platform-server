package token;

import play.Logger;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;


public class JWTTokenSerializerImpl implements TokenSerializer {
	private JWSSigner signer;
	
	private final String secret;
	
	public JWTTokenSerializerImpl(String secret) {
		this.secret = secret;
	}
	
	@Override
	public String sign(String payload) {
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
		Payload payloadObj = new Payload(payload);
		JWSObject jwsObject = new JWSObject(header, payloadObj);
		
		try {
			if(signer == null) {
				this.signer = new MACSigner(secret);
			}
			
			jwsObject.sign(signer);
			return jwsObject.serialize();
		} catch (JOSEException e) {
			Logger.error("TokenSerializerImpl", e);
		}
		
		return "";
	}
}
