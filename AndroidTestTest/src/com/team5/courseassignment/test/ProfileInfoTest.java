package com.team5.courseassignment.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.team5.courseassignment.data.ProfileInfo;

public class ProfileInfoTest extends AndroidTestCase {

	@Test
	public void testConstructor() {

		ProfileInfo data = new ProfileInfo("profile_image",
											"first_name",
											"last_name",
											"email");

		assertEquals("profileImage", data.profile_image);
		assertEquals("firstName", data.first_name);
		assertEquals("lastName", data.last_name);
		assertEquals("reviewerId", data.email);
		
	}
}
