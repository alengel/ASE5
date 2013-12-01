package com.team5.courseassignment.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.team5.courseassignment.data.VenueReview;

public class VenueReviewTest extends AndroidTestCase {

	@Test
	public void testConstructor() {

		VenueReview venue = new VenueReview("profileImage",
											"firstName",
											"lastName",
											"reviewerId",
											"rating",
											"review",
											"reviewId",
											"votes");

		assertEquals("profileImage", venue.profileImage);
		assertEquals("firstName", venue.firstName);
		assertEquals("lastName", venue.lastName);
		assertEquals("reviewerId", venue.reviewerId);
		assertEquals("rating", venue.rating);
		assertEquals("review", venue.review);
		assertEquals("reviewId", venue.reviewId);
		assertEquals("votes", venue.votes);
	}
}
