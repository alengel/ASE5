package com.team5.courseassignment.test;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.team5.courseassignment.data.FourSquareVenue;
import com.team5.courseassignment.parsers.FourSquareJsonParser;

import android.test.AndroidTestCase;



public class FourSquareJsonParserTest extends AndroidTestCase {

	@Test
	public void test_parseJSONWithNullJSONObject() {
		
		// Test with null JSON object.
		final List<FourSquareVenue> venues = new FourSquareJsonParser().parseJSON(null);
		assertTrue(venues.isEmpty());
	}
	
	@Test
	public void test_parseJSONWithEmptyJSON() {
		
		JSONObject json = createJSONObject("{}");
		
		// Test with empty JSON.
		final List<FourSquareVenue> venues = new FourSquareJsonParser().parseJSON(json);
		assertTrue(venues.isEmpty());
	}
	
	@Test
	public void test_parseJSONWithMalformatedJSON() {
		
		JSONObject json = createJSONObject("{response : {}}");
		
		// Test with malformated JSON.
		final List<FourSquareVenue> venues = new FourSquareJsonParser().parseJSON(json);
		assertTrue(venues.isEmpty());
	}
	
	@Test
	public void test_parseJSONWithOneVenue() {
		
		JSONObject json = createJSONObject("{\"response\":{"+
											      "\"groups\":[{"+
											         "\"items\":[{"+											                  
											            "\"venue\":{" + 
											              " \"id\":\"430d0a00f964a5203e271fe3\","+
											              " \"name\":\"Brooklyn Bridge Park\","+
											              " \"location\":{" + 
												              " \"distance\":3,"+
												              " \"lat\":4,"+
												              " \"lng\":3"+
												            "},"+
											              " \"contact\":{" + 
											                  " \"formattedPhone\":\"formattedPhone\""+
											            "}"+
											            "}"+
											         "}]"+
											      "}]"+
											   "}"+
											"}");
		
		// Test with one venue.
		final List<FourSquareVenue> venues = new FourSquareJsonParser().parseJSON(json);
		assertEquals(1, venues.size());
		assertEquals(venues.get(0).getName(), "Brooklyn Bridge Park");
		assertEquals(venues.get(0).getId(), "430d0a00f964a5203e271fe3");
		assertEquals(venues.get(0).getDistance(), 3);
		assertEquals(venues.get(0).getPhoneNumber(), "formattedPhone");
		assertEquals(venues.get(0).getHomepage(), "");
	}
	
	@Test
	public void test_parseJSONWithTwoVenues() {
		
		JSONObject json = createJSONObject("{\"response\":{"+
											      "\"groups\":[{"+
											         "\"items\":[{"+											                  
											            "\"venue\":{" + 
											              " \"id\":\"430d0a00f964a5203e271fe3\","+
											              " \"name\":\"Brooklyn Bridge Park\","+
											              " \"location\":{" + 
												              " \"distance\":3,"+
												              " \"lat\":4,"+
												              " \"lng\":3"+
												            "},"+
											              " \"contact\":{" + 
											                  " \"formattedPhone\":\"formattedPhone\""+
											              "}"+
											            "}},"+
											            "{"+											                  
											            "\"venue\":{" + 
											              " \"id\":\"42377700f964a52024201fe3\","+
											              " \"name\":\"Brooklyn Heights Promenade\","+
											              " \"location\":{" + 
												              " \"distance\":5,"+
												              " \"lat\":4,"+
												              " \"lng\":3"+
												            "},"+
											              " \"contact\":{" + 
											                  " \"formattedPhone\":\"formattedPhone2\""+
											              "},"+
											              " \"url\":\"homepage\""+
											            "}"+
											         "}]"+
											      "}]"+
											   "}"+
											"}");
		
		// Test with one venues.
		final List<FourSquareVenue> venues = new FourSquareJsonParser().parseJSON(json);
		
		assertEquals(2, venues.size());
		
		assertEquals(venues.get(0).getName(), "Brooklyn Bridge Park");
		assertEquals(venues.get(0).getId(), "430d0a00f964a5203e271fe3");
		assertEquals(venues.get(0).getDistance(), 3);
		assertEquals(venues.get(0).getPhoneNumber(), "formattedPhone");
		assertEquals(venues.get(0).getHomepage(), "");
		
		assertEquals(venues.get(1).getName(), "Brooklyn Heights Promenade");
		assertEquals(venues.get(1).getId(), "42377700f964a52024201fe3");
		assertEquals(venues.get(1).getDistance(), 5);
		assertEquals(venues.get(1).getPhoneNumber(), "formattedPhone2");
		assertEquals(venues.get(1).getHomepage(), "homepage");
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