package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileInfoParser {

	/**
	 * This class parses profile data of the follower as a JSONArray
	 * 
	 * @param results
	 * @return profile - array list of objects to return, which is
	 *         profile_image, first_name, last_name and email. If the response from the
	 *         server is true-this parser will parse all data from 
	 *         data: [ array ] to ArrayList<ProfileInfo>
	 */
	public List<ProfileInfo> parseJSON(JSONObject results) throws JSONException {
		List<ProfileInfo> profile = new ArrayList<ProfileInfo>();

		JSONObject data = results.getJSONObject("data");
		// JSONObject user = data.getJSONObject(0);
		String profileImage = data.getString("profile_image");
		String name = data.getString("first_name");
		String lastName = data.getString("last_name");
		String email = data.getString("email");

		ProfileInfo followerProfileInfo = new ProfileInfo(profileImage, name,
				lastName, email);
		profile.add(followerProfileInfo);

		return profile;
	}
}
