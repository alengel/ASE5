package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FollowerProfileActivity extends Activity implements
		OnItemClickListener {
	// variables for the GET call
	private static String RETRIEVE_REVIEWER_PROFILE_URL;
	private final static String RETRIEVE_REVIEWER_PROFILE_URL_EXT = "reviewer-profile/reviewer_id/";

	// variables for POST call
	private static String FOLLOW_URL;
	private final static String FOLLOW_URL_EXT = "follow";
	private final String REVIEWER_ID = "reviewer_id";
	private final String FOLLOW = "follow";
	private Boolean followReviewer;
	private String reviewerId;

	// key of user for connecting to the server
	private String kKey;
	private String KEY_JSON = SharedPreferencesEditor.KEY_JSON;
	public static final String SUCCESS_JSON = "success";

	ListView list;
	FollowerVenueAdapter adapter;
	private CheckBox followButton;

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

		// Get the key and user details
		kKey = SharedPreferencesEditor.getKey();

		// Set the reviewer_Id
		reviewerId = this.getIntent().getStringExtra(REVIEWER_ID);

		// Get the base url and set up Urls
		String baseUrl = getResources().getString(R.string.base_url);
		RETRIEVE_REVIEWER_PROFILE_URL = baseUrl
				+ RETRIEVE_REVIEWER_PROFILE_URL_EXT;
		FOLLOW_URL = baseUrl + FOLLOW_URL_EXT;

		// Set layout
		setContentView(R.layout.follower_profile);

		// Setting up follow button.
		followButton = (CheckBox) findViewById(R.id.reviewerFollowButton);
		followButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (followButton.isChecked()) {
					followReviewer = true;
					follow();
				} else {
					followReviewer = false;
					follow();
				}
			}
		});

		ListView listView;

		listView = (ListView) findViewById(R.id.reviewerVenueList);
		listView.setOnItemClickListener(this);

		// make GET request to retrieve existing user reviews for venue
		String data = reviewerId + "/key/" + kKey;
		ProgressDialog progress = ProgressDialog.show(
				FollowerProfileActivity.this, "Please wait", "Loading ...");

		new ReviewerProfileAsyncTask(progress).execute(data);
	}

	/**
	 * Created for this activity to start interacting with the user. Called
	 * after onRestoreInstanceState(Bundle), onRestart(); or onPause();
	 * 
	 * @param savedInstanceState
	 */
	protected void onResume(Bundle savedInstanceState) {
		super.onResume();

		// Get the key and user details
		kKey = SharedPreferencesEditor.getKey();
	}

	/**
	 * Makes it possible to click on the Review and allows to go to the Review
	 * screen once set up
	 */

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {
		Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * Creates get request on execute. With list of data which needs to be taken
	 * from server. Pre-loader created when executed.
	 */
	private class ReviewerProfileAsyncTask extends
			AsyncTask<String, Void, JSONObject> {

		String data;
		private ProgressDialog progress;

		public ReviewerProfileAsyncTask(ProgressDialog progress) {
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
					RETRIEVE_REVIEWER_PROFILE_URL, data);

			return resultJson;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			progress.dismiss();

			if (result != null) {

				final FollowerProfileInfo reviewer_profile = new FollowerProfileInfoParser()
						.parseJSON(result);
				final List<FollowerProfileVenue> reviewer_profile_venue = new FollowerProfileVenueParser()
						.parseJSON(result);

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (reviewer_profile != null)
							fillProfile(reviewer_profile);

						showList(reviewer_profile_venue);
					}
				});
			}
		}
	}

	/**
	 * Creates list view with custom adapter. To display list of all profile
	 * reviewer venues. With venue picture in the right.
	 * 
	 */
	private void showList(List<FollowerProfileVenue> reviewer_profile_venues) {
		ListView list = (ListView) findViewById(R.id.reviewerVenueList);
		adapter = new FollowerVenueAdapter(this, R.layout.venue_review_row,
				reviewer_profile_venues);
		list.setAdapter(adapter);
	}

	/**
	 * This method fills all text views. With list of data taken from server.
	 * 
	 */
	private void fillProfile(FollowerProfileInfo reviewer_profile) {
		// Set User name
		TextView name = (TextView) findViewById(R.id.reviewerName);
		String firstName = reviewer_profile.getFirstName();
		String lastName = reviewer_profile.getLastName();

		name.setText(firstName + " " + lastName);

	}

	/**
	 * This method sends list of @param data to the server. To make follow
	 * request.
	 */
	@SuppressWarnings("unchecked")
	private void follow() {
		List<NameValuePair> data = new ArrayList<NameValuePair>(3);
		data.add(new BasicNameValuePair(KEY_JSON, kKey));
		data.add(new BasicNameValuePair(REVIEWER_ID, reviewerId));
		data.add(new BasicNameValuePair(FOLLOW, followReviewer.toString()));

		// make POST call
		ProgressDialog progress = ProgressDialog.show(
				FollowerProfileActivity.this, "Please wait", "Loading ...");
		new FollowAsyncTask(progress).execute(data);
	}

	/**
	 * Creates post request on execute. With list of data to send to server.
	 * Pre-loader created when executed. User is followed by you on post
	 * execute.
	 * 
	 */
	private class FollowAsyncTask extends
			AsyncTask<List<NameValuePair>, Void, JSONObject> {
		private ProgressDialog progress;

		public FollowAsyncTask(ProgressDialog progress) {
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

			JSONObject resultJson = HttpRequest.makePostRequest(FOLLOW_URL,
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
						// TODO: Move logic of button changing in here.
					} else {
						// TODO: if the server responds with error, display
						// message
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
