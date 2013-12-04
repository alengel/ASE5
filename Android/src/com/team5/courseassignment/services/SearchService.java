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
import com.team5.courseassignment.activities.ProfileActivity;
import com.team5.courseassignment.utilities.SharedPreferencesEditor;

import android.R;
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
 * @author Pascal
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
	private final static String PUSH_GEO_URL = "http://switchcodes.in/sandbox/projectpackets/t5/user/geo-push/";
	private final static String REQUEST_KEY = "request";
	private final static String REQUEST_VALUE = "put";
	private final static String LATITUDE = "latitude";
	private final static String LONGITUDE = "longitude";
	
	private double venueLat;
	private final static String VENUE_LAT = "lat";
	private double venueLng;
	private final static String VENUE_LNG = "lng";
		
		
	//variables for the POST answer
	private final static String SUCCESS_JSON = "success";
	
	
	
	private SharedPreferencesEditor kSharedPreferencesEditor;
	
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
			kSharedPreferencesEditor = new SharedPreferencesEditor(this, kKey);
			
			showNotification();
			
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
	
	private void showNotification() {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		int icon = R.drawable.ic_dialog_alert;  //TODO: wrong!
		CharSequence tickerText = "SearchService Started";
		long when = System.currentTimeMillis();
		
		Notification noti = new Notification();
		noti.icon=icon;
		noti.tickerText = tickerText;
		noti.when = when;
		
		
		CharSequence contentTitle = getText(R.string.cancel); //TODO: wrong!
		CharSequence contentText = "Press to view more information!";
		Intent notificationIntent = new Intent(this, ProfileActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent , 0);;
		
		noti.setLatestEventInfo(this, contentTitle, contentText, contentIntent);
		mNotificationManager.notify(NOTIFICATION, noti);
	}

	
	
	
	
	
	
	
	
	/*
	 * ----------------------------------------------------------------------------
	 * methods beloging to Geofence implementation
	 * ----------------------------------------------------------------------------
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
	
	@Override
	public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) {
		
		if (! (LocationStatusCodes.SUCCESS == statusCode)) {
            
			stopSelf(); //TODO: find better solution
        } 
        
		// Turn off the in progress flag and disconnect the client
        kLocationRequestsRunning = false;
        kLocationClient.disconnect();

		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		
		kLocationRequestsRunning = false;
		if(kNumberOfTrials < 5) {
			addGeofence();
		}
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		
		//we are conencted to the LocationService. Therefore, we can create the GeoFence
		//create the GeoFence
		Geofence.Builder mGeofenceBuilder = new Geofence.Builder();
		mGeofenceBuilder.setCircularRegion(venueLat, venueLng, GEOFENCE_RADIUS_IN_M).setExpirationDuration(GEOFENCE_EXPIRATION_TIME).setRequestId("1").setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT);
		Geofence myFence = mGeofenceBuilder.build();
		List<Geofence> myFences = new ArrayList<Geofence>();
		myFences.add(myFence);
		
		//add it to the LocationClient
		kLocationClient.addGeofences(myFences, getTransitionPendingIntent(), this);
		
	}
	
	private PendingIntent getTransitionPendingIntent() {
		Intent intent = new Intent(this, ReceiveTransitionsIntentService.class);         
		//Return the PendingIntent            
		return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	@Override
	public void onDisconnected() {
		
		kLocationClient = null;
		kLocationRequestsRunning = false;
	}
	
	
	
	
	
	
	
	
	
	
	
}