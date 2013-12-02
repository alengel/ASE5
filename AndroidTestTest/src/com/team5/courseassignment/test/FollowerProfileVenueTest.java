package com.team5.courseassignment.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.team5.courseassignment.data.FollowerProfileVenue;

public class FollowerProfileVenueTest extends AndroidTestCase {

	@Test
	public void testConstructor() {

		FollowerProfileVenue venue = new FollowerProfileVenue("locationImage",
											"locationName",
											"rating", "review"
											);

		assertEquals("locationImage", venue.locationImage);
		assertEquals("locationName", venue.locationName);
		assertEquals("rating", venue.rating);
		assertEquals("review", venue.review);
		
	}
}
