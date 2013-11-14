package com.team5.courseassignment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ReviewActivity extends Activity {
	
	//variables for the POST call
	private final static String REVIEW_URL = "http://switchcodes.in/sandbox/projectpackets/t5/user/check-in";
	
	//key of user for connecting to the server
	private String kKey;
    private final static String KEY_JSON ="key";
	private final static String SUCCESS_JSON = "success";
	
	//venue details
	private String venueName;
	private final static String VENUE_NAME = "name";
	private String venueId;
	private final static String VENUE_ID = "id";
	
	private final static String RATING = "rating";
	private final static String REVIEW = "review";
	private final static String IMAGE ="profileImage";
	
	private AlertDialog.Builder alert;
	private ImageView imageView;
    
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
    	alert = new AlertDialog.Builder(this);
    	
    	//Setting up review button.
    	Button checkinButton = (Button) findViewById(R.id.review_button);
    	checkinButton.setOnClickListener(new OnClickListener() {
			
    		@SuppressWarnings("unchecked")
			public void onClick(View v) {
    			
    			//get data for call
    			
    			imageView.buildDrawingCache();
    			Bitmap profilePicture = imageView.getDrawingCache();
    			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
    			profilePicture.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object   
    			byte[] b = baos.toByteArray();

    			String encodedImage = Base64.encodeToString(b , Base64.DEFAULT);
    			
				String review = ((EditText) findViewById(R.id.venue_review)).getEditableText().toString();
				RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_bar);
				String rating = String.valueOf(ratingBar.getRating());
				
				List<NameValuePair> data = new ArrayList<NameValuePair>(5);
				data.add(new BasicNameValuePair(KEY_JSON, kKey));
				data.add(new BasicNameValuePair("venue_name", venueName));
				data.add(new BasicNameValuePair("venue_id", venueId));
				data.add(new BasicNameValuePair(RATING, rating));
				data.add(new BasicNameValuePair(REVIEW, review));
				data.add(new BasicNameValuePair(IMAGE,encodedImage));
				
				//make POST call
				new ReviewAsyncTask().execute(data);
			}
		});	
    }
    
    private class ReviewAsyncTask extends AsyncTask<List<NameValuePair>, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(List<NameValuePair>... params) {
			
			
			List<NameValuePair> data = params[0];
			
			JSONObject resultJson = HttpRequest.makePostRequest(REVIEW_URL, data);
			
			return resultJson;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			
			super.onPostExecute(result);
			
			if(result != null) {
				
				try {
					
					String success = result.getString(SUCCESS_JSON);
					if(success.equals("true")) {
						
						showReviewSuccessMessage((getResources().getString(R.string.review_success_title)),getResources().getString(R.string.review_success));
						
					} else {
						//TODO: if the server responds with error, display message
					} 
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			} 
		}	
    }
    
    private void showReviewSuccessMessage(final String title,final String message) {
		
    	alert.setTitle(title);
		
		alert.setMessage(message);
		
		alert.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if((getResources().getString(R.string.review_success)).equals(message)){
					Intent i = new Intent(getApplicationContext(), MapActivity.class);
					i.putExtra(KEY_JSON, kKey);
					startActivity(i);
				}else{
					// TODO: If the user clicks neither button.
				}
			}
		});
		
		alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if((getResources().getString(R.string.review_success)).equals(message)){
					Intent i = new Intent(getApplicationContext(),  CheckinActivity.class);
					
					i.putExtra(KEY_JSON, kKey);
					i.putExtra(VENUE_NAME, venueName);
					i.putExtra(VENUE_ID, venueId);
					
					startActivity(i);
				}else{
					// TODO: If the user clicks neither button. 
				}
			}
		});
		
		alert.show();
	}
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
	
	//set settings icon actions
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		i.putExtra(KEY_JSON, kKey);
		startActivity(i);
		
		return true;
    }
}
