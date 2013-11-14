package com.team5.courseassignment.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.team5.courseassignment.FourSquareVenue;

public class FourSquareVenueTest extends AndroidTestCase {

	@Test
	public void testConstructor() {

		FourSquareVenue venue = new FourSquareVenue("name", "id");
		assertEquals("name", venue.name);
		assertEquals("id", venue.id);
	}

}
