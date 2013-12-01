package com.team5.courseassignment.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team5.courseassignment.FollowerProfileVenue;

public class FollowerProfileVenueParser {

	/**
	 * This class parses list of venues "details" visited by the follower as a
	 * JSONArray
	 * 
	 * @param results
	 * @return reviewer_profile_venue - array list of objects to return, which
	 *         is location_image, location_name, rating and review. If the
	 *         response from the server is true-this parser will parse all data
	 *         from details: [ array ]
	 */

	public List<FollowerProfileVenue> parseJSON(JSONObject results) {
		List<FollowerProfileVenue> reviewer_profile_venue = new ArrayList<FollowerProfileVenue>();

		if (results == null)
			return reviewer_profile_venue;

		JSONArray details;
		try {
			details = results.getJSONArray("details");

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
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return reviewer_profile_venue;
	}
}
