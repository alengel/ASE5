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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

//TODO: ensure uniqueness without Singleton!!!
public class SharedPreferencesEditor {
	
	//variables for the POST call
	private final static String GET_SETTINGS_URL = "http://switchcodes.in/sandbox/projectpackets/t5/user/settings/";
	private final static String SET_SETTINGS_URL = ""; //TODO
	private final static String REQUEST_KEY = "request";
	private final static String REQUEST_UPDATE_VALUE = "update";
	private final static String REQUEST_GET_VALUE = ""; //TODO
		
		
	//variables for the POST answer
	private final static String SUCCESS_JSON = "success";
	private final static String KEY_JSON ="key";
	
	
	//variables for logic
	public static final int DEFAULT_VALUE = -1;
	private boolean kWebserverAccess; //determines wheter an Async Task is currently connecting to the server
	
	
	//unique name for the SharedPreferences
	private final static String kSHARED_PREFERENCES_NAME = "DEFAULT_PREFERENCES";
	
	//settings names, must be unique
	private final static String INTERVAL = "interval";
	private final static String LOGGING_PERIOD = "logging_period";
	private final static String DISTANCE = "distance";
	private final static String LOCAL_STORAGE_LIMIT = "local_storage_limit";
	
	
	//store the access to the SharedPreferences
	private SharedPreferences kSharedPreferences;
	
	//key of user for connecting to the Webserver
	private String kKey;
		
		
	/**
	 * IMPORTANT: Ensure that only once instantiated during runtime!!!!!!!!
	 * @param context 
	 * @param key the key that is needed to contact the server (authentication)
	 */
	public SharedPreferencesEditor(Context context, String key) {
		super();
		this.kSharedPreferences = context.getSharedPreferences(kSHARED_PREFERENCES_NAME, 0);
		
		kKey = key;
		
		kWebserverAccess = false;
	}
	
	
	
	public int getInterval() {
		
		int result = kSharedPreferences.getInt(INTERVAL, DEFAULT_VALUE);
		
		if(result == DEFAULT_VALUE) {
			List<NameValuePair> data = new ArrayList<NameValuePair>(2);
			data.add(new BasicNameValuePair(REQUEST_KEY, REQUEST_GET_VALUE));
			data.add(new BasicNameValuePair(KEY_JSON, kKey));
			//TODO: change according to new API!!!
			
			//make POST call in order to get current Settings
			new GetSettingsAsyncTask().execute(data);
		}
		
		return result;
	}
	
	public void setInterval(int newInterval) {
		//TODO
		
		Editor editor = kSharedPreferences.edit();
		editor.putInt(INTERVAL, newInterval);
		editor.commit();
		
		List<NameValuePair> data = new ArrayList<NameValuePair>(2);
		data.add(new BasicNameValuePair(REQUEST_KEY, REQUEST_UPDATE_VALUE));
		data.add(new BasicNameValuePair(KEY_JSON, kKey));
		//TODO: change according to new API!!!
		
		//make POST call
		new SetSettingsAsyncTask().execute(data);
		
		return;
	}
	
	public int getLoggingPeriod() {
		
		int result = kSharedPreferences.getInt(LOGGING_PERIOD, DEFAULT_VALUE);
		
		if(result == DEFAULT_VALUE) {
			List<NameValuePair> data = new ArrayList<NameValuePair>(2);
			data.add(new BasicNameValuePair(REQUEST_KEY, REQUEST_GET_VALUE));
			data.add(new BasicNameValuePair(KEY_JSON, kKey));
			//TODO: change according to new API!!!
			
			//make POST call in order to get current Settings
			new GetSettingsAsyncTask().execute(data);
		}
		
		return result;
	}
	
	public void setLoggingPeriod(int newLoggingPeriod) {
		//TODO
		
		Editor editor = kSharedPreferences.edit();
		editor.putInt(LOGGING_PERIOD, newLoggingPeriod);
		editor.commit();
		
		List<NameValuePair> data = new ArrayList<NameValuePair>(2);
		data.add(new BasicNameValuePair(REQUEST_KEY, REQUEST_UPDATE_VALUE));
		data.add(new BasicNameValuePair(KEY_JSON, kKey));
		//TODO: change according to new API!!!
		
		//make POST call
		new SetSettingsAsyncTask().execute(data);
		
		return;
	}
	
	public int getDistance() {
		
		int result = kSharedPreferences.getInt(DISTANCE, DEFAULT_VALUE);
		
		if(result == DEFAULT_VALUE) {
			List<NameValuePair> data = new ArrayList<NameValuePair>(2);
			data.add(new BasicNameValuePair(REQUEST_KEY, REQUEST_GET_VALUE));
			data.add(new BasicNameValuePair(KEY_JSON, kKey));
			//TODO: change according to new API!!!
			
			//make POST call in order to get current Settings
			new GetSettingsAsyncTask().execute(data);
		}
		
		return result;
	}
	
	public void setDistance(int newDistance) {
		//TODO
		
		Editor editor = kSharedPreferences.edit();
		editor.putInt(DISTANCE, newDistance);
		editor.commit();
		
		List<NameValuePair> data = new ArrayList<NameValuePair>(2);
		data.add(new BasicNameValuePair(REQUEST_KEY, REQUEST_UPDATE_VALUE));
		data.add(new BasicNameValuePair(KEY_JSON, kKey));
		//TODO: change according to new API!!!
		
		//make POST call
		new SetSettingsAsyncTask().execute(data);
		
		return;
	}
	
//	public int getLocalStorageLimit() {
//		
//		int result = kSharedPreferences.getInt(LOCAL_STORAGE_LIMIT, DEFAULT_VALUE);
//		
//		if(result == DEFAULT_VALUE) {
//			List<NameValuePair> data = new ArrayList<NameValuePair>(2);
//			data.add(new BasicNameValuePair(REQUEST_KEY, REQUEST_GET_VALUE));
//			data.add(new BasicNameValuePair(KEY_JSON, kKey));
//			//TODO: change according to new API!!!
//			
//			//make POST call in order to get current Settings
//			new GetSettingsAsyncTask().execute(data);
//		}
//		
//		return result;
//	}
//	
//	public void setLocalStorageLimit(int newLocalStorageLimit) {
//		//TODO
//		
//		Editor editor = kSharedPreferences.edit();
//		editor.putInt(LOCAL_STORAGE_LIMIT, newLocalStorageLimit);
//		editor.commit();
//		
//		List<NameValuePair> data = new ArrayList<NameValuePair>(2);
//		data.add(new BasicNameValuePair(REQUEST_KEY, REQUEST_UPDATE_VALUE));
//		data.add(new BasicNameValuePair(KEY_JSON, kKey));
//		//TODO: change according to new API!!!
//		
//		//make POST call
//		new SetSettingsAsyncTask().execute(data);
//		
//		return;
//	}
	
	
	
	
	
	
	
	private class GetSettingsAsyncTask extends AsyncTask<List<NameValuePair>, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(List<NameValuePair>... params) {
			
			
			List<NameValuePair> data = params[0];
			
			HttpClient client =new DefaultHttpClient();
			HttpPost post = new HttpPost(GET_SETTINGS_URL);
			
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
					
					if(success.equals("true")) {
						
						//TODO: extract new settings + set them!
						
					} else {
						
						//TODO
						
					} 
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			} 
		}	
		

    	
    }
	
	
	
	
	
	private class SetSettingsAsyncTask extends AsyncTask<List<NameValuePair>, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(List<NameValuePair>... params) {
			
			
			List<NameValuePair> data = params[0];
			
			HttpClient client =new DefaultHttpClient();
			HttpPost post = new HttpPost(SET_SETTINGS_URL);
			
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
					
					if(success.equals("true")) {
						
						//TODO
						
					} else {
						
						//TODO
						
					} 
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			} 
		}	
		

    	
    }
	
}