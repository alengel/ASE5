package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileInfoParser {

	public List<ProfileInfo> parseJSON(JSONObject results) throws JSONException {
		List<ProfileInfo> user_profile = new ArrayList<ProfileInfo>();
		
		JSONArray data = results.getJSONArray("data");
		JSONObject user = data.getJSONObject(0);
		String profileImage = user.getString("profile_image");
		String name = user.getString("first_name");
		String lastName = user.getString("last_name");
		String email = user.getString("email");
		
		
		ProfileInfo followerProfileInfo = new ProfileInfo(profileImage, name, lastName,email);
		user_profile.add(followerProfileInfo);	
	    
		return user_profile;
	}
}

