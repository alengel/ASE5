package com.team5.courseassignment;

public class FollowerProfileInfo {
	public String profileImage;
	public String firstName;
	public String lastName;

	public FollowerProfileInfo(String profileImage, String firstName, String lastName) {

		this.profileImage = profileImage;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String geProfileImage() {
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

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}