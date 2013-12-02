package com.team5.courseassignment.data;

public class UserFollowers {
	public String profileImage;
	public String firstName;
	public String lastName;

	/**
	 * Constructor method of user followers. Info as follows
	 * 
	 * @param profile_image
	 *            - user picture which is taken from server as String an after
	 *            decoded from BASE64
	 * @param first_name
	 *            - user name - String.
	 * @param last_name
	 *            - User's last name - String.
	 * 
	 */
	public UserFollowers(String profileImage, String firstName,
			String lastName) {

		this.profileImage = profileImage;
		this.firstName = firstName;
		this.lastName = lastName;

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
	 * @param profileImage
	 *            - in case if user wants to change his/her profile picture.
	 */
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * Getter method for retrieving user name (first_name)
	 * 
	 * @return first_name
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
	 * Setter method for posting user's last name (last_name)
	 * 
	 * @param last_name
	 *            - in case if user wants to change his/her last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Overriden toString method to get all required user followers data as a
	 * chunk of String.
	 */
	@Override
	public String toString() {
		return profileImage + " " + firstName + lastName + "\" ";
	}
}
