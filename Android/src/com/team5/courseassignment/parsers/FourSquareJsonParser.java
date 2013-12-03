package com.team5.courseassignment.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.team5.courseassignment.data.FourSquareVenue;

public class FourSquareJsonParser {

	/**
	 * This class parses list of venues "groups" from the foursquare-api as a
	 * JSONArray
	 * 
	 * @param results
	 * @return venues - array list of objects to return, which is venue, name,
	 *         id, location and distance. If the response from the server is
	 *         true-this parser will parse all data from groups: [ array ]
	 */
	public List<FourSquareVenue> parseJSON(JSONObject results) {

		List<FourSquareVenue> venues = new ArrayList<FourSquareVenue>();

		if (results == null)
			return venues;

		try {
			JSONObject response = results.getJSONObject("response");

			JSONArray groups = response.getJSONArray("groups");
			JSONArray items = groups.getJSONObject(0).getJSONArray("items");

			for (int i = 0, size = items.length(); i < size; i++) {
				
				JSONObject item = items.getJSONObject(i);
				
				//venue upper level
				JSONObject venue = item.getJSONObject("venue");
				String name = venue.getString("name");
				String id = venue.getString("id");
				
				//location
				JSONObject location = venue.getJSONObject("location");
				Integer distance = location.getInt("distance");
				int lat = location.getInt("lat");
				int lng = location.getInt("lng");
				LatLng latLng = new LatLng(lat, lng);
				
				//contact
				JSONObject contact = venue.getJSONObject("contact");
				String phonenumber;
				if(contact != null) {
					phonenumber = contact.getString("formattedPhone");
					if(phonenumber == null || phonenumber.equals("")) {
						phonenumber = contact.getString(" phone");
					}
				} else {
					phonenumber = "";
				}
				
				//homepage
				String homepage = venue.getString("url");
				

				FourSquareVenue fourSquareVenue = new FourSquareVenue(name, id,
						distance, latLng, homepage, phonenumber);
				venues.add(fourSquareVenue);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}

		return venues;
	}
}
