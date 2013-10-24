package com.team5.courseassignment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.team5.courseassignment.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.app.Activity;

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
        	UiSettings settings = map.getUiSettings();
        	settings.setZoomControlsEnabled(false);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
