package com.team5.courseassignment.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.team5.courseassignment.data.ProfileInfo;

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
	public ProfileInfo parseJSON(JSONObject results) {
		ProfileInfo profile = null;

		if (results == null)
			return profile;
		
		
		try {
			

		JSONObject user = results.getJSONObject("data");
		 
		String profileImage = user.getString("profile_image");
		String name = user.getString("first_name");
		String lastName = user.getString("last_name");
		String email = user.getString("email");

		profile = new ProfileInfo(profileImage, name,
				lastName, email);
		
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return profile;
	}
}
