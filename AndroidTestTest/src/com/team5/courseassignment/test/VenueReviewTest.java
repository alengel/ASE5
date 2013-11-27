package com.team5.courseassignment.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.team5.courseassignment.VenueReview;

public class VenueReviewTest extends AndroidTestCase {

	@Test
	public void testConstructor() {

		VenueReview venue = new VenueReview("firstName", "lastName", "rating", "review");

		assertEquals("firstName", venue.firstName);
		assertEquals("lastName", venue.lastName);
		assertEquals("rating", venue.rating);
		assertEquals("review", venue.review);
	}
}
