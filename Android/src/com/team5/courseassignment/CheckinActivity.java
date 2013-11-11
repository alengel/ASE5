package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CheckinActivity extends Activity {
	
	//variables for the POST call
	private final static String CHECK_IN_URL = "http://switchcodes.in/sandbox/projectpackets/t5/user/geo-push";
	private final static String TIMESTAMP = "timestamp";
	
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
    
    @SuppressWarnings("unchecked")
	private void checkIn() {
    	
    	Long tsLong = System.currentTimeMillis() / 1000L;
    	String timestamp = tsLong.toString();
    	
    	List<NameValuePair> data = new ArrayList<NameValuePair>(3);
    	data.add(new BasicNameValuePair(KEY_JSON, kKey));
    	data.add(new BasicNameValuePair(VENUE_ID, venueId));
//    	data.add(new BasicNameValuePair(VENUE_NAME, venueName));
		data.add(new BasicNameValuePair(TIMESTAMP, timestamp));
    	
    	//make POST call
		new CheckinAsyncTask().execute(data);
    }
    
    private class CheckinAsyncTask extends AsyncTask<List<NameValuePair>, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(List<NameValuePair>... params) {
			
			
			List<NameValuePair> data = params[0];
			
			JSONObject resultJson = HttpPostRequest.makePostRequest(CHECK_IN_URL, data);
			
			return resultJson;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			
			super.onPostExecute(result);
			
			if(result != null) {
				
				try {
					
					String success = result.getString(SUCCESS_JSON);
					
					if(success.equals("true")) {
						
						// launch ReviewActivity
						Intent i = new Intent(getApplicationContext(), ReviewActivity.class);
						i.putExtra(KEY_JSON, kKey);
						startActivity(i);
					} else {
						//TODO: if the server responds with error, display message
					} 
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			} 
		}	
    } 
}
