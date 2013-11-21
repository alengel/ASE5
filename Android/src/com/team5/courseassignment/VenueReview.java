package com.team5.courseassignment;

import android.graphics.Bitmap;

public class VenueReview {
	public String profileImage;
	public String firstName;
	public String lastName;
	public String rating;
	public String review;
	
	public VenueReview(String profileImage, String firstName, String lastName, String rating, String review) {
		
		this.profileImage=profileImage;
		this.firstName = firstName;
		this.lastName = lastName;
		this.rating = rating;
		this.review = review;
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
	public String toString()
	{
		return profileImage + " " + firstName + " " + lastName + " was here. Rating: " + rating + " stars. \""+ review +"\" ";
	}
}