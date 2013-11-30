package com.team5.courseassignment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FollowerProfileInfoParser {

	/**
	 * This class parses profile data of the follower as a JSONArray
	 * 
	 * @param results
	 * @return reviewer_profile - array list of objects to return, which is
	 *         profile_image, first_name and last_name. If the response from the
	 *         server is true-this parser will parse all data from profile: [
	 *         array ]
	 */
	public FollowerProfileInfo parseJSON(JSONObject results) {
		FollowerProfileInfo reviewer_profile = null;

		if (results == null)
			return reviewer_profile;

		JSONArray profile;
		try {
			profile = results.getJSONArray("profile");

			JSONObject user = profile.getJSONObject(0);
			String profileImage = user.getString("profile_image");
			String firstName = user.getString("first_name");
			String lastName = user.getString("last_name");

			reviewer_profile = new FollowerProfileInfo(profileImage, firstName,
					lastName);
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return reviewer_profile;
	}
}
