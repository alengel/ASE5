package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.team5.courseassignment.R;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !!!
 */


public class MapActivity extends Activity implements OnItemClickListener {
	
	private GoogleMap map;
	
	// variables for the GET call to FourSquare API
	private final static String RETRIEVE_VENUE_URL = "https://api.foursquare.com/v2/venues/explore?client_id=K3Y020Y1OPD2PPOMSJVTD3TSBQOK3X4SQR3XR12DD4SH554W&client_secret=KLUIFTDOXY2QK20W0LYKPPY1KBE1PKO1ZLRS45R4CL0Q3NBN&v=20131016&";

	//variables for the POST call
	private final static String KEY_JSON ="key";
	
	//key of user for connecting to the server
	private String kKey;
	
	// Location variables required to get the current location.
	private LocationListener mLocationListener;
	private Location mLastLocation;
	
	private final static String VENUE_NAME ="name";
	private final static String VENUE_ID ="id";
	
	// ArrayList of venues.
	ArrayList<FourSquareVenue> venues = new ArrayList<FourSquareVenue>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == ConnectionResult.SUCCESS) {
        	
        	//get the key
        	kKey = this.getIntent().getStringExtra(KEY_JSON);
        	
        	//set layout
        	setContentView(R.layout.map);
        	
        	map = ( (MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        	map.setMyLocationEnabled(true);
        	UiSettings settings = map.getUiSettings();
        	settings.setZoomControlsEnabled(false);
        	settings.setMyLocationButtonEnabled(false);
        	
        	//Setting up locate me button.
        	Button loginButton = (Button) findViewById(R.id.locate_me_button);
            loginButton.setOnClickListener(new OnClickListener() {
    			public void onClick(View v) {
					onLocateMe();
				}
			});	
        } else {
        	
        	// kill the application and inform the user
        	Toast.makeText(getApplicationContext(),	getResources().getString(R.string.play_services_missing), Toast.LENGTH_LONG).show();
        	finish();
        }
        
        mLocationListener = new LocationListener() {
    	    @Override
    	    public void onLocationChanged(final Location location) {
    	        //your code here
    	    	mLastLocation = location;
    	    	Log.d("location_update", location.toString());
    	    }

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
    	};
    	
    	LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5*1000,
                0, mLocationListener);
        
        mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }
    
	@SuppressLint("DefaultLocale")
	private void onLocateMe()
    {
		// get the new location
		Double latitude = mLastLocation.getLatitude();
		Double longitude = mLastLocation.getLongitude();
		Integer zoom = 15;
    	String data = String.format("ll=%f,%f", latitude, longitude);
		
    	//zoom map into user's current location
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom));
		
		// make POST call to FourSquare API
		new GetFourSquareVenue().execute(data);
    }
	
	private void showList(List<FourSquareVenue> venues)
	{
		ListAdapter adapter = new ArrayAdapter<FourSquareVenue>(this, android.R.layout.simple_list_item_1, venues);
    	ListView list = (ListView) findViewById(R.id.list); 	
    	list.setAdapter(adapter);
    	list.setOnItemClickListener(this);
	}
	

	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

		Intent i = new Intent(getApplicationContext(), CheckinActivity.class);
		
		@SuppressWarnings("unchecked")
		ArrayAdapter<FourSquareVenue> adapter = (ArrayAdapter<FourSquareVenue>) arg0.getAdapter();
		FourSquareVenue venue = adapter.getItem(position);
		
		i.putExtra(KEY_JSON, kKey);
		i.putExtra(VENUE_NAME, venue.name);
		i.putExtra(VENUE_ID, venue.id);
		
		startActivity(i);
    }
   
	private class GetFourSquareVenue extends AsyncTask<String, Void, JSONObject> {
		
    	String data;
    	
		@Override
		protected JSONObject doInBackground(String... params) {
		
			data = params[0];
			
			JSONObject resultJson = HttpRequest.makeGetRequest(RETRIEVE_VENUE_URL, data);
			
			return resultJson;
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
		
			if (result != null) {

				final List<FourSquareVenue> venues = new FourSquareJsonParser().parseJSON(result);
				
				runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                    	showList(venues);
                    }
                });
			}
		}
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
		i.putExtra(KEY_JSON, kKey);
		startActivity(i);
		
		return true;
    }
}
