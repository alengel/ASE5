package com.team5.courseassignment.test;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.team5.courseassignment.VenueReviewParser;
import com.team5.courseassignment.VenueReview;

import android.test.AndroidTestCase;



public class VenueReviewParserTest extends AndroidTestCase {

	@Test
	public void test_parseJSONWithNullJSONObject() {
		
		// Test with null JSON object.
		final List<VenueReview> venues = new VenueReviewParser().parseJSON(null);
		assertTrue(venues.isEmpty());
	}

	@Test
	public void test_parseJSONWithEmptyJSON() {

		JSONObject json = createJSONObject("{}");

		// Test with empty JSON.
		final List<VenueReview> venues = new VenueReviewParser().parseJSON(json);
		assertTrue(venues.isEmpty());
	}
	
	@Test
	public void test_parseJSONWithMalformatedJSON() {
		
		JSONObject json = createJSONObject("{response : {}}");
		
		// Test with malformated JSON.
		final List<VenueReview> venues = new VenueReviewParser().parseJSON(json);
		assertTrue(venues.isEmpty());
	}
	
	@Test
	public void test_parseJSONWithOneReview() {
		
		String text = "{\r\n" + 
				"   \"success\":\"true\",\r\n" + 
				"   \"data\":[\r\n" + 
				"      {\r\n" + 
				"         \"first_name\":\"Alena\",\r\n" + 
				"         \"last_name\":\"Ruprecht\",\r\n" + 
				"         \"email\":\"alenaruprecht@gmail.com\",\r\n" + 
				"         \"rating\":\"4.0\",\r\n" + 
				"         \"review\":\"great place, long queues\"\r\n" + 
				"      },\r\n" + 
				"   ]\r\n" + 
				"}";
		
		JSONObject json = createJSONObject(text);		

		// Test with one review.
		final List<VenueReview> reviews = new VenueReviewParser().parseJSON(json);
		assertEquals(1, reviews.size());
		assertEquals(reviews.get(0).firstName, "Alena");
		assertEquals(reviews.get(0).lastName, "Ruprecht");
		assertEquals(reviews.get(0).rating, "4.0");
		assertEquals(reviews.get(0).review, "great place, long queues");
	}
	
	@Test
	public void test_parseJSONWithTwoReviews() {
		
		String text = "{\r\n" + 
				"   \"success\":\"true\",\r\n" + 
				"   \"data\":[\r\n" + 
				"      {\r\n" + 
				"         \"first_name\":\"Alena\",\r\n" + 
				"         \"last_name\":\"Ruprecht\",\r\n" + 
				"         \"email\":\"alenaruprecht@gmail.com\",\r\n" + 
				"         \"rating\":\"4.0\",\r\n" + 
				"         \"review\":\"great place, long queues\"\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"first_name\":\"Michael\",\r\n" + 
				"         \"last_name\":\"Jackson\",\r\n" + 
				"         \"email\":\"alenaruprecht@gmail.com\",\r\n" + 
				"         \"rating\":\"3.0\",\r\n" + 
				"         \"review\":\"always busy\"\r\n" + 
				"      }\r\n" + 
				"   ]\r\n" + 
				"}";
		
		JSONObject json = createJSONObject(text);		

		// Test with two reviews.
		final List<VenueReview> reviews = new VenueReviewParser().parseJSON(json);
		assertEquals(2, reviews.size());
		assertEquals(reviews.get(0).firstName, "Alena");
		assertEquals(reviews.get(0).lastName, "Ruprecht");
		assertEquals(reviews.get(0).rating, "4.0");
		assertEquals(reviews.get(0).review, "great place, long queues");
		
		assertEquals(reviews.get(1).firstName, "Michael");
		assertEquals(reviews.get(1).lastName, "Jackson");
		assertEquals(reviews.get(1).rating, "3.0");
		assertEquals(reviews.get(1).review, "always busy");
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