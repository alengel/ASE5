package com.team5.courseassignment.test;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.team5.courseassignment.data.FollowerProfileInfo;
import com.team5.courseassignment.parsers.FollowerProfileInfoParser;



public class FollowerProfileInfoParserTest extends AndroidTestCase
{

	@Test
	public void test_parseJSONWithNullJSONObject()
	{

		// Test with null JSON object.
		final FollowerProfileInfo profile = new FollowerProfileInfoParser().parseJSON(null);
		assertNull(profile);
	}

	@Test
	public void test_parseJSONWithEmptyJSON()
	{

		JSONObject json = createJSONObject("{}");

		// Test with empty JSON.
		final FollowerProfileInfo profile = new FollowerProfileInfoParser().parseJSON(json);
		assertNull(profile);
	}

	@Test
	public void test_parseJSONWithMalformatedJSON()
	{

		JSONObject json = createJSONObject("{response : {}}");

		// Test with malformated JSON.
		final FollowerProfileInfo profile = new FollowerProfileInfoParser().parseJSON(json);
		assertNull(profile);
	}
	
	@Test
	public void test_parseJSON() 
	{		
		String text = "{\r\n" + 
				"   \"success\":\"true\",\r\n" + 
				"   \"profile\":[\r\n" + 
				"      {\r\n" + 
				"         \"profile_image\":\"profileImage\",\r\n" + 
				"         \"first_name\":\"Alena\",\r\n" + 
				"         \"last_name\":\"Ruprecht\"\r\n" + 
				"      },\r\n" + 
				"   ]\r\n" + 
				"}";
		
		JSONObject json = createJSONObject(text);		

		final FollowerProfileInfo profile = new FollowerProfileInfoParser().parseJSON(json);
		assertNotNull(profile);		
	
		assertEquals(profile.profileImage, "profileImage");
		assertEquals(profile.firstName, "Alena");
		assertEquals(profile.lastName, "Ruprecht");
	}
	
	// Create a JSONObject from a string.
	private JSONObject createJSONObject(String string)
	{
		JSONObject json = null;
		try
		{
			json = new JSONObject(string);
		} 
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			super.fail();
		}

		return json;
	}
}