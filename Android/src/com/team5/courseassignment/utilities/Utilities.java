package com.team5.courseassignment.utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * helper class that contains methods which are used in many activities
 * 
 */
public class Utilities {

	/**
	 * encrypts a String with SHA1 algorithm, returns it as a String
	 * 
	 * @param input
	 *            String that need to be encrypted
	 * @return the encrypted String
	 */
	public static String encryptString(String input) {
		String sha1 = "";

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(input.getBytes("UTF-8"));
			sha1 = convertByteArrayToString(crypt.digest());
		}

		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return sha1;
	}
	
	/**
	 * This method converts byte array to string representation.
	 * 
	 * @param hash
	 *            - byte array.
	 * @return result
	 */
	public static String convertByteArrayToString(final byte[] hash) {

		Formatter formatter = new Formatter();

		for (byte b : hash) {
			formatter.format("%02x", b);
		}

		String result = formatter.toString();
		formatter.close();

		return result;
	}
}
