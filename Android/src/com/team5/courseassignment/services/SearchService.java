package com.team5.courseassignment.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;
import com.team5.courseassignment.activities.FollowerProfileActivity;
import com.team5.courseassignment.activities.ProfileActivity;
import com.team5.courseassignment.data.FollowerProfileInfo;
import com.team5.courseassignment.data.FourSquareVenue;
import com.team5.courseassignment.parsers.FollowerProfileInfoParser;
import com.team5.courseassignment.parsers.FourSquareJsonParser;
import com.team5.courseassignment.utilities.HttpRequest;
import com.team5.courseassignment.utilities.SharedPreferencesEditor;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


/**
 * this class checks periodically if there are other users nearby as long as the user in the area of the venue (checked by GeoFence, if the user has left the venue, the Service will automatically be stopped) 
 * 
 *
 */
public class SearchService extends Service implements ConnectionCallbacks, OnConnectionFailedListener, OnAddGeofencesResultListener  {
	
	//for logic of behaviour
	private int kInterval; //in seconds
	private LocationClient kLocationClient;
	private boolean kLocationRequestsRunning; //are request running currently?
	private static int GEOFENCE_EXPIRATION_TIME = 12 * 60 * 60 * 1000;
	private final static float GEOFENCE_RADIUS_IN_M = 700;
	private static int SLEEP_TIME = 5000;
	int kNumberOfTrials;
	
	//key of user for connecting to the Webserver
	private String kKey;
	private final static String KEY_JSON ="key";
	
	//variables for the POST call
	private final static String RETRIEVE_USERS_URL = "http://switchcodes.in/sandbox/projectpackets/t5/user/find-user"; 
	private final String REVIEWER_ID = "reviewer_id";
	
	private String venueId;
	private final static String VENUE_ID = "id";
	private double venueLat;
	private final static String VENUE_LAT = "lat";
	private double venueLng;
	private final static String VENUE_LNG = "lng";
		
	
	//id for the notification
	private final static int NOTIFICATION = 99999;
	
	
	/*
	 * ----------------------------------------------------------------------------
	 * methods beloging to Service
	 * ----------------------------------------------------------------------------
	 */
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		
		//initialize the variables
		kLocationRequestsRunning = false;
		kNumberOfTrials = 0;
				
		
	}

	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(intent != null) {
			
			kKey = intent.getStringExtra(KEY_JSON);
			venueLat = intent.getDoubleExtra(VENUE_LAT, 0);
			venueLng = intent.getDoubleExtra(VENUE_LNG, 0);
			venueId = intent.getStringExtra(VENUE_ID);
			
			addGeofence();
			
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
	/**
	 * show a notification which gives the user the possibility to view the Profile of the user that has just arrived at the venue
	 * @param notificationIntent
	 */
	@SuppressWarnings("deprecation")
	private void showNotification(Intent notificationIntent) {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		mNotificationManager.cancel(NOTIFICATION);
		
		int icon = R.drawable.ic_dialog_alert;  //TODO: wrong!
		CharSequence tickerText = "A new user has just arrived here!";
		long when = System.currentTimeMillis();
		
		Notification noti = new Notification();
		noti.icon=icon;
		noti.tickerText = tickerText;
		noti.when = when;
		
		CharSequence contentTitle = "A new user has just arrived here!"; 
		CharSequence contentText = "Press to view details about the user!";
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent , 0);
		
		noti.setLatestEventInfo(this, contentTitle, contentText, contentIntent);
		mNotificationManager.notify(NOTIFICATION, noti);
	}

	/**
	 * search periodically for arriving users nearby
	 */
	private void startSearching() {
		
		final Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				//periodically check for users nearby
				String data = String.format("ll=%s,%s%s", kKey, venueId, System.currentTimeMillis());
				new GetNearbyUsers().execute(data);
				
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		});
		t.start();
		
	}
	
	
	
	
	
	
	
	/*
	 * ----------------------------------------------------------------------------
	 * methods beloging to Geofence implementation
	 * ----------------------------------------------------------------------------
	 */
	/**
	 * we want to create a geofence around us so that the service is stopped automatically when the user has left the venue
	 */
	private void addGeofence() {
		
		kLocationClient = new LocationClient(this, this, this);
        // If a request is not already underway
        if (!kLocationRequestsRunning) {
            
        	// Indicate that a request is underway
            kLocationRequestsRunning = true;
            // Request a connection from the client to Location Services
            kLocationClient.connect();
            
        } else {
        	
        	kLocationClient.disconnect();
        }

		
	}
	
	/**
	 * called when a GeoFence result arrived
	 */
	@Override
	public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) {
		
		if (! (LocationStatusCodes.SUCCESS == statusCode)) {
            
			stopSelf(); //TODO: find better solution
        } else {
        	
        	//start the periodically checking for nearby users
        	startSearching();
        }
        
		// Turn off the in progress flag and disconnect the client
        kLocationRequestsRunning = false;
        kLocationClient.disconnect();

		
	}

	/**
	 * called when unable to connect to LocationServices
	 */
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		
		kLocationRequestsRunning = false;
		if(kNumberOfTrials < 5) {
			addGeofence();
		}
		
	}

	/**
	 * called when connect with LocationServices
	 */
	@Override
	public void onConnected(Bundle arg0) {
		
		//we are connected to the LocationService. Therefore, we can create the GeoFence
		//create the GeoFence
		Geofence.Builder mGeofenceBuilder = new Geofence.Builder();
		mGeofenceBuilder.setCircularRegion(venueLat, venueLng, GEOFENCE_RADIUS_IN_M).setExpirationDuration(GEOFENCE_EXPIRATION_TIME).setRequestId("1").setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT);
		Geofence myFence = mGeofenceBuilder.build();
		List<Geofence> myFences = new ArrayList<Geofence>();
		myFences.add(myFence);
		
		//add it to the LocationClient
		kLocationClient.addGeofences(myFences, getTransitionPendingIntent(), this);
		
	}
	
	/**
	 * get the required PendingIntent for the GeoFence creation
	 * @return
	 */
	private PendingIntent getTransitionPendingIntent() {
		Intent intent = new Intent(this, ReceiveTransitionsIntentService.class);         
		//Return the PendingIntent            
		return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	/**
	 * called when disconnected from LocationServices
	 */
	@Override
	public void onDisconnected() {
		
		kLocationClient = null;
		kLocationRequestsRunning = false;
	}
	
	
	
	/**
	 * get the latest arrived users from the server
	 * @author Pascal
	 *
	 */
	private class GetNearbyUsers extends
			AsyncTask<String, Void, JSONObject> {

		String data;

		public GetNearbyUsers() {
			
		}

		@Override
		protected JSONObject doInBackground(String... params) {

			data = params[0];

			JSONObject resultJson = HttpRequest.makeGetRequest(
					RETRIEVE_USERS_URL, data);

			return resultJson;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);

			if (result != null) {

				if (result.has("data")) {

					String userId;
					try {
						
						JSONArray users = result
								.getJSONArray("data");

						if (users != null) {
							userId = users.getString(0);
							// launch ProfilePageActivity
							Intent openProfile = new Intent(
									getApplicationContext(),
									FollowerProfileActivity.class);
							openProfile.putExtra(
									SharedPreferencesEditor.KEY_JSON, kKey);
							openProfile.putExtra(REVIEWER_ID, userId);
							showNotification(openProfile);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}

		}
	}
	
	
	
	
	
	
	
	
}