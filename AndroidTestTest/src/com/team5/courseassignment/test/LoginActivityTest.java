package com.team5.courseassignment.test;

import com.team5.courseassignment.LoginActivity;
import com.team5.courseassignment.RegistrationActivity;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;

import android.widget.TextView;

public class LoginActivityTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {
	
	private LoginActivity mActivity;
	private TextView mRegister;

	@SuppressWarnings("deprecation")
	public LoginActivityTest() {
		super("com.team5.courseassignment", LoginActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mActivity = getActivity();

		mRegister = (TextView) mActivity.findViewById(com.team5.courseassignment.R.id.register_here_login);

	} 

	// Test that clicking on the "register" button brings up the RegistrationActivity.
	@UiThreadTest
	public void testRegisterButton() throws Exception {
		
		// Monitor the creation of the RegistrationActivity.
		ActivityMonitor activityMonitor = new ActivityMonitor(RegistrationActivity.class.getName(), null, true);
		getInstrumentation().addMonitor(activityMonitor);

		// Click register button
		mRegister.performClick();

		activityMonitor.waitForActivityWithTimeout(10000);
		
		// Check that RegistrationActivity was created once. 
		assertEquals(1, activityMonitor.getHits());
	}
}
