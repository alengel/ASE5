package com.team5.courseassignment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity implements OnItemClickListener{
	//variables for the GET call
		private static String RETRIEVE_PROFILE_URL;
		private final static String RETRIEVE_PROFILE_URL_EXT = "profile";
		
		@SuppressWarnings("unused")
		private static String SET_PROFILE_URL;
		private final static String SET_PROFILE_URL_EXT = "update";
		
	
	//key of user for connecting to the server
		private String kKey;
	    private final static String KEY_JSON ="key";
	    

	    @SuppressWarnings("unused")
		private String firstNameKey;
	    private final static String FIRSTNAME_KEY = "first_name";
	    
	    @SuppressWarnings("unused")
	    private String lastNameKey;
		private final static String LASTNAME_KEY = "last_name";
		
		@SuppressWarnings("unused")
		private String imageKey;
		private final static String IMAGE = "profile_image";
	    
		ListView list;
		ProfileListAdapter adapter;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //Set layout
    	setContentView(R.layout.user_profile);
    	kKey = this.getIntent().getStringExtra(KEY_JSON);
    	firstNameKey =this.getIntent().getStringExtra(FIRSTNAME_KEY);
    	lastNameKey=this.getIntent().getStringExtra(LASTNAME_KEY);
    	imageKey=this.getIntent().getStringExtra(IMAGE);
    
    	//Get the base url
    	String baseUrl = getResources().getString(R.string.base_url);
    	RETRIEVE_PROFILE_URL = baseUrl + RETRIEVE_PROFILE_URL_EXT;
    	SET_PROFILE_URL = baseUrl + SET_PROFILE_URL_EXT;
    	
    	ProgressDialog progress = ProgressDialog.show(ProfileActivity.this, "Please wait", "Loading ...");
    	String data = "/key/" + kKey ;
		
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
			
			
		    
			//resultJson	= HttpRequest.makeGetRequest(RETRIEVE_PROFILE_URL, data);
			return resultJson;
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			
			super.onPostExecute(result);
			progress.dismiss();
			if (result != null) {
				try {
					final List<ProfileInfo> profile = new ProfileInfoParser().parseJSON(result);
					//final List<FollowerProfileVenue> reviewer_profile_venue = new FollowerProfileVenueParser().parseJSON(result);
					
					runOnUiThread(new Runnable() {

	                    @Override
	                    public void run() {
	                    	fillProfile(profile);
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
	
	 
    @SuppressWarnings("unused")
	private void showList(List<UserFollowers> followers)
	{
    	 ListView list = (ListView) findViewById(R.id.listView1);
    	 adapter = new ProfileListAdapter(this, R.layout.follower_row, followers);
         list.setAdapter(adapter);
	}
	
	private void fillProfile( List<ProfileInfo> profile) 
	{	//Set User name
		String firstName =  profile.get(0).getName();
		String LastName =  profile.get(0).getLastName();
		
    	TextView name = (TextView) findViewById(R.id.name);
    	name.setText(firstName);
    	
    	//Set User last_name
    	TextView lastName = (TextView) findViewById(R.id.lastName);
    	lastName.setText(LastName);
    	
    	//Set User email
    	TextView email = (TextView) findViewById(R.id.email);
    	String Email =  profile.get(0).getEmail();
    	email.setText(Email);
    	
    	//Set User picture
    	ImageView image = (ImageView)findViewById(R.id.profilePicture);
    	byte[] decodedString = Base64.decode(profile.get(0).getProfileImage(),Base64.NO_WRAP);
    	InputStream inputStream  = new ByteArrayInputStream(decodedString);
    	Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
    	
    	if(bitmap != null){
    		image.setImageBitmap(bitmap);
    	}
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
		
		switch (item.getItemId()) {
	    case R.id.action_profile:
	    	Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
			i.putExtra(KEY_JSON, kKey);
			startActivity(i);
			
	      break;
	    case R.id.action_settings:
	    	Intent i1 = new Intent(getApplicationContext(), SettingsActivity.class);
			i1.putExtra(KEY_JSON, kKey);
			startActivity(i1);
			
	      break;

	    default:
	      break;
	    }

	    return true;
    }
}
	
		
