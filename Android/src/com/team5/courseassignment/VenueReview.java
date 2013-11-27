package com.team5.courseassignment;

public class VenueReview {
	public String profile_image;
	public String first_name;
	public String last_name;
	public String rating;
	public String review;
	public String total_vote;
	
	public VenueReview(String profile_image, String first_name, String last_name, String rating, String review, String total_vote) {
		
		this.profile_image=profile_image;
		this.first_name = first_name;
		this.last_name = last_name;
		this.rating = rating;
		this.review = review;
		this.total_vote = total_vote;
	}
	
	public String getProfileImage() {
        return profile_image;
    }
	
	public void setProfileImage(String profile_image) {
        this.profile_image = profile_image;
    }
	
	public String getFirstName() {
        return first_name;
    }
	
	public void setFirstName(String first_name) {
        this.first_name = first_name;
    }
	
	public String getLastName() {
        return last_name;
    }
	
	public void setLasttName(String last_name) {
        this.last_name = last_name;
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
	
	public String getVotes() {
        return total_vote;
    }
	
	public void setVotes(String total_vote) {
        this.total_vote = total_vote;
    }
	
	@Override 
	public String toString()
	{
		return  first_name + " " + last_name + " was here. Rating: " + rating + " stars. \""+ review +"\"+ total_vote ";
	}
}