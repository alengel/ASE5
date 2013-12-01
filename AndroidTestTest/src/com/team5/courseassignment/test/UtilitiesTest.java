package com.team5.courseassignment.test;

import com.team5.courseassignment.utilities.Utilities;

import org.junit.Test;

import android.test.AndroidTestCase;

public class UtilitiesTest extends AndroidTestCase {

	@Test
	public void test_convertByteArrayToString() {

		byte[] byteArray = new byte[] { -6, -101, -21, -103, -28, 2, -102, -43,
				-90, 97, 83, -103, -25, -69, -82, 33, 53, 96, -122, -77 };
		
		String byteArrayAsString = Utilities.convertByteArrayToString(byteArray);

		String expected = "fa9beb99e4029ad5a6615399e7bbae21356086b3";

		super.assertEquals(expected, byteArrayAsString);
	}

	@Test
	public void test_encryptString() {

		String expected = "fa9beb99e4029ad5a6615399e7bbae21356086b3";

		String string = "changeme";
		String byteArrayAsString = Utilities.encryptString(string);

		super.assertEquals(expected, byteArrayAsString);
	}

}
