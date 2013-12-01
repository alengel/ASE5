package com.team5.courseassignment.activities;

import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.team5.courseassignment.FourSquareVenue;
import com.team5.courseassignment.R;
import com.team5.courseassignment.adapters.MapListViewAdapter;
import com.team5.courseassignment.parsers.FourSquareJsonParser;
import com.team5.courseassignment.utilities.HttpRequest;
import com.team5.courseassignment.utilities.SharedPreferencesEditor;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !!!
 */

public class MapActivity extends Activity implements OnItemClickListener {

	private GoogleMap map;

	// variables for the GET call to FourSquare API
	private final static String RETRIEVE_VENUE_URL = "https://api.foursquare.com/v2/venues/explore?client_id=K3Y020Y1OPD2PPOMSJVTD3TSBQOK3X4SQR3XR12DD4SH554W&client_secret=KLUIFTDOXY2QK20W0LYKPPY1KBE1PKO1ZLRS45R4CL0Q3NBN&v=20131016&";

	// variables for the POST call
	private final static String KEY_JSON = "key";

	// key of user for connecting to the server
	private String kKey;

	// Location variables required to get the current location.
	private LocationListener mLocationListener;
	private Location mLastLocation;

	private final static String VENUE_NAME = "name";
	private final static String VENUE_ID = "id";

	MapListViewAdapter adapter;

	/**
	 * Called when the activity is first created. This is where we do all of our
	 * normal static set up: create views, bind data to lists, etc. This method
	 * also provides a Bundle containing the activity's previously frozen state,
	 * if there was one.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext()) == ConnectionResult.SUCCESS) {

			// get the key
			kKey = SharedPreferencesEditor.getKey();

			// set layout
			setContentView(R.layout.map);

			map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			map.setMyLocationEnabled(true);
			UiSettings settings = map.getUiSettings();
			settings.setZoomControlsEnabled(false);
			settings.setMyLocationButtonEnabled(false);

			// Setting up locate me button.
			Button loginButton = (Button) findViewById(R.id.locateMeButton);
			loginButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					TextView tv = (TextView) MapActivity.this
							.findViewById(R.id.hint);
					tv.setVisibility(View.INVISIBLE);
					onLocateMe();
				}
			});
		} else {

			// close the application and inform the user
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.play_services_missing),
					Toast.LENGTH_LONG).show();
			finish();
		}

		mLocationListener = new LocationListener() {
			@Override
			public void onLocationChanged(final Location location) {

				mLastLocation = location;
				Log.d("location_update", location.toString());
			}

			@Override
			public void onProviderDisabled(String provider) {

			}

			@Override
			public void onProviderEnabled(String provider) {

			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {

			}
		};

		LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		mLocationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 5 * 1000, 0,
				mLocationListener);

		mLastLocation = mLocationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	}

	/**
	 * Called when the activity has detected the user's press of the back key.
	 * The default implementation simply finishes the current activity, but in
	 * our case we override this to go to MapActivity screen.
	 */
	@Override
	public void onBackPressed() {
		this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
				KeyEvent.KEYCODE_BACK));
	}

	/**
	 * When LocateMe button pressed... Method which gets location, zooms map
	 * into current position. and executes fourSquare api call for nearest
	 * venue/location as a list. Stays at MapActivity after execution.
	 */
	@SuppressLint("DefaultLocale")
	private void onLocateMe() {
		// get the new location
		Double latitude = mLastLocation.getLatitude();
		Double longitude = mLastLocation.getLongitude();
		Integer zoom = 15;
		String data = String.format("ll=%f,%f", latitude, longitude);

		// zoom map into user's current location
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
				longitude), zoom));

		// make POST call to FourSquare API
		ProgressDialog progress = ProgressDialog.show(MapActivity.this,
				"Please wait", "Loading ...");
		new GetFourSquareVenue(progress).execute(data);
	}

	/**
	 * Creates list view with Array adapter. To display list of all nearest
	 * venues/locations around you.
	 */
	private void showList(List<FourSquareVenue> venues) {
		ListAdapter adapter = new ArrayAdapter<FourSquareVenue>(this,
				R.layout.venue_list_item, venues);
		ListView list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
	}

	/**
	 * On list item click - By passing venue name and venue id to foursquare
	 * server CheckinActivity executed.
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		Intent i = new Intent(getApplicationContext(), CheckinActivity.class);

		@SuppressWarnings("unchecked")
		ArrayAdapter<FourSquareVenue> adapter = (ArrayAdapter<FourSquareVenue>) arg0
				.getAdapter();
		FourSquareVenue venue = adapter.getItem(position);

		i.putExtra(VENUE_NAME, venue.name);
		i.putExtra(VENUE_ID, venue.id);

		startActivity(i);
	}

	/**
	 * Creates get request on execute. With list of venues/locations which needs
	 * to be taken - - from server. pre-loader created when pre-executed.
	 */
	private class GetFourSquareVenue extends
			AsyncTask<String, Void, JSONObject> {

		String data;
		private ProgressDialog progress;

		public GetFourSquareVenue(ProgressDialog progress) {
			this.progress = progress;
		}

		@Override
		public void onPreExecute() {
			progress.show();
		}

		@SuppressWarnings("unused")
		protected void onProgressUpdate(Integer... progress) {
			setProgress(progress[0]);
		}

		@Override
		protected JSONObject doInBackground(String... params) {

			data = params[0];

			JSONObject resultJson = HttpRequest.makeGetRequest(
					RETRIEVE_VENUE_URL, data);

			return resultJson;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			progress.dismiss();

			if (result != null) {

				final List<FourSquareVenue> venues = new FourSquareJsonParser()
						.parseJSON(result);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						showList(venues);
					}
				});
			}
		}
	}

	/**
	 * Creates implicit inflation for use in action bar. Rendering map_menu
	 * layout.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Setting actionBar icons. first - profile icon. second - settings icon.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_profile:
			Intent openProfile = new Intent(getApplicationContext(),
					ProfileActivity.class);
			openProfile.putExtra(KEY_JSON, kKey);
			startActivity(openProfile);
			return true;

		case R.id.action_settings:
			Intent openSettings = new Intent(getApplicationContext(),
					SettingsActivity.class);
			openSettings.putExtra(KEY_JSON, kKey);
			startActivity(openSettings);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}
}
