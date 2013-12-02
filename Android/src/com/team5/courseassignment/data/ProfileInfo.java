package com.team5.courseassignment.data;

public class ProfileInfo {
	public String profileImage;
	public String firstName;
	public String lastName;
	public String email;

	/**
	 * Constructor method of user profile. Info as follows
	 * 
	 * @param profile_image
	 *            - user picture which is taken from server as String an after
	 *            decoded from BASE64
	 * @param first_name
	 *            - user name - String.
	 * @param last_name
	 *            - User's last name - String.
	 * @param email
	 *            - User's email - String.
	 */
	public ProfileInfo(String profileImage, String firstName,
			String lastName, String email) {

		this.profileImage = profileImage;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;

	}

	/**
	 * Getter method for user image
	 * 
	 * @return profile_image
	 */
	public String getProfileImage() {
		return profileImage;
	}

	/**
	 * Setter method for user image
	 * 
	 * @param profile_image
	 *            - in case if user wants to change his/her profile picture.
	 */
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * Getter method for retrieving user name (first_name)
	 * 
	 * @return user name
	 */
	public String getName() {
		return firstName;
	}

	/**
	 * Setter method for posting user name (first_name)
	 * 
	 * @param first_name
	 *            - in case if user wants to change his/her first name.
	 */
	public void setName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter method for retrieving user's last name (last_name)
	 * 
	 * @return user's last_name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter method for retrieving user's last name (last_name)
	 * 
	 * @param last_name
	 *            - in case if user wants to change his/her last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter method for retrieving user's email (email)
	 * 
	 * @return user's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter method for retrieving user's email (email)
	 * 
	 * @param email
	 *            - in case if user wants to change his/her email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Overriden toString method to get all required user profile data as a
	 * chunk of String.
	 */
	@Override
	public String toString() {
		return profileImage + " " + firstName + lastName + email + "\" ";
	}
}
