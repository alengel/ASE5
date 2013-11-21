package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OtherUsersReviewsParser {

	public List<OtherUsersReviews> parseJSON(JSONObject results) throws JSONException {
		List<OtherUsersReviews> reviews = new ArrayList<OtherUsersReviews>();
		
		JSONArray data = results.getJSONArray("data");
		
		for (int i = 0, size = data.length(); i < size; i++)
	    {
			JSONObject item = data.getJSONObject(i);
			String locationImage = item.getString("location_image");
			String locationName = item.getString("location_name");
			String rating = item.getString("rating");
			String review = item.getString("review");
			
			OtherUsersReviews otherUsersReviews = new OtherUsersReviews(locationImage, locationName, rating, review);
			reviews.add(otherUsersReviews);	
	    }
		return reviews;
	}
}

