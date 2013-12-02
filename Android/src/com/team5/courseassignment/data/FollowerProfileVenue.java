package com.team5.courseassignment.data;

public class FollowerProfileVenue {
	public String locationImage;
	public String locationName;
	public String rating;
	public String review;

	/**
	 * Constructor method of profile followers list of venues. Info as follows
	 * 
	 * @param locationImage
	 *            - Picture of the location uploaded to database. BASE64
	 * @param locationName
	 *            - The name of the location.
	 * @param rating
	 *            - Given rating for this location (Out of 5.0)
	 * @param review
	 *            - Left review from the user, to this particular location.
	 */
	public FollowerProfileVenue(String locationImage, String locationName,
			String rating, String review) {

		this.locationImage = locationImage;
		this.locationName = locationName;
		this.rating = rating;
		this.review = review;
	}

	/**
	 * Getter method for location picture
	 * 
	 * @return locationImage
	 */
	public String getLocationImage() {
		return locationImage;
	}

	/**
	 * Setter method for location picture
	 * 
	 * @param locationImage
	 *            - in case if user wants to change location picture.
	 */
	public void setLocationImage(String locationImage) {
		this.locationImage = locationImage;
	}

	/**
	 * Getter method for retrieving location name.
	 * 
	 * @return locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * Setter method for retrieving locationName
	 * 
	 * @param locationName
	 *            - in case if user wants to change location name.
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * Getter method for retrieving rating of the venue.
	 * 
	 * @return rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * Setter method for posting rating of the venue
	 * 
	 * @param rating
	 *            - in case if user wants to change rating of the venue.
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * Getter method for retrieving user reviews in particular venue.
	 * 
	 * @return review
	 */
	public String getReview() {
		return review;
	}

	/**
	 * Setter method for posting review for particular venue
	 * 
	 * @param review
	 *            - in case if user wants to post review of this venue.
	 */
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * Overriden toString method to get all follower profile venue data as a
	 * chunk of String.
	 */
	@Override
	public String toString() {
		return locationName  + rating +  review;
	}
}