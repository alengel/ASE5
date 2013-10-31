package com.team5.courseassignment;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import android.util.Log;

/**
 * helper class that contains methods which are used in many activities
 * @author Pascal
 *
 */
public class Utilies {
	
	/**
	 * encrypts a String with SHA1 algorithm, returns it as a String
	 * @param input String that need to be encrypted
	 * @return the encrypted String
	 */
	public static String encryptString(String input)
	{
	    String sha1 = "";
	    
	    try {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(input.getBytes("UTF-8"));
	        sha1 = convertByteArrayToString(crypt.digest());
	    }
	    
	    catch(NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    
	    catch(UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    
	    return sha1;
	}

	private static String convertByteArrayToString(final byte[] hash) {
	    
		Formatter formatter = new Formatter();
		
	    for (byte b : hash) {
	        formatter.format("%02x", b);
	    }
	    
	    String result = formatter.toString();
	    formatter.close();
	    
	    return result;
	}
	
	
	/**
	 * determines the distance between target and User in Meters
	 * 
	 * @param targetLatitude
	 * @param targetLongitude
	 * @param userLatitude
	 * @param userLongitude
	 * @return the distance between Target and User (in M)
	 */
	public static double calculateDistanceInM(double targetLatitude,
			double targetLongitude, double userLatitude, double userLongitude) {

		final double earthRadius = 6372.8; // In kilometers
		double distance;

		// calculate the distance between the car and the venue using the haversin formula
		double deltaLatitude = Math.toRadians(Math.abs(userLatitude - targetLatitude));
		double deltaLongitude = Math.toRadians(Math.abs(userLongitude - targetLongitude));

		targetLatitude = Math.toRadians(targetLatitude);
		userLatitude = Math.toRadians(userLatitude);

		double sinDeltaLatitude2 = Math.sin(deltaLatitude / 2); // for better performance
		double sinDeltaLongitude2 = Math.sin(deltaLongitude / 2); // for better performance
		distance = sinDeltaLatitude2 * sinDeltaLatitude2 + sinDeltaLongitude2 * sinDeltaLongitude2 * Math.cos(targetLatitude) * Math.cos(userLatitude);
		distance = 2 * Math.asin(Math.sqrt(distance));
		distance = earthRadius * distance;

		Log.d("b_logic", "HelperMethod.calculateDistanceInKm - result: " + distance);

		return (distance * 1000);
	}

	
	
}
