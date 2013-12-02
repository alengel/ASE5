package com.team5.courseassignment.test;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.team5.courseassignment.data.ProfileInfo;
import com.team5.courseassignment.parsers.ProfileInfoParser;



public class ProfileInfoParserTest extends AndroidTestCase
{

	@Test
	public void test_parseJSONWithNullJSONObject() throws JSONException
	{

		// Test with null JSON object.
		final ProfileInfo profile = (ProfileInfo) new ProfileInfoParser().parseJSON(null);
		assertNull(profile);
	}

	@Test
	public void test_parseJSONWithEmptyJSON() throws JSONException
	{

		JSONObject json = createJSONObject("{}");

		// Test with empty JSON.
		final ProfileInfo profile = (ProfileInfo) new ProfileInfoParser().parseJSON(json);
		assertNull(profile);
	}

	@Test
	public void test_parseJSONWithMalformatedJSON() throws JSONException
	{

		JSONObject json = createJSONObject("{response : {}}");

		// Test with malformated JSON.
		final ProfileInfo profile = (ProfileInfo) new ProfileInfoParser().parseJSON(json);
		assertNull(profile);
	}
	
	@Test
	public void test_parseJSON() throws JSONException 
	{		
		String text = "{\r\n" + 
				"   \"success\":\"true\",\r\n" + 
				"   \"data\":[\r\n" + 
				"      {\r\n" + 
				"         \"profile_image\":\"profileImage\",\r\n" + 
				"         \"first_name\":\"Alena\",\r\n" + 
				"         \"last_name\":\"Ruprecht\"\r\n" + 
				//"         \"email\":\"somemail@mail.com\"\r\n" + 
				"      },\r\n" + 
				"   ]\r\n" + 
				"}";
		
		JSONObject json = createJSONObject(text);		

		final ProfileInfo profile = (ProfileInfo) new ProfileInfoParser().parseJSON(json);
		assertNotNull(profile);		
	
		assertEquals(profile.profileImage, "profileImage");
		assertEquals(profile.firstName, "Alena");
		assertEquals(profile.lastName, "Ruprecht");
		//assertEquals(profile.email, "somemail@mail.com");
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