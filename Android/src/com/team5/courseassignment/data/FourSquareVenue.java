package com.team5.courseassignment.data;

import com.google.android.gms.maps.model.LatLng;

public class FourSquareVenue {
	private String name;
	private String id;
	private int distance;
	private LatLng location;
	private String homepage;
	private String phoneNumber;

	
	
	/**
	 * Constructor of FoursquareVenue
	 * @param name
	 * @param id foursquare id of venue
	 * @param distance Distance to the venue
	 * @param location LatLng of the venue
	 * @param homepage
	 * @param phoneNumer
	 */
	public FourSquareVenue(String name, String id, int distance, LatLng location, String homepage, String phoneNumer) {

		this.name = name;
		this.id = id;
		this.distance = distance;
		this.location = location;
		this.homepage = homepage;
		this.phoneNumber = phoneNumer;
	}

	/**
	 * Getter method for name of the venue/location
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for name of the venue/location.
	 * 
	 * @param name
	 *            - in case if user wants to change venue/location name.
	 */
	public void setFirstName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for retrieving distance to the venue/location.
	 * 
	 * @return Integer distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * Setter method for distance.
	 * 
	 * @param distance
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
	 * Overriden toString method to get all foursquare venues data as a chunk of
	 * String. Especially name and distance in meters.
	 */
	@Override
	public String toString() {
		return name + ", " + distance + " m";
	}

	public String getId() {
		return id;
	}

	public LatLng getLocation() {
		return location;
	}

	public String getHomepage() {
		return homepage;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	
}
