package com.team5.courseassignment.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;



import com.team5.courseassignment.R;
import com.team5.courseassignment.adapters.VenueReviewAdapter;
import com.team5.courseassignment.data.VenueReview;
import com.team5.courseassignment.parsers.VenueReviewParser;
import com.team5.courseassignment.services.SearchService;
import com.team5.courseassignment.utilities.HttpRequest;
import com.team5.courseassignment.utilities.SharedPreferencesEditor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CheckinActivity extends Activity {

	// variables for the POST call
	private final static String CHECK_IN_URL_EXT = "check-in";

	// variables for the GET call
	private final static String RETRIEVE_VENUE_REVIEW_URL_EXT = "venue/venue_id/";

	// key of user for connecting to the server
	private String kKey;
	private final static String KEY_JSON = "key";
	private final static String SUCCESS_JSON = "success";

	// venue details
	private String venueName;
	private final static String VENUE_NAME = "name";
	private String venueId;
	private final static String VENUE_ID = "id";
	private double venueLat;
	private final static String VENUE_LAT = "lat";
	private double venueLng;
	private final static String VENUE_LNG = "lng";
	private String venueHomepage;
	private final static String VENUE_HOMEPAGE = "homepage";
	private String venuePhoneNumber;
	private final static String VENUE_PHONE = "phone";
	private double userLat;
	private final static String USER_LAT = "u_lat";
	private double userLng;
	private final static String USER_LNG = "u_lng";

	ListView list;
	VenueReviewAdapter adapter;
	
	ProgressDialog checkinAsyncTaskDialog;
	ProgressDialog venueAsyncTaskDialog;
	
	CheckinAsyncTask checkinAsyncTask;
	VenueReviewsAsyncTask venueAsyncTask;

	/**
	 * Called when the activity is first created. This is where we do all of our
	 * normal static set up: create views, bind data to lists, etc. This method
	 * also provides a Bundle containing the activity's previously frozen state,
	 * if there was one.
	 * 
	 * 
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the key and venue details
		kKey = SharedPreferencesEditor.getKey();
		venueName = this.getIntent().getStringExtra(VENUE_NAME);
		venueId = this.getIntent().getStringExtra(VENUE_ID);
		venueLat = this.getIntent().getDoubleExtra(VENUE_LAT, 0);
		venueLng = this.getIntent().getDoubleExtra(VENUE_LNG, 0);
		venueHomepage = this.getIntent().getStringExtra(VENUE_HOMEPAGE);
		venuePhoneNumber = this.getIntent().getStringExtra(VENUE_PHONE);
		userLat = this.getIntent().getDoubleExtra(USER_LAT, 0);
		userLng = this.getIntent().getDoubleExtra(USER_LNG, 0);
		
		// vote = this.getIntent().getStringExtra(TOTAL_UP);

		// Set layout
		setContentView(R.layout.checkin);
		TextView name = (TextView) findViewById(R.id.venueName);
		name.setText(venueName);
		
		defineExternalIntentButtonActions();

		// Setting up check in button.
		Button checkinButton = (Button) findViewById(R.id.checkInButton);
		checkinButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checkIn();
			}
		});

		// make GET request to retrieve existing user reviews for venue
		String data = venueId + "/key/" + kKey;
		venueAsyncTaskDialog = ProgressDialog.show(CheckinActivity.this, "Please wait", "Loading ...");

		venueAsyncTask = (VenueReviewsAsyncTask) new VenueReviewsAsyncTask(venueAsyncTaskDialog).execute(data);
	}

	private void defineExternalIntentButtonActions() {
		
		Button directionsButton = (Button) findViewById(R.id.directionsButton);
		if(venueLat != Double.MAX_VALUE && venueLng != Double.MAX_VALUE && userLat != Double.MAX_VALUE && userLng != Double.MAX_VALUE) {
			directionsButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					String userLatLng = userLat + "," + userLng;
					
					Uri mapsUri = Uri
							.parse("http://maps.google.com/maps?saddr=" + userLatLng + "&daddr="
									+ venueLat
									+ ","
									+ venueLng);
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(mapsUri);
					startActivity(intent);
					
				}
			});
		} else {
			directionsButton.setEnabled(false);
		}
		
		
		Button websiteButton = (Button) findViewById(R.id.websiteButton);
		if(venueHomepage == null || venueHomepage.equals("")) {
			websiteButton.setEnabled(false);
		} else {
			websiteButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Uri venueWebsite = Uri.parse(venueHomepage);
					Intent i = new Intent(Intent.ACTION_VIEW);

					i.setData(venueWebsite);
					startActivity(i);
					
				}
			});
		}
		
		
		Button callButton = (Button) findViewById(R.id.callButton);
		if (venuePhoneNumber == null || venuePhoneNumber.equals("")) {
			callButton.setEnabled(false);

		} else {
			callButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					Uri telUri = Uri.parse("tel:"
							+ venuePhoneNumber.trim());
					Intent i = new Intent(Intent.ACTION_DIAL);
					i.setData(telUri);
					startActivity(i);

				}
			});
		}
	}

	/**
	 * Performs any final cleanup before activity is destroyed.  .
	 * 
	 */
	

	@Override
	public void onDestroy() {
		if(list != null) {
			list.setAdapter(null);
	    }
	    super.onDestroy();
	    
	    if(checkinAsyncTask != null)
	    {
	    	checkinAsyncTask.cancel(true);
		    if (checkinAsyncTaskDialog!=null && checkinAsyncTaskDialog.isShowing())
		    	checkinAsyncTaskDialog.dismiss();
	    }
	    
		if(venueAsyncTask != null)
		{
			venueAsyncTask.cancel(true);
		    if (venueAsyncTaskDialog!=null && venueAsyncTaskDialog.isShowing())
		    	venueAsyncTaskDialog.dismiss();
		}
	}
	
	
	
	/**
	 * OnBackPressed()
	 * Called when the activity has detected the user's press of the back key.
	 * The default implementation simply finishes the current activity, 
	 * but in our case we override this to go to MapActivity screen.
	 */

	@Override
	public void onBackPressed() {
		Intent start = new Intent(CheckinActivity.this, MapActivity.class);
		startActivity(start);
		finishActivity(0);
	}

	/**
	 * Creates get request on execute. With list of data which needs to be taken
	 * from server. Pre-loader created when executed.
	 * 
	 * 
	 */
	
	private class VenueReviewsAsyncTask extends
			AsyncTask<String, Void, JSONObject> {

		String data;
		private ProgressDialog progress;

		public VenueReviewsAsyncTask(ProgressDialog progress) {
			this.progress = progress;
		}

		@Override
		public void onPreExecute() {
			progress.show();
		}

		@SuppressWarnings("unused")
		protected void onProgressUpdate(Integer... progress) {
			setProgress(progress[0]);
		}

		@Override
		protected JSONObject doInBackground(String... params) {

			data = params[0];

			JSONObject resultJson = HttpRequest.makeGetRequest(SharedPreferencesEditor.getBaseUrl(getApplicationContext()), RETRIEVE_VENUE_REVIEW_URL_EXT, data);

			return resultJson;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			progress.dismiss();
			super.onPostExecute(result);
			
			if (result != null) {

				final List<VenueReview> reviews = new VenueReviewParser()
						.parseJSON(result);

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						showList(reviews);
					}
				});

			}
		}
	}

	/**
	 * Creates list view with custom adapter. To display list of all reviews made in particular venue.
	 * With their profile picture in the right, and comment/voteUp|voteDown button in the left.
	 * 
	 */
	private void showList(List<VenueReview> reviews) {
		ListView list = (ListView) findViewById(R.id.list);
		adapter = new VenueReviewAdapter(this, R.layout.follower_venue_row,
				reviews);
		list.setAdapter(adapter);
	}
	
	/**
	 * Method which sends key and venue id to server.
	 * When check-in button pressed.
	 * Opens reviewActivity after execution.
	 */
	@SuppressWarnings("unchecked")
	private void checkIn() {

		List<NameValuePair> data = new ArrayList<NameValuePair>(2);
		data.add(new BasicNameValuePair(KEY_JSON, kKey));
		data.add(new BasicNameValuePair(VENUE_ID, venueId));

		// make POST call
		checkinAsyncTaskDialog = ProgressDialog.show(CheckinActivity.this, "Please wait", "Loading ...");
		checkinAsyncTask = (CheckinAsyncTask) new CheckinAsyncTask(checkinAsyncTaskDialog).execute(data);
	}
	
	/**
	 * Creates post request on execute. With list of data to send to server.
	 * Pre-loader created when executed.
	 * Review Activity is loaded on post execute.
	 * 
	 */
	
	private class CheckinAsyncTask extends
			AsyncTask<List<NameValuePair>, Void, JSONObject> {
		private ProgressDialog progress;

		public CheckinAsyncTask(ProgressDialog progress) {
			this.progress = progress;
		}

		@Override
		public void onPreExecute() {
			progress.show();
		}

		@SuppressWarnings("unused")
		protected void onProgressUpdate(Integer... progress) {
			setProgress(progress[0]);
		}

		@Override
		protected JSONObject doInBackground(List<NameValuePair>... params) {

			List<NameValuePair> data = params[0];

			
			JSONObject resultJson = HttpRequest.makePostRequest(SharedPreferencesEditor.getBaseUrl(getApplicationContext()), CHECK_IN_URL_EXT, data);

			return resultJson;
		}

		@Override
		protected void onPostExecute(JSONObject result) {

			super.onPostExecute(result);
			progress.dismiss();
			if (result != null) {

				try {

					String success = result.getString(SUCCESS_JSON);

					if (success.equals("true")) {
						
						//launch SearchService
						Intent i = new Intent(getApplicationContext(), SearchService.class);
						i.putExtra(KEY_JSON, kKey);
						i.putExtra(VENUE_LAT, venueLat);
						i.putExtra(VENUE_LNG, venueLng);
						i.putExtra(VENUE_ID, venueId);
						startService(i);
						
						// launch ReviewActivity
						i = new Intent(getApplicationContext(),
								ReviewActivity.class);

						i.putExtra(VENUE_NAME, venueName);
						i.putExtra(VENUE_ID, venueId);
						
						startActivity(i);

					} else {
						
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
	}
	
	
	/**
	 * Creates implicit inflation for use in action bar.
	 * Rendering map_menu layout.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Setting actionbar icons
	 * first profile icon.
	 * second settings icon
	 */
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_profile:
			Intent openProfile = new Intent(getApplicationContext(),
					ProfileActivity.class);
			startActivity(openProfile);
			break;

		case R.id.action_settings:
			Intent openSettings = new Intent(getApplicationContext(),
					SettingsActivity.class);
			startActivity(openSettings);
			break;

		default:
			break;
		}

		return true;
	}
}
