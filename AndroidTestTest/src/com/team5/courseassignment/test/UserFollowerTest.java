package com.team5.courseassignment.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.team5.courseassignment.data.UserFollowers;

public class UserFollowerTest extends AndroidTestCase {

	@Test
	public void testConstructor() {

		UserFollowers data = new UserFollowers("profile_image",
											"first_name",
											"last_name");

		assertEquals("profileImage", data.profile_image);
		assertEquals("firstName", data.first_name);
		assertEquals("lastName", data.last_name);
		
		
	}
}
