package model;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class Authenticator {

	private static final String SECRET = "magic";
	private static long nonceCount = 0;
	
	public CustomerBean validate(String username, String hash, long nonce, String fullName) throws Exception {
		String stringToHash = username + SECRET + nonce;
		String hashed = DatatypeConverter
				.printHexBinary(MessageDigest.getInstance("SHA1").digest(stringToHash.getBytes()));
		if(hashed.equalsIgnoreCase(hash)) {
			return new CustomerBean(username, fullName);
		} else {
			throw new Exception("Invalid credentials");
		}
	}
	
	public long getNextSecret() {
		return nonceCount++;
	}
}
