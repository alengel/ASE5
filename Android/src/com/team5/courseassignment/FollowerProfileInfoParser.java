package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FollowerProfileInfoParser {

	public List<FollowerProfileInfo> parseJSON(JSONObject results)
			throws JSONException {
		List<FollowerProfileInfo> reviewer_profile = new ArrayList<FollowerProfileInfo>();

		JSONArray profile = results.getJSONArray("profile");
		JSONObject user = profile.getJSONObject(0);
		String profileImage = user.getString("profile_image");
		String firstName = user.getString("first_name");
		String lastName = user.getString("last_name");

		FollowerProfileInfo followerProfileInfo = new FollowerProfileInfo(
				profileImage, firstName, lastName);
		reviewer_profile.add(followerProfileInfo);

		return reviewer_profile;
	}
}
