package com.team5.courseassignment.services;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class ReceiveTransitionsIntentService extends IntentService {

	public ReceiveTransitionsIntentService() {
		super("ReceiveTransitionsIntentService");
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Log.i("onHandleIntent", "Geofence enter/leaving recognised...");
		
		
		if (LocationClient.hasError(intent)) {
            // Get the error code with a static method
            int errorCode = LocationClient.getErrorCode(intent);
            // Log the error
            Log.e("ReceiveTransitionsIntentService", "Location Services error: " + Integer.toString(errorCode));
            
		} else {
			int transitionType = LocationClient.getGeofenceTransition(intent);
			
			if ((transitionType == Geofence.GEOFENCE_TRANSITION_EXIT)) {
	             
				//stop the SearchService
				Intent i = new Intent(getApplicationContext(), SearchService.class);
				stopService(i);
	                
	                
	        } else {
	        	// Error handling of invalid transitionType
	        	Log.e("ReceiveTransitionsIntentService", "Geofence transition error: " + Integer.toString(transitionType));
	        }

			
		}
		
		
		return;
	}


}
