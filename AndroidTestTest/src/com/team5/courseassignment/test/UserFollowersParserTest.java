package com.team5.courseassignment.test;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;


import com.team5.courseassignment.data.UserFollowers;
import com.team5.courseassignment.parsers.UserFollowersParser;

import android.test.AndroidTestCase;



public class UserFollowersParserTest extends AndroidTestCase {

	@Test
	public void test_parseJSONWithNullJSONObject() throws JSONException {
		
		// Test with null JSON object.
		final List<UserFollowers> followers = new UserFollowersParser().parseJSON(null);
		assertTrue(followers.isEmpty());
	}

	@Test
	public void test_parseJSONWithEmptyJSON() throws JSONException {

		JSONObject json = createJSONObject("{}");

		// Test with empty JSON.
		final List<UserFollowers> followers = new UserFollowersParser().parseJSON(json);
		assertTrue(followers.isEmpty());
	}
	
	@Test
	public void test_parseJSONWithMalformatedJSON() throws JSONException {
		
		JSONObject json = createJSONObject("{response : {}}");
		
		// Test with malformated JSON.
		final List<UserFollowers> followers = new UserFollowersParser().parseJSON(json);
		assertTrue(followers.isEmpty());
	}
	
	@Test
	public void test_parseJSONWithOneReview() throws JSONException {
		
		String text = "{\r\n" + 
				"   \"success\":\"true\",\r\n" + 
				"   \"followers\":[\r\n" + 
				"      {\r\n" + 
				"         \"profile_image\":\"profileImage\",\r\n" + 
				"         \"first_name\":\"Alena\",\r\n" + 
				"         \"last_name\":\"Ruprecht\",\r\n" + 
				"      },\r\n" + 
				"   ]\r\n" + 
				"}";
		
		JSONObject json = createJSONObject(text);		

		// Test with one review.
		final List<UserFollowers> followers = new UserFollowersParser().parseJSON(json);
		assertEquals(1, followers.size());
		assertEquals(followers.get(0).profileImage, "profileImage");
		assertEquals(followers.get(0).firstName, "Alena");
		assertEquals(followers.get(0).lastName, "Ruprecht");
		
	}
	
	@Test
	public void test_parseJSONWithTwoReviews() throws JSONException {
		
		String text = "{\r\n" + 
				"   \"success\":\"true\",\r\n" + 
				"   \"followers\":[\r\n" + 
				"      {\r\n" + 
				"         \"profile_image\":\"profileImage\",\r\n" + 
				"         \"first_name\":\"Alena\",\r\n" + 
				"         \"last_name\":\"Ruprecht\",\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"profile_image\":\"profileImage2\",\r\n" + 
				"         \"first_name\":\"Michael\",\r\n" + 
				"         \"last_name\":\"Jackson\",\r\n" + 
				"      }\r\n" + 
				"   ]\r\n" + 
				"}";
		
		JSONObject json = createJSONObject(text);		

		// Test with two reviews.
		final List<UserFollowers> followers = new UserFollowersParser().parseJSON(json);
		assertEquals(2, followers.size());
		assertEquals(followers.get(0).profileImage, "profileImage");
		assertEquals(followers.get(0).firstName, "Alena");
		assertEquals(followers.get(0).lastName, "Ruprecht");
		
		
		assertEquals(followers.get(1).profileImage, "profileImage2");
		assertEquals(followers.get(1).firstName, "Michael");
		assertEquals(followers.get(1).lastName, "Jackson");
		
	}
	
	// Create a JSONObject from a string.
	private JSONObject createJSONObject(String string)
	{
		JSONObject json = null;
		try {
			json = new JSONObject(string);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			super.fail();
		}
		
		return json;
	}
}