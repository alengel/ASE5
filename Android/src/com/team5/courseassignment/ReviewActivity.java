package com.team5.courseassignment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ReviewActivity extends Activity {
	
	//variables for the POST call
	private final static String REVIEW_URL = "http://switchcodes.in/sandbox/projectpackets/t5/user/geo-push";
	
	//key of user for connecting to the server
	private String kKey;
    private final static String KEY_JSON ="key";
	private final static String SUCCESS_JSON = "success";
	
	//venue details
	private String venueName;
	private final static String VENUE_NAME = "name";
	private String venueId;
	private final static String VENUE_ID = "id";
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Get the key and venue details
        kKey = this.getIntent().getStringExtra(KEY_JSON);
    	venueName = this.getIntent().getStringExtra(VENUE_NAME);
    	venueId = this.getIntent().getStringExtra(VENUE_ID);
    	
    	//Set layout
    	setContentView(R.layout.review);
    	TextView name = (TextView) findViewById(R.id.venue_name_review);
    	name.setText(venueName);
    	
    	//Setting up review button.
    	Button checkinButton = (Button) findViewById(R.id.review_button);
    	checkinButton.setOnClickListener(new OnClickListener() {
			
    		public void onClick(View v) {
				Log.d("clicked", "the review");
			}
		});	
    }
}
