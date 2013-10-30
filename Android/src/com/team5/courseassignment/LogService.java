package com.team5.courseassignment;

import java.util.Calendar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;


/**
 * this class logs the position of the user automatically in the background and sends the data to the server (plus: stores it locally) 
 * @author Pascal
 *
 */
public class LogService extends Service implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
	
	//for logic of behaviour
	private int kInterval;
	private int kLoggingPeriod;
	private int kDistance;
	private int kLocalStorageLimit;
	private int startTime; //time when the service started logging
	
	//key of user for connecting to the Webserver
	private String kKey;
	private final static String KEY_JSON ="key";
	
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
		kSharedPreferencesEditor = new SharedPreferencesEditor(this, kKey);
		kLocationClient = new LocationClient(this, this, this);  //GooglePlayServices availability is already checked in MapActivity
		
		
		
		
		
		
	}

	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(intent != null) {
			kKey = intent.getStringExtra(KEY_JSON);
			
			getSettings();
			
			showNotification();
			
			kLocationClient.connect();
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
	
	void getSettings() {
		
		kDistance = kSharedPreferencesEditor.getDistance();
		kInterval = kSharedPreferencesEditor.getInterval();
		kLocalStorageLimit = kSharedPreferencesEditor.getLocalStorageLimit();
		kLoggingPeriod = kSharedPreferencesEditor.getLoggingPeriod();
		
	}

	
	
	
	
	
	/*
	 * ----------------------------------------------------------------------------
	 * methods beloging to GooglePlayServicesClient implementation
	 * ----------------------------------------------------------------------------
	 */

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.log_service_stopped), Toast.LENGTH_LONG).show();
		stopSelf();
		
	}

	@Override
	public void onConnected(Bundle arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	

}
