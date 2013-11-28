package com.team5.courseassignment;

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
	private final static String RETRIEVE_REVIEWER_PROFILE_URL_EXT = "reviewer-profile/reviewer_id/20";
	
	//variables for POST call
	private static String FOLLOW_URL;
	private final static String FOLLOW_URL_EXT = "reviewer_id";
	private final static String FOLLOW = "follow";
	private String followReviewer; 
		
	// key of user for connecting to the server
	private String kKey;
	private String KEY_JSON = SharedPreferencesEditor.KEY_JSON;
	public static final String SUCCESS_JSON = null;
		
	//reviewer details
	private String reviewerID;
		

	ListView list;
	FollowerVenueAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the key and user details
		kKey = SharedPreferencesEditor.getKey();

		//Get the base url
        String baseUrl = getResources().getString(R.string.base_url);
        RETRIEVE_REVIEWER_PROFILE_URL = baseUrl + RETRIEVE_REVIEWER_PROFILE_URL_EXT;
        FOLLOW_URL = baseUrl + FOLLOW_URL_EXT;

		// Set layout
		setContentView(R.layout.follower_profile);

		// Setting up follow button.

		final CheckBox followButton = (CheckBox) findViewById(R.id.reviewer_follow_button);
		followButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (followButton.isChecked()) {
	    	    		buttonView.setText("Unfollow");
	    	    		followReviewer = "true";
	    	    		follow();
				} else {
	    	        	buttonView.setText("Follow");
	    	        	followReviewer = "false";
	    	        	follow();
				}
			}
		});

		ListView listView;

		listView = (ListView) findViewById(R.id.reviewerVenueList);
		listView.setOnItemClickListener(this);

		// make GET request to retrieve existing user reviews for venue
		String data = "/key/" + kKey;
		ProgressDialog progress = ProgressDialog.show(
				FollowerProfileActivity.this, "Please wait", "Loading ...");

		new ReviewerProfileAsyncTask(progress).execute(data);
	}

	// Makes it possible to click on the Review and allows to go to the Review
	// screen once set up
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {
		Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
				Toast.LENGTH_SHORT).show();
	}

	private class ReviewerProfileAsyncTask extends
			AsyncTask<String, Void, JSONObject> {

		String data;
		private ProgressDialog progress;

		public ReviewerProfileAsyncTask(ProgressDialog progress) {
			this.progress = progress;
		}

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
				try {
					final List<FollowerProfileInfo> reviewer_profile = new FollowerProfileInfoParser()
							.parseJSON(result);
					final List<FollowerProfileVenue> reviewer_profile_venue = new FollowerProfileVenueParser()
							.parseJSON(result);

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							fillProfile(reviewer_profile);
							showList(reviewer_profile_venue);
						}
					});

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void showList(List<FollowerProfileVenue> reviewer_profile_venues) {
		ListView list = (ListView) findViewById(R.id.reviewerVenueList);
		adapter = new FollowerVenueAdapter(this, R.layout.venue_review_row,
				reviewer_profile_venues);
		list.setAdapter(adapter);
	}

	private void fillProfile(List<FollowerProfileInfo> reviewer_profile) {
		// Set User name
		TextView name = (TextView) findViewById(R.id.reviewer_name);
		String firstName = reviewer_profile.get(0).getFirstName();
		String lastName = reviewer_profile.get(0).getLastName();

		name.setText(firstName + " " + lastName);

	}
	
	 @SuppressWarnings("unchecked")
		private void follow() {
	    	String reviewerID = "20";
	    	List<NameValuePair> data = new ArrayList<NameValuePair>(3);
	    	data.add(new BasicNameValuePair(KEY_JSON, kKey));
	    	data.add(new BasicNameValuePair(FOLLOW_URL_EXT, reviewerID));
	    	data.add(new BasicNameValuePair(FOLLOW, followReviewer));
	    	
	    	//make POST call
			ProgressDialog progress = ProgressDialog.show(FollowerProfileActivity.this, "Please wait", "Loading ...");
			new FollowAsyncTask(progress).execute(data);
	    }
	
	 private class FollowAsyncTask extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
    	private ProgressDialog progress;
    	public FollowAsyncTask(ProgressDialog progress) {
    	    this.progress = progress;
    	  }
    	
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
			
			JSONObject resultJson = HttpRequest.makePostRequest(FOLLOW_URL, data);
			
			return resultJson;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			
			super.onPostExecute(result);
			progress.dismiss();
			if(result != null) {
				
				try {
					
					String success = result.getString(SUCCESS_JSON);
					
					if(success.equals("true")) {
						
						// launch
						Intent i = new Intent(getApplicationContext(), FollowerProfileActivity.class);
						i.putExtra(FOLLOW_URL_EXT, reviewerID);
						i.putExtra(FOLLOW, followReviewer);						
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
