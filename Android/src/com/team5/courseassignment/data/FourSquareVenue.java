package com.team5.courseassignment.data;

public class FourSquareVenue {
	public String name;
	public String id;
	public int distance;

	/**
	 * Constructor method of foursquare venue. Info as follows
	 * 
	 * @param name
	 *            - The name of the venue/location.
	 * @param id
	 *            - Given id of the venue/location.
	 * @param distance
	 *            - Distance to the venue/location.
	 */
	public FourSquareVenue(String name, String id, int distance) {

		this.name = name;
		this.id = id;
		this.distance = distance;
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
}
