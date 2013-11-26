package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity implements OnItemClickListener{
	//variables for the GET call
		private static String RETRIEVE_PROFILE_URL;
		private final static String RETRIEVE_PROFILE_URL_EXT = "user/userID";
	
	//key of user for connecting to the server
		private String kKey;
	    private final static String KEY_JSON ="key";
		
	    private String name;
	    private final static String NAME ="name";
	    
	    private String lastName;
	    private final static String LAST_NAME ="lastName";
	    
	    private String email;
	    private final static String EMAIL ="email";
	    
	    private String editName;
	    private final static String EDIT_NAME ="editName";
	    
	    private String editLastName;
	    private final static String EDIT_LAST_NAME ="editLastName";
	    
	    
		ListView list;
		//ProfileVenueAdapter adapter;
		
		
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Get the key and user details
        kKey = this.getIntent().getStringExtra(KEY_JSON);
        name = this.getIntent().getStringExtra(NAME);
        lastName =  this.getIntent().getStringExtra(LAST_NAME);
        email =  this.getIntent().getStringExtra(EMAIL);
        editName = this.getIntent().getStringExtra(NAME);
        editLastName = this.getIntent().getStringExtra(LAST_NAME);
    	
    	//Get the base url
    	String baseUrl = getResources().getString(R.string.base_url);
    	RETRIEVE_PROFILE_URL = baseUrl + RETRIEVE_PROFILE_URL_EXT;
    	
    	//Set layout
    	setContentView(R.layout.user_profile);
    	
  
    	
    	EditText editName = (EditText)findViewById(R.id.editName);
    	editName.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
    	
    	/*List View for future
    	ListView listView;
        listView = (ListView) findViewById(R.id.reviewerVenueList);
        listView.setOnItemClickListener(this);
        
    	*/
      //make GET request to retrieve existing user reviews for venue
    	String data = "/key/" + kKey;
    	ProgressDialog progress = ProgressDialog.show(ProfileActivity.this, "Please wait", "Loading ...");
    	
    	new ProfileAsyncTask(progress).execute(data);
    	
    	
    }
	
	//Makes it possible to click on the Review and allows to go to the Review screen once set up
	@Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                  Toast.LENGTH_SHORT).show();
    }
	
	private class ProfileAsyncTask extends AsyncTask<String, Void, JSONObject> {
		
		String data;
		private ProgressDialog progress;
		public ProfileAsyncTask(ProgressDialog progress) {
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
			
			
			JSONObject resultJson = HttpRequest.makeGetRequest(RETRIEVE_PROFILE_URL, data);
			
			return resultJson;
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			
			super.onPostExecute(result);
			progress.dismiss();
			if (result != null) {
				try {
					final List<ProfileInfo> user_profile = new ProfileInfoParser().parseJSON(result);
					//final List<FollowerProfileVenue> reviewer_profile_venue = new FollowerProfileVenueParser().parseJSON(result);
					
					runOnUiThread(new Runnable() {

	                    @Override
	                    public void run() {
	                    	fillProfile(user_profile);
	                    	//showList(reviewer_profile_venue);
	                    }
	                });
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * 
	 * private void showList(List<FollowerProfileVenue> reviewer_profile_venues)
	{
    	 ListView list = (ListView) findViewById(R.id.reviewerVenueList);
    	 adapter = new FollowerVenueAdapter(this, R.layout.venue_review_row, reviewer_profile_venues);
         list.setAdapter(adapter);
	}
	*/
	private void fillProfile(List<ProfileInfo> user_profile) 
	{
		//Set User name
    	TextView name = (TextView) findViewById(R.id.name);
    	String firstName = user_profile.get(0).getName();
    	String lastName = user_profile.get(0).getLastName();
    	String email = user_profile.get(0).getEmail();
    	
    	name.setText(firstName + " " + lastName+ " " + email);
	}
}
	
		

