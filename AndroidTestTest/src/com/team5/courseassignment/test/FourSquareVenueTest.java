package com.team5.courseassignment.test;

import org.junit.Test;
import com.google.android.gms.maps.model.LatLng;

import android.test.AndroidTestCase;

import com.team5.courseassignment.data.FourSquareVenue;

public class FourSquareVenueTest extends AndroidTestCase {

	@Test
	public void testConstructor() {

		FourSquareVenue venue = new FourSquareVenue("name", "id", 3, new LatLng(0,0), "homePage", "phoneNumber");
		
		assertEquals("name", venue.getName());
		assertEquals("id", venue.getId());
		assertEquals(3, venue.getDistance());
		assertEquals("homePage", venue.getHomepage());
		assertEquals("phoneNumber", venue.getPhoneNumber());
	}
}
