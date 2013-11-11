package com.team5.courseassignment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !!!
 */

//TODO: show licensing info in the Settings!!!
public class CheckinActivity extends Activity {
	
	//key of user for connecting to the server
	private String kKey;
	private final static String KEY_JSON ="key";
	
	//venue details
	private String venueName;
	private final static String VENUE_NAME = "name";
	private String venueId;
	private final static String VENUE_ID = "id";
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //get the key and venue details
    	kKey = this.getIntent().getStringExtra(KEY_JSON);
    	venueName = this.getIntent().getStringExtra(VENUE_NAME);
    	venueId = this.getIntent().getStringExtra(VENUE_ID);
    	
    	//set layout
    	setContentView(R.layout.checkin_activity);
    	TextView name = (TextView) findViewById(R.id.venue_name);
    	name.setText(venueName);
    	
    	//Setting up check in button.
    	Button checkinButton = (Button) findViewById(R.id.check_in_button);
    	checkinButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				checkIn();
			}
		});	
    }
    
    private void checkIn() {
    	Log.d("check", "in here!!");
    }
}
