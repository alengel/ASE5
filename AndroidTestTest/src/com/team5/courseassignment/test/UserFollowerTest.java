package com.team5.courseassignment.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.team5.courseassignment.data.UserFollowers;

public class UserFollowerTest extends AndroidTestCase {

	@Test
	public void testConstructor() {

		UserFollowers data = new UserFollowers("profileImage",
											"firstName",
											"lastName");

		assertEquals("profileImage", data.profileImage);
		assertEquals("firstName", data.firstName);
		assertEquals("lastName", data.lastName);
		
		
	}
}
