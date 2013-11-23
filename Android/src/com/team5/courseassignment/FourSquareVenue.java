package com.team5.courseassignment;

public class FourSquareVenue {
	public String name;
	public String id;
	public int distance;
	
	
	public FourSquareVenue(String name, String id, int distance) {
		
		this.name = name;
		this.id = id;
		this.distance = distance;
	}
	
	@Override 
	public String toString()
	{
		return name + ", "+ distance+ " m";
	}
}
