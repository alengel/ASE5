package com.team5.courseassignment;

public class VenueReview {
	public String profileImage;
	public String firstName;
	public String lastName;
	public String reviewerId;
	public String rating;
	public String review;
	public static String reviewId;
	public String votes;

	public VenueReview(String profileImage, String firstName, String lastName, String reviewerId,
			String rating, String review, String reviewId, String votes) {

		this.profileImage = profileImage;
		this.firstName = firstName;
		this.lastName = lastName;
		this.reviewerId = reviewerId;
		this.rating = rating;
		this.review = review;
		VenueReview.reviewId = reviewId;
		this.votes = votes;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLasttName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
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
	
	public static String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		VenueReview.reviewId = reviewId;
	}

	public String getVotes() {
		return votes;
	}

	public void setVotes(String votes) {
		this.votes = votes;
	}
}