package models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TokenTest {
	@Test
	public void tokenSerializationDeserializationTest() {
		Long id = 9775L;
		Token t = Token.buildToken(id);
		Long timestamp = t.generationTimestamp;
		String token = t.token;

		Token unpacked = Token.unpackToken(token);
		assertEquals(id.toString(), unpacked.associatedId);
		assertEquals(timestamp, unpacked.generationTimestamp);
		assertEquals(token, unpacked.token);
	}
}