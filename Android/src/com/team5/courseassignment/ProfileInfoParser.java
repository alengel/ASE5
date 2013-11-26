package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileInfoParser {

	public List<ProfileInfo> parseJSON(JSONObject results) throws JSONException {
		List<ProfileInfo> reviewer_profile = new ArrayList<ProfileInfo>();
		
		JSONArray profile = results.getJSONArray("profile");
		JSONObject user = profile.getJSONObject(0);
		String profileImage = user.getString("profile_image");
		String name = user.getString("first_name");
		String lastName = user.getString("last_name");
		String email = user.getString("email");
		String editName = user.getString("editName");
		String editLastName = user.getString("editLastName");
		
		ProfileInfo followerProfileInfo = new ProfileInfo(profileImage, name, lastName,email,editName, editLastName);
		reviewer_profile.add(followerProfileInfo);	
	    
		return reviewer_profile;
	}
}

