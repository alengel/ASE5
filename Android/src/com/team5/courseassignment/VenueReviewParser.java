package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VenueReviewParser {
	public List<VenueReview> parseJSON(JSONObject results) throws JSONException {
		List<VenueReview> reviews = new ArrayList<VenueReview>();
		
		JSONArray data = results.getJSONArray("data");
		
		for (int i = 0, size = data.length(); i < size; i++)
	    {
			JSONObject item = data.getJSONObject(i);
			String profile_Image = item.getString("profile_image");
			String firstName = item.getString("first_name");
			String lastName = item.getString("last_name");
			String rating = item.getString("rating");
			String review = item.getString("review");
			String votes = item.getString("total_vote");
			
			VenueReview venueReview = new VenueReview(profile_Image, firstName, lastName, rating, review,votes);
			reviews.add(venueReview);	
	    }
		return reviews;
	}
}
