package com.team5.courseassignment.test;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.team5.courseassignment.FollowerProfileVenue;
import com.team5.courseassignment.FollowerProfileVenueParser;



public class FollowerProfileVenueParserTest extends AndroidTestCase
{

	@Test
	public void test_parseJSONWithNullJSONObject()
	{

		// Test with null JSON object.
		final List<FollowerProfileVenue> reviewer_profile_venue = new  FollowerProfileVenueParser().parseJSON(null);
		assertTrue(reviewer_profile_venue.isEmpty());
	}

	@Test
	public void test_parseJSONWithEmptyJSON()
	{

		JSONObject json = createJSONObject("{}");

		// Test with empty JSON.
		final List<FollowerProfileVenue> reviewer_profile_venue = new  FollowerProfileVenueParser().parseJSON(json);
		assertTrue(reviewer_profile_venue.isEmpty());
	}

	@Test
	public void test_parseJSONWithMalformatedJSON()
	{

		JSONObject json = createJSONObject("{response : {}}");

		// Test with malformated JSON.
		final List<FollowerProfileVenue> reviewer_profile_venue = new  FollowerProfileVenueParser().parseJSON(json);
		assertTrue(reviewer_profile_venue.isEmpty());
	}
	
	@Test
	public void test_parseJSONWithOneVenue() 
	{		
		String text = "{\r\n" + 
				"   \"success\":\"true\",\r\n" + 
				"   \"details\":[\r\n" + 
				"      {\r\n" + 
				"         \"location_image\":\"locationImage\",\r\n" + 
				"         \"location_name\":\"locationName\",\r\n" + 
				"         \"rating\":\"rating\",\r\n" + 
				"         \"review\":\"review\"\r\n" + 
				"      },\r\n" + 
				"   ]\r\n" + 
				"}";
		
		JSONObject json = createJSONObject(text);		

		// Test with one profile.
		final List<FollowerProfileVenue> reviewer_profile_venue = new FollowerProfileVenueParser().parseJSON(json);
		assertEquals(1, reviewer_profile_venue.size());
		assertEquals(reviewer_profile_venue.get(0).locationImage, "locationImage");
		assertEquals(reviewer_profile_venue.get(0).locationName, "locationName");
		assertEquals(reviewer_profile_venue.get(0).rating, "rating");
		assertEquals(reviewer_profile_venue.get(0).review, "review");
	}
	
	@Test
	public void test_parseJSONWithTwoVenues() 
	{		
		String text = "{\r\n" + 
				"   \"success\":\"true\",\r\n" + 
				"   \"details\":[\r\n" + 
				"      {\r\n" + 
				"         \"location_image\":\"locationImage\",\r\n" + 
				"         \"location_name\":\"locationName\",\r\n" + 
				"         \"rating\":\"rating\",\r\n" + 
				"         \"review\":\"review\"\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"location_image\":\"locationImage2\",\r\n" + 
				"         \"location_name\":\"locationName2\",\r\n" + 
				"         \"rating\":\"rating2\",\r\n" + 
				"         \"review\":\"review2\"\r\n" + 
				"      },\r\n" +
				"   ]\r\n" + 
				"}";
		
		JSONObject json = createJSONObject(text);		

		// Test with one profile.
		final List<FollowerProfileVenue> reviewer_profile_venue = new FollowerProfileVenueParser().parseJSON(json);
		assertEquals(2, reviewer_profile_venue.size());
		
		assertEquals(reviewer_profile_venue.get(0).locationImage, "locationImage");
		assertEquals(reviewer_profile_venue.get(0).locationName, "locationName");
		assertEquals(reviewer_profile_venue.get(0).rating, "rating");
		assertEquals(reviewer_profile_venue.get(0).review, "review");
		
		assertEquals(reviewer_profile_venue.get(1).locationImage, "locationImage2");
		assertEquals(reviewer_profile_venue.get(1).locationName, "locationName2");
		assertEquals(reviewer_profile_venue.get(1).rating, "rating2");
		assertEquals(reviewer_profile_venue.get(1).review, "review2");
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