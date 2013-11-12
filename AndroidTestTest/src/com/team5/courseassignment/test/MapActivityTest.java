package com.team5.courseassignment.test;

import com.team5.courseassignment.MapActivity;
import com.team5.courseassignment.SettingsActivity;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;

public class MapActivityTest extends
		ActivityInstrumentationTestCase2<MapActivity> {

	private MapActivity mActivity;
	private View mSettingsMenu;

	@SuppressWarnings("deprecation")
	public MapActivityTest() {
		super("com.team5.courseassignment", MapActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mActivity = getActivity();
		mSettingsMenu = (View) mActivity.findViewById(com.team5.courseassignment.R.id.action_settings);
	}

	// Test that clicking on the "settings" menu brings up the SettingsActivity.
	@UiThreadTest
	public void testSettingsMenu() throws Exception {

		// Monitor the creation of the SettingsActivity.
		ActivityMonitor activityMonitor = new ActivityMonitor(SettingsActivity.class.getName(), null, true);
		getInstrumentation().addMonitor(activityMonitor);

		// Click register button
		mSettingsMenu.performClick();

		activityMonitor.waitForActivityWithTimeout(10000);

		// Check that SettingsActivity was created once.
		assertEquals(1, activityMonitor.getHits());
	}

}
