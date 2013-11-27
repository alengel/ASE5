package com.team5.courseassignment;

public class ProfileInfo {
	public String profile_image;
	public String first_name;
	public String last_name;
	public String email;
	

	
	public ProfileInfo(String profile_image, String first_name, String last_name, String email) {
		
		this.profile_image=profile_image;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		
	}
	
	public String getProfileImage() {
        return profile_image;
    }
	
	public void setProfileImage(String profileImage) {
        this.profile_image = profileImage;
    }
	
	public String getName() {
        return first_name;
    }
	
	public void setName(String first_name) {
        this.first_name = first_name;
    }
	
	public String getLastName() {
        return last_name;
    }
	
	public void setLastName(String last_name) {
        this.last_name = last_name;
    }
	
	public String getEmail() {
        return email;
    }
	
	public void setEmail(String email) {
        this.email = email;
    }
	
	
	
	
	public String toString()
	{
		return  profile_image + " " +first_name+ last_name  + email +"\" ";
	}
}
