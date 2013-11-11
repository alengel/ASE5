package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FourSquareJsonParser {
	public List<FourSquareVenue> parseJSON(JSONObject results) throws JSONException {
		List<FourSquareVenue> venues = new ArrayList<FourSquareVenue>();
		
		JSONObject response = results.getJSONObject("response");
		JSONArray groups = response.getJSONArray("groups");
		JSONArray items = groups.getJSONObject(0).getJSONArray("items");
		
		for (int i = 0, size = items.length(); i < size; i++)
	    {
			JSONObject item = items.getJSONObject(i);
			JSONObject venue = item.getJSONObject("venue");
			String name = venue.getString("name");
			String id = venue.getString("id");
			
			FourSquareVenue fourSquareVenue = new FourSquareVenue(name, id);
			venues.add(fourSquareVenue);	
	    }
		return venues;
	}
}
