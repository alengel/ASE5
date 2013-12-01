package com.team5.courseassignment.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.team5.courseassignment.R;
import com.team5.courseassignment.adapters.VenueReviewAdapter;
import com.team5.courseassignment.data.VenueReview;
import com.team5.courseassignment.parsers.VenueReviewParser;
import com.team5.courseassignment.utilities.HttpRequest;
import com.team5.courseassignment.utilities.SharedPreferencesEditor;

public class CheckinActivity extends Activity {

	// variables for the POST call
	private static String CHECK_IN_URL;
	private final static String CHECK_IN_URL_EXT = "check-in";

	// variables for the GET call
	private static String RETRIEVE_VENUE_REVIEW_URL;
	private final static String RETRIEVE_VENUE_REVIEW_URL_EXT = "venue/venue_id/";

	// variables for the GET call
	@SuppressWarnings("unused")
	private static String RETRIEVE_VOTES_URL;
	private final static String RETRIEVE_VOTES_URL_EXT = "vote";

	// key of user for connecting to the server
	private String kKey;
	private final static String KEY_JSON = "key";
	private final static String SUCCESS_JSON = "success";

	// venue details
	private String venueName;
	private final static String VENUE_NAME = "name";
	private String venueId;
	private final static String VENUE_ID = "id";

	ListView list;
	VenueReviewAdapter adapter;

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
		// vote = this.getIntent().getStringExtra(TOTAL_UP);

		// Get the base url
		String baseUrl = getResources().getString(R.string.base_url);
		// String baseUrl_location =
		// getResources().getString(R.string.base_url_location);
		CHECK_IN_URL = baseUrl + CHECK_IN_URL_EXT;
		RETRIEVE_VENUE_REVIEW_URL = baseUrl + RETRIEVE_VENUE_REVIEW_URL_EXT;
		RETRIEVE_VOTES_URL = baseUrl + RETRIEVE_VOTES_URL_EXT;

		// Set layout
		setContentView(R.layout.checkin);
		TextView name = (TextView) findViewById(R.id.venueName);
		name.setText(venueName);

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
		ProgressDialog progress = ProgressDialog.show(CheckinActivity.this,
				"Please wait", "Loading ...");

		new VenueReviewsAsyncTask(progress).execute(data);
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

			JSONObject resultJson = HttpRequest.makeGetRequest(
					RETRIEVE_VENUE_REVIEW_URL, data);

			return resultJson;
		}

		@Override
		protected void onPostExecute(JSONObject result) {

			super.onPostExecute(result);
			progress.dismiss();
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
		ProgressDialog progress = ProgressDialog.show(CheckinActivity.this,
				"Please wait", "Loading ...");
		new CheckinAsyncTask(progress).execute(data);
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

			JSONObject resultJson = HttpRequest.makePostRequest(CHECK_IN_URL,
					data);

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

						// launch ReviewActivity
						Intent i = new Intent(getApplicationContext(),
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
