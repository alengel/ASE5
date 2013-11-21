package com.team5.courseassignment;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class OtherUsersProfileActivity extends Activity implements OnItemClickListener{
	//variables for the POST call
		private static String USER_URL;
		private static String USER_URL_EXT = "user";
	
	//variables for the GET call
		private static String RETRIEVE_USER_URL;
		private final static String RETRIEVE_USER_URL_EXT = "first_name/last_name/user_image";
	
	//key of user for connecting to the server
		private String kKey;
	    private final static String KEY_JSON ="key";
		private final static String SUCCESS_JSON = "success";
		
			
	//user details
	private String firstName;
	private final static String FIRST_NAME = "name";
	private String lastName;
	private final static String LAST_NAME = "last_name";
	private String userImage;
	private final static String USER_IMAGE = "user_image";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Get the key and user details
        kKey = this.getIntent().getStringExtra(KEY_JSON);
    	firstName = this.getIntent().getStringExtra(FIRST_NAME);
    	lastName = this.getIntent().getStringExtra(LAST_NAME);
    	userImage = this.getIntent().getStringExtra(USER_IMAGE);
    	
    	//Get the base url
    	String baseUrl = getResources().getString(R.string.base_url);
    	USER_URL = baseUrl + USER_URL_EXT;
    	RETRIEVE_USER_URL = baseUrl + RETRIEVE_USER_URL_EXT;
    	
    	//Set layout
    	setContentView(R.layout.other_users_profile);
    	
    	//Set User name
    	TextView name = (TextView) findViewById(R.id.textView1);
    	name.setText(firstName);
    	
    	//Set User image
    	ImageView imgView = (ImageView) findViewById(R.id.imgView);
    	imgView.setImage(userImage);
    	    	
    	//Setting up follow button.
    	Button followButton = (Button) findViewById(R.id.other_users_profile_button_Follow);
    	followButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				follow();
			}

			private void follow() {
				// TODO Auto-generated method stub
				
			}
		
		});

    	
   
	ListView listView;
	
        listView = (ListView) findViewById(R.id.reviewList);
        listView.setOnItemClickListener(this);
    }
	//Makes it possible to click on the Review and allows to go to the Review screen once set up
	@Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                  Toast.LENGTH_SHORT).show();
    }
}
	
		
