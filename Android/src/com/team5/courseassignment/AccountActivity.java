package com.team5.courseassignment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AccountActivity extends Activity implements OnItemClickListener {
	// variables for the GET call
	private static String RETRIEVE_PROFILE_URL;
	private final static String RETRIEVE_PROFILE_URL_EXT = "profile";

	private static String SET_PROFILE_URL;
	private final static String SET_PROFILE_URL_EXT = "update";

	private final static String SUCCESS_JSON = "success";
	private final static String MSG_JSON = "msg";

	// key of user for connecting to the server
	private String kKey;
	private final static String KEY_JSON = "key";

	@SuppressWarnings("unused")
	private String firstNameKey;
	private final static String FIRSTNAME_KEY = "first_name";

	@SuppressWarnings("unused")
	private String lastNameKey;
	private final static String LASTNAME_KEY = "last_name";

	@SuppressWarnings("unused")
	private String imageKey;
	private final static String IMAGE = "profile_image";

	private static int RESULT_LOAD_IMAGE = 1;
	private static final int CAMERA_REQUEST = 1888;
	private ImageView profilePicture;

	ListView list;
	ProfileListAdapter adapter;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set layout
		setContentView(R.layout.user_account);
		kKey = SharedPreferencesEditor.getKey();
		firstNameKey = this.getIntent().getStringExtra(FIRSTNAME_KEY);
		lastNameKey = this.getIntent().getStringExtra(LASTNAME_KEY);
		imageKey = this.getIntent().getStringExtra(IMAGE);

		// Get the base url
		String baseUrl = getResources().getString(R.string.base_url);
		RETRIEVE_PROFILE_URL = baseUrl + RETRIEVE_PROFILE_URL_EXT;
		SET_PROFILE_URL = baseUrl + SET_PROFILE_URL_EXT;

		ProgressDialog progress = ProgressDialog.show(AccountActivity.this,
				"Please wait", "Loading ...");
		String data = "/key/" + kKey;

		new ProfileAsyncTask(progress).execute(data);

		Button chooseExisting = (Button) findViewById(R.id.choose);
		chooseExisting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});

		this.profilePicture = (ImageView) this
				.findViewById(R.id.profilePicture);

		Button photoButton = (Button) this.findViewById(R.id.take);
		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
			}
		});

		// Get the key and user details

		// setName = this.getIntent().getStringExtra(FIRSTNAME_KEY);
		// setLastName = this.getIntent().getStringExtra(LASTNAME_KEY);

		// get Strings from the EditText fields
		Button continueButton = (Button) findViewById(R.id.submit);
		continueButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String firstName = ((EditText) findViewById(R.id.editName))
						.getEditableText().toString();

				String lastName = ((EditText) findViewById(R.id.editLastName))
						.getEditableText().toString();

				profilePicture.buildDrawingCache();
				Bitmap ProfilePicture = profilePicture.getDrawingCache();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ProfilePicture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] b = baos.toByteArray();

				String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

				// ListView listView;
				// listView = (ListView) findViewById(R.id.listView1);
				// listView.setOnItemClickListener(this);

				// make GET request to retrieve existing user reviews for venue

				ProgressDialog progress = ProgressDialog.show(
						AccountActivity.this, "Please wait", "Loading ...");
				List<NameValuePair> data1 = new ArrayList<NameValuePair>(4);
				data1.add(new BasicNameValuePair(KEY_JSON, kKey));
				data1.add(new BasicNameValuePair(FIRSTNAME_KEY, firstName));
				data1.add(new BasicNameValuePair(LASTNAME_KEY, lastName));
				data1.add(new BasicNameValuePair(IMAGE, encodedImage));
				new SubmitChangesAsyncTask(progress).execute(data1);

			}
		});

	}

	// Makes it possible to click on the Review and allows to go to the Review
	// screen once set up
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {
		Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			profilePicture = (ImageView) findViewById(R.id.profilePicture);

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8; // 1/8 of original image
			Bitmap b = BitmapFactory.decodeFile(picturePath, options);
			profilePicture.setImageBitmap(b);

		}

		if (requestCode == CAMERA_REQUEST) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			profilePicture.setImageBitmap(photo);
		}

	}

	private class SubmitChangesAsyncTask extends
			AsyncTask<List<NameValuePair>, Void, JSONObject> {
		private ProgressDialog progress;

		public SubmitChangesAsyncTask(ProgressDialog progress) {
			this.progress = progress;
		}

		public void onPreExecute() {
			progress.show();
		}

		@SuppressWarnings("unused")
		protected void onProgressUpdate(Integer... progress) {
			setProgress(progress[0]);
		}

		@Override
		protected JSONObject doInBackground(List<NameValuePair>... params) {

			List<NameValuePair> data1 = params[0];

			JSONObject resultJson = HttpRequest.makePostRequest(
					SET_PROFILE_URL, data1);

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

						showAlertMessage(
								(getResources()
										.getString(R.string.congratulations)),
								getResources().getString(
										R.string.successMessageRegistration));

					} else {

						showAlertMessage(
								(getResources()
										.getString(R.string.errorMessage)),
								MSG_JSON);

					}// TODO: do more error checking stuff when Sandeep has
						// extended his API

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void showAlertMessage(final String title, final String message) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(title);

		alert.setMessage(message);
		alert.setPositiveButton(getResources().getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO: maybe delete content of some fields
						if ((getResources()
								.getString(R.string.successMessageRegistration))
								.equals(message)) {
							Intent i = new Intent(getApplicationContext(),
									LoginActivity.class);
							startActivity(i);
						} else {
							// TODO
						}
					}
				});

		alert.show();
	}

	private class ProfileAsyncTask extends AsyncTask<String, Void, JSONObject> {

		String data;
		private ProgressDialog progress;

		public ProfileAsyncTask(ProgressDialog progress) {
			this.progress = progress;
		}

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
					RETRIEVE_PROFILE_URL, data);

			// resultJson = HttpRequest.makeGetRequest(RETRIEVE_PROFILE_URL,
			// data);
			return resultJson;
		}

		@Override
		protected void onPostExecute(JSONObject result) {

			super.onPostExecute(result);
			progress.dismiss();
			if (result != null) {
				try {
					final List<ProfileInfo> profile = new ProfileInfoParser()
							.parseJSON(result);
					// final List<FollowerProfileVenue> reviewer_profile_venue =
					// new FollowerProfileVenueParser().parseJSON(result);

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							fillProfile(profile);
							// showList(reviewer_profile_venue);
						}
					});

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void showList(List<UserFollowers> followers) {
		ListView list = (ListView) findViewById(R.id.listView1);
		adapter = new ProfileListAdapter(this, R.layout.follower_row, followers);
		list.setAdapter(adapter);
	}

	private void fillProfile(List<ProfileInfo> profile) { // Set User name
		EditText editName = (EditText) findViewById(R.id.editName);
		String firstName = profile.get(0).getName();
		editName.setText(firstName);

		EditText editLastName = (EditText) findViewById(R.id.editLastName);
		String LastName = profile.get(0).getLastName();
		editLastName.setText(LastName);

		TextView name = (TextView) findViewById(R.id.name);
		name.setText(firstName);

		// Set User last_name
		TextView lastName = (TextView) findViewById(R.id.lastName);
		lastName.setText(LastName);

		// Set User email
		TextView email = (TextView) findViewById(R.id.email);
		String Email = profile.get(0).getEmail();
		email.setText(Email);

		// Set User picture
		ImageView image = (ImageView) findViewById(R.id.profilePicture);
		byte[] decodedString = Base64.decode(profile.get(0).getProfileImage(),
				Base64.NO_WRAP);
		InputStream inputStream = new ByteArrayInputStream(decodedString);
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		image.setImageBitmap(bitmap);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// set settings icon actions
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_profile:
			Intent i = new Intent(getApplicationContext(),
					AccountActivity.class);
			i.putExtra(KEY_JSON, kKey);
			startActivity(i);

			break;
		case R.id.action_settings:
			Intent i1 = new Intent(getApplicationContext(),
					SettingsActivity.class);
			i1.putExtra(KEY_JSON, kKey);
			startActivity(i1);

			break;

		default:
			break;
		}

		return true;

	}
}
