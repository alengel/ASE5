package com.team5.courseassignment.test;

import com.team5.courseassignment.activities.ForgottenPasswordActivity;
import com.team5.courseassignment.activities.LoginActivity;
import com.team5.courseassignment.activities.RegistrationActivity;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivityTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {
	
	private LoginActivity mActivity;
	private TextView mRegister;
	private TextView mForgottenPw;
	private EditText mPassword;
	private EditText mEmail;

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
		mForgottenPw = (TextView) mActivity.findViewById(com.team5.courseassignment.R.id.forgotten_password_login);
		
		mPassword = (EditText) mActivity.findViewById(com.team5.courseassignment.R.id.password_box_login);
		mEmail = (EditText) mActivity.findViewById(com.team5.courseassignment.R.id.email_box_login);
	} 
	
	// Test email and password hints are correct
	public void testHints()
	{
		String passwordHint = mPassword.getHint().toString();
		String expectedPasswordHint = mActivity.getResources().getString(com.team5.courseassignment.R.string.password);
		
		assertEquals(expectedPasswordHint, passwordHint);
		
		String emailHint = mEmail.getHint().toString();
		String expectedEmailHint = mActivity.getResources().getString(com.team5.courseassignment.R.string.email_address);
		
		assertEquals(emailHint, expectedEmailHint);
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
	
	// Test that clicking on the "forgotten password" link brings up the ForgottenPasswordActivity.
		@UiThreadTest
		public void testForgottenPasswordLink() throws Exception {
			
			// Monitor the creation of the ForgottenPasswordActivity.
			ActivityMonitor activityMonitor = new ActivityMonitor(ForgottenPasswordActivity.class.getName(), null, true);
			getInstrumentation().addMonitor(activityMonitor);

			// Click register button
			mForgottenPw.performClick();

			activityMonitor.waitForActivityWithTimeout(10000);
			
			// Check that ForgottenPasswordActivity was created once. 
			assertEquals(1, activityMonitor.getHits());
		}
}
