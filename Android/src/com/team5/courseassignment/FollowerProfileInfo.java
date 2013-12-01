package com.team5.courseassignment;

public class FollowerProfileInfo {
	public String profileImage;
	public String firstName;
	public String lastName;

	/**
	 * Constructor method of profile follower. Info as follows
	 * 
	 * @param profileImage
	 *            - user picture which is taken from server as String an after
	 *            decoded from BASE64
	 * @param firstName
	 *            - user name - String.
	 * @param lastName
	 *            - User's last name - String.
	 */
	public FollowerProfileInfo(String profileImage, String firstName,
			String lastName) {

		this.profileImage = profileImage;
		this.firstName = firstName;
		this.lastName = lastName;
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
	 * Getter method for retrieving user name (first name)
	 * 
	 * @return user name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter method for retrieving user name (first name)
	 * 
	 * @param firstName
	 *            - in case if user wants to change his/her first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter method for retrieving user's last name (last name)
	 * 
	 * @return user's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter method for retrieving user's last name (last name)
	 * 
	 * @param lastName
	 *            - in case if user wants to change his/her last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter method for retrieving location image.
	 * 
	 * @return null
	 */
	public String getLocationImage() {

		return null;
	}
}