package com.team5.courseassignment;

public class VenueReview {
	public String profileImage;
	public String firstName;
	public String lastName;
	public String reviewerId;
	public String rating;
	public String review;
	public String reviewId;
	public String votes;

	/**
	 * Constructor method of venue review. Info as follows
	 * 
	 * @param profileImage
	 *            - user picture which is taken from server as String an after
	 *            decoded from BASE64
	 * @param firstName
	 *            - user name - String.
	 * @param lastName
	 *            - User's last name - String.
	 * @param reviewerId
	 *            - Reviewer id - String.
	 * @param rating
	 *            - user rating for the venue/location - String.
	 * @param review
	 *            - user review for the venue/location - String.
	 * @param reviewId
	 *            - The review id - String.
	 * @param votes
	 *            - Votes for particular review - String.
	 */
	public VenueReview(String profileImage, String firstName, String lastName,
			String reviewerId, String rating, String review, String reviewId,
			String votes) {

		this.profileImage = profileImage;
		this.firstName = firstName;
		this.lastName = lastName;
		this.reviewerId = reviewerId;
		this.rating = rating;
		this.review = review;
		this.reviewId = reviewId;
		this.votes = votes;
	}

	/**
	 * Getter method for user image
	 * 
	 * @return profileImage
	 */
	public String getProfileImage() {
		return profileImage;
	}

	/**
	 * Setter method for user image
	 * 
	 * @param profileImage
	 *            - in case if user wants to change his/her profile picture.
	 */
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * Getter method for retrieving user name (firstName)
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter method for posting user name (firstName)
	 * 
	 * @param firstName
	 *            - in case if user wants to change his/her first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter method for retrieving user's last name (lastName)
	 * 
	 * @return user's lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter method for posting user's last name (lastName)
	 * 
	 * @param lastName
	 *            - in case if user wants to change his/her last name.
	 */
	public void setLasttName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter method for retrieving reviewer Id (reviewerId)
	 * 
	 * @return reviewerId
	 */
	public String getReviewerId() {
		return reviewerId;
	}

	/**
	 * Setter method for posting reviewerId (reviewerId)
	 * 
	 * @param reviewerId
	 *            - String.
	 * 
	 */
	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
	}

	/**
	 * Getter method for retrieving rating (rating)
	 * 
	 * @return rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * Setter method for posting rating (rating)
	 * 
	 * @param rating
	 *            - String.
	 * 
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * Getter method for retrieving review (review)
	 * 
	 * @return review
	 */
	public String getReview() {
		return review;
	}

	/**
	 * Setter method for posting review (review)
	 * 
	 * @param review
	 *            - String.
	 * 
	 */
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * Getter method for retrieving reviewId (reviewId)
	 * 
	 * @return reviewId
	 */
	public String getReviewId() {
		return reviewId;
	}

	/**
	 * Setter method for posting reviewId (reviewId)
	 * 
	 * @param reviewId
	 *            - String.
	 * 
	 */
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	/**
	 * Getter method for retrieving votes (votes)
	 * 
	 * @return votes
	 */
	public String getVotes() {
		return votes;
	}

	/**
	 * Setter method for posting votes (votes)
	 * 
	 * @param votes
	 *            - String.
	 * 
	 */
	public void setVotes(String votes) {
		this.votes = votes;
	}
}