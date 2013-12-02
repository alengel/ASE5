package com.team5.courseassignment.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team5.courseassignment.data.UserFollowers;

public class UserFollowersParser
{

	/**
	 * This class parses user data of the follower as a JSONArray
	 * 
	 * @param results
	 * @return reviewer_profile_venue - array list of objects to return, which
	 *         is profile_image, first_name and last_name. If the response from
	 *         the server is true-this parser will parse all data from
	 *         followers: [ array ] to ArrayList<ProfileInfo>
	 */
	public List<UserFollowers> parseJSON(JSONObject results)
	{
		List<UserFollowers> reviewer_profile_venue = new ArrayList<UserFollowers>();

		if (results == null)
			return reviewer_profile_venue;

		try
		{
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
		} 
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reviewer_profile_venue;
	}
}
