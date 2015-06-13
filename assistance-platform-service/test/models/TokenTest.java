package models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TokenTest {
	@Test
	public void tokenSerializationDeserializationTest() {
		Long id = 9775L;
		Token t = Token.buildToken(id, 24);
		Long timestamp = t.expirationTimestamp;
		String token = t.token;

		Token unpacked = Token.unpackToken(token);
		assertEquals(id.toString(), unpacked.associatedId);
		assertEquals(timestamp, unpacked.expirationTimestamp);
		assertEquals(token, unpacked.token);
		assertEquals(true, unpacked.stillValid());
	}
	
	@Test
	public void invalidTokenSerialization() {
		Long id = 9775L;
		Token t = Token.buildToken(id, -1);
		String token = t.token;

		Token unpacked = Token.unpackToken(token);
		assertEquals(false, unpacked.stillValid());
	}
}