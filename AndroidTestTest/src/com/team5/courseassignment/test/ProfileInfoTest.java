package com.team5.courseassignment.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.team5.courseassignment.data.ProfileInfo;

public class ProfileInfoTest extends AndroidTestCase {

	@Test
	public void testConstructor() {

		ProfileInfo data = new ProfileInfo("profileImage",
											"firstName",
											"lastName",
											"email");

		assertEquals("profileImage", data.profileImage);
		assertEquals("firstName", data.firstName);
		assertEquals("lastName", data.lastName);
		assertEquals("email", data.email);
		
	}
}
