package com.team5.courseassignment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !!!
 */


public class MapActivity extends Activity {
	
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == ConnectionResult.SUCCESS) {
        	
        	//get the key
        	kKey = this.getIntent().getStringExtra(KEY_JSON);
        	
        	//set layout
        	setContentView(R.layout.activity_map);
        	
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
		ListPopupWindow popup = new ListPopupWindow(this);
        popup.setAnchorView(findViewById(R.id.anchor));
        ListAdapter adapter = new ArrayAdapter<FourSquareVenue>(this, android.R.layout.simple_list_item_1, venues);
        popup.setAdapter(adapter);

        popup.show();
	}
    
	private class GetFourSquareVenue extends AsyncTask<String, Void, JSONObject> {
		
    	String data;
    	
		@Override
		protected JSONObject doInBackground(String... params) {
		
			data = params[0];
			
			HttpClient client = new DefaultHttpClient();
			String createUrl = RETRIEVE_VENUE_URL + data;
			HttpGet get = new HttpGet(createUrl);
			HttpResponse response = null;
		
			try {
		
				Log.d("map_activity", "get: " + get);
		
				response = client.execute(get);
		
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			JSONObject resultJson = null;
		
			if (response != null) {
				try {
		
					Log.d("map_activity", "response: " + response);
		
					String resultString = EntityUtils.toString(response
							.getEntity());
		
					Log.d("map_activity", "resultString: " + resultString);
		
					resultJson = new JSONObject(resultString);
		
					Log.d("map_activity", "resultJson: " + resultJson.toString());
		
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return resultJson;
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
		
			if (result != null) {
				try {
					final List<FourSquareVenue> venues = new FourSquareJsonParser().parseJSON(result);
					for(Iterator<FourSquareVenue> i = venues.iterator(); i.hasNext(); ) {
						FourSquareVenue item = i.next();
						Log.d("Venue: ", item.name + " " + item.id);
						
					}
					runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                        	showList(venues);
                        }
                    });
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
