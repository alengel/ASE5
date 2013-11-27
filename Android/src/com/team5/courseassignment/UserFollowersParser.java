package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserFollowersParser {

	public List<UserFollowers> parseJSON(JSONObject results) throws JSONException {
		List<UserFollowers> reviewer_profile_venue = new ArrayList<UserFollowers>();
	
		JSONArray followers = results.getJSONArray("followers");
		for (int i = 0, size = followers.length(); i < size; i++)
	    {
			JSONObject item = followers.getJSONObject(i);
			String profileImage = item.getString("profile_image");
			String firstName = item.getString("first_name");
			String lastName = item.getString("last_name");
			
			
			UserFollowers followerProfileVenue = new UserFollowers(profileImage, firstName, lastName);
			reviewer_profile_venue.add(followerProfileVenue);	
	    }
		return reviewer_profile_venue;
	}
}

