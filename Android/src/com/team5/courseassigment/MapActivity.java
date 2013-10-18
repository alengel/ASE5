package com.team5.courseassigment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !!!
 */


public class MapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == ConnectionResult.SUCCESS) {
        	
        	setContentView(R.layout.activity_map);
        	
        	GoogleMap map = ( (MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        	map.setMyLocationEnabled(true);
        	

        }
        
    }


    
    
}
