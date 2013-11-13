package com.team5.courseassignment;

public class VenueReview {
	public String firstName;
	public String lastName;
	public String rating;
	public String review;
	
	public VenueReview(String firstName, String lastName, String rating, String review) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.rating = rating;
		this.review = review;
	}
	
	@Override 
	public String toString()
	{
		return firstName + " " + lastName + " was here. Rating: " + rating + " stars. \""+ review +"\" ";
	}
}