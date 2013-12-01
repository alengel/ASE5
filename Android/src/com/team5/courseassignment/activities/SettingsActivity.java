package com.team5.courseassignment.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.team5.courseassignment.R;
import com.team5.courseassignment.utilities.HttpRequest;
import com.team5.courseassignment.utilities.SettingsFragment;
import com.team5.courseassignment.utilities.SharedPreferencesEditor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !
 */

public class SettingsActivity extends Activity {

	// key of user for connecting to the server
	private String kKey;
	private final static String KEY_JSON = "key";

	// variables for the POST call
	private static String LOGOUT_URL;
	private static String LOGOUT_URL_EXT = "logout";

	// variables for the POST answer
	private final static String SUCCESS_JSON = "success";

	/**
	 * Called when the activity is first created. This is where we do all of our
	 * normal static set up: create views, bind data to lists, etc. This method
	 * also provides a Bundle containing the activity's previously frozen state,
	 * if there was one.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// get the key
		kKey = SharedPreferencesEditor.getKey();

		// get the base url
		LOGOUT_URL = getResources().getString(R.string.base_url)
				+ LOGOUT_URL_EXT;

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();
	}

	/**
	 * Creates implicit inflation for use in action bar. Rendering settings_menu
	 * layout.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.settings_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Setting logout icon actions.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		List<NameValuePair> data = new ArrayList<NameValuePair>(1);
		data.add(new BasicNameValuePair(KEY_JSON, kKey));

		// make POST call
		ProgressDialog progress = ProgressDialog.show(SettingsActivity.this,
				"Please wait", "Loading ...");
		new LogoutAsyncTask(progress).execute(data);

		return true;
	}

	/**
	 * Creates post request on execute. With list of data to send to server.
	 * Pre-loader created when executed. Redirecting to log in activity screen
	 * on post execute, if and only if success message is "true". It depends on
	 * user input. Corresponding pop up window will appear with corresponding
	 * message, whether its false its error message which we get from server
	 * otherwise its congratulations message taken from string.xml
	 * 
	 */
	private class LogoutAsyncTask extends
			AsyncTask<List<NameValuePair>, Void, JSONObject> {
		private ProgressDialog progress;

		public LogoutAsyncTask(ProgressDialog progress) {
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
		protected JSONObject doInBackground(List<NameValuePair>... params) {

			List<NameValuePair> data = params[0];

			JSONObject resultJson = HttpRequest.makePostRequest(LOGOUT_URL,
					data);

			return resultJson;
		}

		@Override
		protected void onPostExecute(JSONObject result) {

			super.onPostExecute(result);
			progress.dismiss();
			if (result != null) {

				try {

					String success = result.getString(SUCCESS_JSON);

					if (success.equals("true")) {

						Intent i = new Intent(getApplicationContext(),
								LoginActivity.class);
						startActivity(i);

					} else {

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
