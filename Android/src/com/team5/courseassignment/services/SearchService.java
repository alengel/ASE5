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
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
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
public class SearchService extends Service  {
	
	//for logic of behaviour
	private int kInterval; //in seconds
	private int kLoggingPeriod; //in seconds
	private int kDistance; //in meters
	private int kLocalStorageLimit; //in MB (MeBiByte)
	private int startTime; //time when the service started logging
	private LocationRequest kLocationRequest;
	private boolean kLocationRequestsRunning; //are request running currently?
	private Location kLastSentLocation;
	private static int GEOFENCE_EXPIRATION_TIME = 12 * 60 * 60 * 1000;
	
	//key of user for connecting to the Webserver
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
		
//		kLocationClient = new LocationClient(this, this, this);  //GooglePlayServices availability is already checked in MapActivity
		
//		kLocationRequestsRunning = false;
		
		
		
		
	}

	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(intent != null) {
			
			kKey = intent.getStringExtra(KEY_JSON);
			kSharedPreferencesEditor = new SharedPreferencesEditor(this, kKey);
			
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
	
	private void showNotification() {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		int icon = R.drawable.alert_dark_frame;  //TODO: wrong!
		CharSequence tickerText = "LogService Started";
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
	 * methods beloging to Geofence implementation
	 * ----------------------------------------------------------------------------
	 */

	
	
	
	
	
	
	
	
	
	
	
	
}