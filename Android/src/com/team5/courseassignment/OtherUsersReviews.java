package com.team5.courseassignment;

public class OtherUsersReviews {
	public String locationImage;
	public String locationName;
	public String rating;
	public String review;

	public OtherUsersReviews(String locationImage, String locationName,String rating, String review) {

		this.locationImage = locationImage;
		this.locationName = locationName;
		this.rating = rating;
		this.review = review;
	}

	public String getLocationImage() {
		return locationImage;
	}

	public void setLocationImage(String locationImage) {
		this.locationImage = locationImage;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@Override
	public String toString() {
		return locationName + "Rating: " + rating
				+ " stars. \"" + review + "\" ";
	}
}