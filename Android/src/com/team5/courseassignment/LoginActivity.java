package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !!!
 */

public class LoginActivity extends Activity {

	// variables for the POST call
	private static String LOGIN_URL;
	private static String LOGIN_URL_EXT = "login";
	private final static String EMAIL_KEY = "email";
	private final static String PASSWORD_KEY = "passwd";

	// variables for the POST answer
	private final static String SUCCESS_JSON = "success";
	private final static String KEY_JSON = "key";

	/**
	 * Called when the activity is first created. This is where we do all of our
	 * normal static set up: create views, bind data to lists, etc. This method
	 * also provides a Bundle containing the activity's previously frozen state,
	 * if there was one.
	 *
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set layout
		setContentView(R.layout.login);
		LOGIN_URL = getResources().getString(R.string.base_url) + LOGIN_URL_EXT;

		// set Button actions
		TextView newToAppTextView = (TextView) findViewById(R.id.register_here_login);
		newToAppTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(),
						RegistrationActivity.class);
				startActivity(i);

			}
		});

		Button loginButton = (Button) findViewById(R.id.login_button_login);
		loginButton.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				// get data for call
				String email = ((EditText) findViewById(R.id.email_box_login))
						.getEditableText().toString();
				String password = ((EditText) findViewById(R.id.password_box_login))
						.getEditableText().toString();

	

				String encryptedPassword = Utilities.encryptString(password);

				List<NameValuePair> data = new ArrayList<NameValuePair>(2);
				data.add(new BasicNameValuePair(EMAIL_KEY, email));
				data.add(new BasicNameValuePair(PASSWORD_KEY, encryptedPassword));

				// make POST call

				ProgressDialog progress = ProgressDialog.show(
						LoginActivity.this, "Please wait", "Loading ...");

				new LoginAsyncTask(progress).execute(data);
			}
		});

		TextView forgottenPassword = (TextView) findViewById(R.id.forgotten_password_login);
		forgottenPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(),
						ForgottenPasswordActivity.class);
				startActivity(i);

			}
		});
	}

	/**
	*Called when a key was pressed down and not handled by any of the views inside of the activity.
	*
	*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Displays invalid input message as a pop up window. When user incorrectly
	 * types to the Edit text field.
	 * 
	 * @param message
	 *            - String errorMessage - error message.
	 */
	private void showInvalidInput(String message) {

		Log.d("login", "Login.showInvalidInput() with argument: " + message);

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(getResources().getString(R.string.errorMessage));
		alert.setMessage(message);
		alert.setPositiveButton(getResources().getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO: update the api to handle the wrong
						// password|email.

					}
				});
		alert.show();
	}
	
	/**
	 * Creates post request on execute. With list of data to send to server.
	 * Pre-loader created when executed.
	 * Map Activity is loaded on post execute.
	 * If Json result success == true
	 */
	private class LoginAsyncTask extends
			AsyncTask<List<NameValuePair>, Void, JSONObject> {

		private ProgressDialog progress;

		public LoginAsyncTask(ProgressDialog progress) {
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

			JSONObject resultJson = HttpRequest
					.makePostRequest(LOGIN_URL, data);

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

						// TODO: get key, store it
						String key = result.getString(KEY_JSON);
						if (key == null || key.isEmpty()) {
							showInvalidInput(getResources().getString(
									R.string.key_not_retrieved));
						}

						SharedPreferencesEditor.key = key;

						// launch LoginActivity
						Intent i = new Intent(getApplicationContext(),
								MapActivity.class);
						startActivity(i);

					} else {
						showInvalidInput(getResources().getString(
								R.string.invalid_input_generic));
					} 

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
