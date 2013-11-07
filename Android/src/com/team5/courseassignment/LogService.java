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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


/**
 * this class logs the position of the user automatically in the background and sends the data to the server (plus: stores it locally) 
 *
 */
public class LogService extends Service implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	
	//for logic of behaviour
	private int kInterval; //in seconds
	private int kLoggingPeriod; //in seconds
	private int kDistance; //in meters
	private LocationRequest kLocationRequest;
	private boolean kLocationRequestsRunning; //are request running currently?
	private Location kLastSentLocation;
	
	//key of user for connecting to the server
	private String kKey;
	private final static String KEY_JSON ="key";
	
	//variables for the POST call
	private final static String PUSH_GEO_URL = "http://switchcodes.in/sandbox/projectpackets/t5/user/geo-push/";
	private final static String REQUEST_KEY = "request";
	private final static String REQUEST_VALUE = "put";
	private final static String LATITUDE = "latitude";
	private final static String LONGITUDE = "longitude";
		
		
	//variables for the POST answer
	private final static String SUCCESS_JSON = "success";
	
	
	//
	private LocationClient kLocationClient;
	private SharedPreferencesEditor kSharedPreferencesEditor;
	
	//id for the notification
	private final static int NOTIFICATION = 99999;
	
	
	/*
	 * ----------------------------------------------------------------------------
	 * methods belonging to Service
	 * ----------------------------------------------------------------------------
	 */
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		
		
		//initialize the variables
		kSharedPreferencesEditor = new SharedPreferencesEditor(this, kKey);
		kLocationClient = new LocationClient(this, this, this);  //GooglePlayServices availability is already checked in MapActivity
		
		kLocationRequestsRunning = false;
		
		
		
		
	}

	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(intent != null) {
			kKey = intent.getStringExtra(KEY_JSON);
			
			showNotification();
			
			
			getSettings();
			
			
			
			
		}
		
		
		return START_REDELIVER_INTENT; 
	}

	@Override
	public void onDestroy() {
		
	}
	
	
	
	
	
	/*
	 * ----------------------------------------------------------------------------
	 * logic methods
	 * ----------------------------------------------------------------------------
	 */
	
	@SuppressWarnings("deprecation")
	private void showNotification() {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = "LogService Started";
		long when = System.currentTimeMillis();
		
		Notification noti = new Notification();
		noti.icon=icon;
		noti.tickerText = tickerText;
		noti.when = when;
		
		
		CharSequence contentTitle = getText(R.string.log_service_running);
		CharSequence contentText = "Press to view more information!";
		Intent notificationIntent = new Intent(this, MapActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent , 0);;
		
		noti.setLatestEventInfo(this, contentTitle, contentText, contentIntent);
		mNotificationManager.notify(NOTIFICATION, noti);
	}
	
	private void getSettings() {
		
		kDistance = kSharedPreferencesEditor.getDistance();
		kInterval = kSharedPreferencesEditor.getInterval();
//		kLocalStorageLimit = kSharedPreferencesEditor.getLocalStorageLimit();
		kLoggingPeriod = kSharedPreferencesEditor.getLoggingPeriod();
	
		if(!kLocationRequestsRunning && kInterval != SharedPreferencesEditor.DEFAULT_VALUE && kLoggingPeriod != SharedPreferencesEditor.DEFAULT_VALUE) {
			
			kLocationRequest = LocationRequest.create();
			kLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
			kLocationRequest.setInterval(1000 * kInterval);
			kLocationRequest.setExpirationDuration(kLoggingPeriod * 1000);
			
			
			kLocationClient.connect();
			
			
		} else {
			//TODO: error handling!
		}
	}

	
	
	
	
	
	/*
	 * ----------------------------------------------------------------------------
	 * methods belonging to GooglePlayServicesClient implementation
	 * ----------------------------------------------------------------------------
	 */

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.log_service_stopped), Toast.LENGTH_LONG).show();
		stopSelf();
		
	}

	@Override
	public void onConnected(Bundle arg) {
		// request Location updates
		
		if(kLocationRequest != null && !kLocationRequestsRunning) {
			kLocationClient.requestLocationUpdates(kLocationRequest, this);
			kLocationRequestsRunning = true;
		}
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onLocationChanged(Location newLocation) {
		// send the data to the server if the distance is high enough. If sending fails, it should be stored later
		if(kLastSentLocation != null && kDistance != SharedPreferencesEditor.DEFAULT_VALUE) {
			double distance = Utilities.calculateDistanceInM(newLocation.getLatitude(), newLocation.getLongitude(), kLastSentLocation.getLatitude(), kLastSentLocation.getLongitude());
			if(distance < kDistance) {
				//this location is not interesting
				return;
			}
		}
		
		//send the new location
		List<NameValuePair> data = new ArrayList<NameValuePair>(4);
		data.add(new BasicNameValuePair(KEY_JSON, kKey));
		data.add(new BasicNameValuePair(REQUEST_KEY, REQUEST_VALUE));
		data.add(new BasicNameValuePair(LATITUDE, Double.valueOf(newLocation.getLatitude()).toString()));
		data.add(new BasicNameValuePair(LONGITUDE, Double.valueOf(newLocation.getLongitude()).toString()));
		
		//make POST call
		new SendGeoAsyncTask().execute(data);
		kLastSentLocation = newLocation;
	}
	
	
	
	
	
	private class SendGeoAsyncTask extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
		
		List<NameValuePair> data;
		
		@Override
		protected JSONObject doInBackground(List<NameValuePair>... params) {
			
			
			data = params[0];
			
			HttpClient client =new DefaultHttpClient();
			HttpPost post = new HttpPost(PUSH_GEO_URL);
			
			HttpResponse response = null;
			
			try {
				
				HttpEntity entity = new UrlEncodedFormEntity(data, "UTF-8");   
				post.setEntity(entity);
				
				
				Log.d("log_service", "post: " + post);
				
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
					
					Log.d("log_service", "response: " + response);
					
					String resultString = EntityUtils.toString(response.getEntity());
					
					Log.d("log_service", "resultString: " + resultString);
					
					resultJson = new JSONObject(resultString);
					
					Log.d("log_service", "resultJson: " + resultJson.toString());
					
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
						//TODO find better solution!
						Thread t = new Thread(new Runnable() {
							
							@SuppressWarnings("unchecked")
							@Override
							public void run() {
								
								if (data != null) {
									//make POST call
									new SendGeoAsyncTask().execute(data);
								}
								
								
							}
						});
						t.start();
						
					} 
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			} 
		}
    	
    }
	
	
	
}
