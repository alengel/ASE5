package com.team5.courseassignment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FollowerProfileInfoParser
{

	public FollowerProfileInfo parseJSON(JSONObject results)
	{
		FollowerProfileInfo reviewer_profile = null;

		if (results == null)
			return reviewer_profile;

		JSONArray profile;
		try
		{
			profile = results.getJSONArray("profile");

			JSONObject user = profile.getJSONObject(0);
			String profileImage = user.getString("profile_image");
			String firstName = user.getString("first_name");
			String lastName = user.getString("last_name");

			reviewer_profile = new FollowerProfileInfo(profileImage, firstName, lastName);
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reviewer_profile;
	}
}
