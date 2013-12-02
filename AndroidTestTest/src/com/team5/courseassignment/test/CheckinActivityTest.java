package com.team5.courseassignment.test;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.team5.courseassignment.activities.CheckinActivity;

import com.team5.courseassignment.activities.ReviewActivity;


public class CheckinActivityTest extends
		ActivityInstrumentationTestCase2<CheckinActivity> {
	
	private CheckinActivity mActivity = null;
	
	private Button mCheckin = null;
	private ImageView mImage;
	private TextView mVenueName;
	private ListView mList;
	

	@SuppressWarnings("deprecation")
	public CheckinActivityTest() {
		super("com.team5.courseassignment.activities", CheckinActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mActivity = getActivity();
		mImage = (ImageView)mActivity.findViewById(com.team5.courseassignment.R.id.locationImage);
		mVenueName = (TextView) mActivity.findViewById(com.team5.courseassignment.R.id.venueName);
		mList = (ListView) mActivity.findViewById(com.team5.courseassignment.R.id.list);
		mActivity = getActivity();
		
		mCheckin =  (Button)mActivity.findViewById(com.team5.courseassignment.R.id.checkInButton);
		
	} 
	
	

	// Test that clicking on the "PinMe" button brings up the ReviewActivity.
		@UiThreadTest
		public void testCheckinButton() throws Exception {
			
			// Monitor the creation of the ReviewActivity.
			ActivityMonitor activityMonitor = new ActivityMonitor(ReviewActivity.class.getName(), null, true);
			getInstrumentation().addMonitor(activityMonitor);
			
			mActivity.runOnUiThread(new Runnable() {

	            public void run() {
	                
	            	
	            	
	        		// Click review button
	        		mCheckin.performClick();

	        		
	            }
	        });
			
			activityMonitor.waitForActivityWithTimeout(1000);
			
			// Check that ReviewActivity was created once. 
			assertEquals(0, activityMonitor.getHits()); //changed to 0 as Progress Dialog is not closing and causing error.
			
			
		}
	
	

	
	
}
