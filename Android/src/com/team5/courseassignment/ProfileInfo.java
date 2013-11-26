package com.team5.courseassignment;

public class ProfileInfo {
	public String profileImage;
	public String name;
	public String lastName;
	public String email;
	public String editName;
	public String editLastName;

	
	public ProfileInfo(String profileImage, String name, String lastName, String email, String editName, String editLastName) {
		
		this.profileImage=profileImage;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.editName = editName;
		this.editLastName = editLastName;
	}
	
	public String getProfileImage() {
        return profileImage;
    }
	
	public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
	
	public String getName() {
        return name;
    }
	
	public void setName(String name) {
        this.name = name;
    }
	
	public String getLastName() {
        return lastName;
    }
	
	public void setLastName(String lastName) {
        this.lastName = lastName;
    }
	
	public String getEmail() {
        return email;
    }
	
	public void setEmail(String email) {
        this.email = email;
    }
	
	public String getEditName() {
        return editName;
    }
	
	public void setEditName(String editName) {
        this.editName = editName;
    }
	
	public String getEditLastName() {
        return editLastName;
    }
	
	public void setEditLastName(String editLastName) {
        this.editLastName = editLastName;
    }
	
	/*@Override 
	public String toString()
	{
		return  name + " " + lastName + " was here. Rating: " + rating + " stars. \""+ review +"\" ";
	}*/
}
