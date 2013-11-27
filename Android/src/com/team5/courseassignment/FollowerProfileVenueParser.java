package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FollowerProfileVenueParser {

	public List<FollowerProfileVenue> parseJSON(JSONObject results)
			throws JSONException {
		List<FollowerProfileVenue> reviewer_profile_venue = new ArrayList<FollowerProfileVenue>();

		JSONArray details = results.getJSONArray("details");
		for (int i = 0, size = details.length(); i < size; i++) {
			JSONObject item = details.getJSONObject(i);
			String locationImage = item.getString("location_image");
			String locationName = item.getString("location_name");
			String rating = item.getString("rating");
			String review = item.getString("review");

			FollowerProfileVenue followerProfileVenue = new FollowerProfileVenue(
					locationImage, locationName, rating, review);
			reviewer_profile_venue.add(followerProfileVenue);
		}
		return reviewer_profile_venue;
	}
}
