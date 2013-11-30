package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VenueReviewParser {
	/**
	 * This class parses list of venue reviews of particular venue/location as a
	 * JSONArray
	 * 
	 * @param results
	 * @return reviews - array list of objects to return, which is
	 *         profile_image, first_name, last_name, id, rating, review,
	 *         review_id and total_vote. If the response from the server is
	 *         true-this parser will parse all data from data: [ array ] to
	 *         ArrayList<VenueReview>
	 */
	public List<VenueReview> parseJSON(JSONObject results) {
		List<VenueReview> reviews = new ArrayList<VenueReview>();

		if (results == null)
			return reviews;

		JSONArray data;
		try {
			data = results.getJSONArray("data");

			for (int i = 0, size = data.length(); i < size; i++) {
				JSONObject item = data.getJSONObject(i);
				String profileImage = item.getString("profile_image");
				String firstName = item.getString("first_name");
				String lastName = item.getString("last_name");
				String reviewerId = item.getString("id");
				String rating = item.getString("rating");
				String review = item.getString("review");
				String reviewId = item.getString("review_id");
				String votes = item.getString("total_vote");

				VenueReview venueReview = new VenueReview(profileImage,
						firstName, lastName, reviewerId, rating, review,
						reviewId, votes);
				reviews.add(venueReview);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reviews;
	}
}
