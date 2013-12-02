package com.team5.courseassignment.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.team5.courseassignment.data.FollowerProfileInfo;

public class FollowerProfileInfoTest extends AndroidTestCase {

	@Test
	public void testConstructor() {

		FollowerProfileInfo venue = new FollowerProfileInfo("profileImage",
											"firstName",
											"lastName"
											);

		assertEquals("profileImage", venue.profileImage);
		assertEquals("firstName", venue.firstName);
		assertEquals("lastName", venue.lastName);
		
	}
}
