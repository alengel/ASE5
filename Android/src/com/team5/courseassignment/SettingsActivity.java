package com.team5.courseassignment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !!!
 */

//TODO: show licensing info in the Settings!!!
public class SettingsActivity extends Activity {
	
	//key of user for connecting to the server
	private String kKey;
	private final static String KEY_JSON ="key";
	
	//variables for the POST call
	private final static String LOGOUT_URL = "http://switchcodes.in/sandbox/projectpackets/t5/user/logout";
	
	//variables for the POST answer
		private final static String SUCCESS_JSON = "success";
		
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //get the key
    	kKey = this.getIntent().getStringExtra(KEY_JSON);
    	
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();   
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
  //set logout icon actions
  	@SuppressWarnings("unchecked")
	@Override
  	public boolean onOptionsItemSelected(MenuItem item) {
  		
  		List<NameValuePair> data = new ArrayList<NameValuePair>(1);
		data.add(new BasicNameValuePair(KEY_JSON, kKey));
		
  		//make POST call
		new LogoutAsyncTask().execute(data);
  		
  		return true;
      }
  	
private class LogoutAsyncTask extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
		
		List<NameValuePair> data;
	
		@Override
		protected JSONObject doInBackground(List<NameValuePair>... params) {
			
			data = params[0];
			
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(LOGOUT_URL);
			
			HttpResponse response = null;
			
			try {
				
				HttpEntity entity = new UrlEncodedFormEntity(data, "UTF-8");   
				post.setEntity(entity);
				
				
				Log.d("b_logic", "post: " + post);
				
				response = client.execute(post);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			JSONObject resultJson = null;
			
			if(response != null) {
				try {
					
					Log.d("b_logic", "response: " + response);
					
					String resultString = EntityUtils.toString(response.getEntity());
					
					Log.d("b_logic", "resultString: " + resultString);
					
					resultJson = new JSONObject(resultString);
					
					Log.d("b_logic", "resultJson: " + resultJson.toString());
					
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			return resultJson;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			
			super.onPostExecute(result);
			
			if(result != null) {
				
				try {
					
					String success = result.getString(SUCCESS_JSON);
					
					if(success.equals("true")) 
					{
						//launch LoginActivity
						Intent i = new Intent(getApplicationContext(), LoginActivity.class);
						startActivity(i);
						
					} else {
						//TODO: Add toast that server response has failed.
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			} 
		}	
    }
}
