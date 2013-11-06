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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !!!
 */



public class MapActivity extends Activity {
	
	//variables for the POST call
	private final static String GEO_PUSH_URL = "http://switchcodes.in/sandbox/projectpackets/t5/user/geo-push/";
	private final static String KEY_JSON ="key";
	private final static String REQUEST_KEY = "request";
	private final static String REQUEST_VALUE = "put";
	private final static String LATITUDE_KEY = "latitude";
	private final static String LONGITUDE_KEY = "longitude";
	
	
	//key of user for connecting to the Webserver
	private String kKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == ConnectionResult.SUCCESS) {
        	
        	
        	
        	//get the key
        	kKey = this.getIntent().getStringExtra(KEY_JSON);
        	
        	//set layout
        	setContentView(R.layout.activity_map);
        	
        	GoogleMap map = ( (MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        	map.setMyLocationEnabled(true);
        	UiSettings settings = map.getUiSettings();
        	settings.setZoomControlsEnabled(false);
        	
        	//TODO: user should enable as many GPS, WLAN etc as possible
        	
        	
        	
        } else {
        	
        	// kill the application and inform the user
        	Toast.makeText(getApplicationContext(),	getResources().getString(R.string.play_services_missing), Toast.LENGTH_LONG).show();
        	finish();
        }
    }
    
    
    
    @Override
	protected void onResume() {
		
		super.onResume();
		
		// service must be started
		Intent i = new Intent(getApplicationContext(), LogService.class);
		i.putExtra(KEY_JSON, kKey);
		startService(i);
		//TODO: call onStartCommand, what to do if already created?
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
	
	//set settings icon actions
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		startActivity(i);
		
		return true;
    } 
}
