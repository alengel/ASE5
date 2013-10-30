package com.team5.courseassignment;

import com.google.android.gms.location.LocationClient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


/**
 * this class logs the position of the user automatically in the background and sends the data to the server (plus: stores it locally) 
 * @author Pascal
 *
 */
public class LogService extends Service {
	
	private int interval;
	private int logging_period;
	private int distance;
	private int local_storage_limit;
	
	private LocationClient kLocationClient;
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		
	}

	@Override
	public void onDestroy() {
		
	}
	
	

}
