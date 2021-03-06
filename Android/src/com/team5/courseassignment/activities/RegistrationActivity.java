package com.team5.courseassignment.activities;

import java.io.ByteArrayOutputStream;
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
import android.provider.MediaStore.MediaColumns;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.team5.courseassignment.R;
import com.team5.courseassignment.utilities.HttpRequest;
import com.team5.courseassignment.utilities.SharedPreferencesEditor;
import com.team5.courseassignment.utilities.Utilities;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !!!
 */

public class RegistrationActivity extends Activity {

	// variables for the POST call
	private static String REGISTER_URL_EXT = "register";
	private final static String EMAIL_KEY = "email";
	private final static String PASSWORD_KEY = "passwd";
	private final static String FIRSTNAME_KEY = "first_name";
	private final static String LASTNAME_KEY = "last_name";
	private final static String IMAGE = "profile_image";

	// variables for the POST answer
	private final static String SUCCESS_JSON = "success";
	private final static String MSG_JSON = "message";

	private static int RESULT_LOAD_IMAGE = 1;
	private static final int CAMERA_REQUEST = 1888;
	private ImageView imageView;

	/**
	 * Called when the activity is first created. This is where we do all of our
	 * normal static set up: create views, bind data to lists, etc. This method
	 * also provides a Bundle containing the activity's previously frozen state,
	 * if there was one.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set layout
		setContentView(R.layout.registration);

		// set button actions
		Button chooseExisting = (Button) findViewById(R.id.chooseExisting);
		chooseExisting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});

		this.imageView = (ImageView) this.findViewById(R.id.imgView);
		Button photoButton = (Button) this.findViewById(R.id.takeAphoto);
		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
			}
		});

		Button continueButton = (Button) findViewById(R.id.register_button_registration);
		continueButton.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {

				// fields to be unchangeable!

				// get Strings from the EditText fields
				String firstName = ((EditText) findViewById(R.id.first_name_registration))
						.getEditableText().toString();
				String lastName = ((EditText) findViewById(R.id.last_name_registration))
						.getEditableText().toString();
				String email = ((EditText) findViewById(R.id.email_box_registration))
						.getEditableText().toString();
				String password = ((EditText) findViewById(R.id.password_box_registration))
						.getEditableText().toString();
				String retypePassword = ((EditText) findViewById(R.id.retype_password_box_registration))
						.getEditableText().toString();

				imageView.buildDrawingCache();
				Bitmap profilePicture = imageView.getDrawingCache();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				profilePicture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] b = baos.toByteArray();

				String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

				// ensure that the password Strings are equal
				if (!password.equals(retypePassword)) {

					showAlertMessage(
							(getResources().getString(R.string.errorMessage)),
							getResources().getString(
									R.string.passwords_not_equal));
				}

				String encryptedPassword = Utilities.encryptString(password);

				List<NameValuePair> data = new ArrayList<NameValuePair>(4);
				data.add(new BasicNameValuePair(FIRSTNAME_KEY, firstName));
				data.add(new BasicNameValuePair(LASTNAME_KEY, lastName));
				data.add(new BasicNameValuePair(IMAGE, encodedImage));
				data.add(new BasicNameValuePair(EMAIL_KEY, email));
				data.add(new BasicNameValuePair(PASSWORD_KEY, encryptedPassword));

				// make POST call
				ProgressDialog progress = ProgressDialog
						.show(RegistrationActivity.this, "Please wait",
								"Loading ...");
				new RegisterAsyncTask(progress).execute(data);

			}

		});

	}

	/**
	 * OnBackPressed() Called when the activity has detected the user's press of
	 * the back key. The default implementation simply finishes the current
	 * activity, but in our case we override this to go to MapActivity screen.
	 */
	@Override
	public void onBackPressed() {
		Intent start = new Intent(RegistrationActivity.this,
				LoginActivity.class);
		startActivity(start);
		finishActivity(0);
	}

	/**
	 * Gets image from gallery or takes a picture on button activity result.
	 * 
	 * @param requestCode
	 *            The request code whether its load from gallery or from camera.
	 * @param resultCode
	 *            The result code whether its load from gallery or from camera
	 * 
	 * @return data - A Bitmap image if the picture loaded from gallery or
	 *         camera.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaColumns.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			imageView = (ImageView) findViewById(R.id.imgView);

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8; // 1/8 of original image
			Bitmap b = BitmapFactory.decodeFile(picturePath, options);
			imageView.setImageBitmap(b);

		}

		if (requestCode == CAMERA_REQUEST) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			imageView.setImageBitmap(photo);
		}

	}

	/**
	 * Displays success registration message as a pop up window. When server
	 * verified all data input made by user.
	 * 
	 * @param title
	 *            - Title of the pop up window.
	 * @param message
	 *            - String successMessageRegistration - Success message!
	 */
	private void showAlertMessage(final String title, final String message) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(title);

		alert.setMessage(message);
		alert.setPositiveButton(getResources().getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						if ((getResources()
								.getString(R.string.successMessageRegistration))
								.equals(message)) {
							Intent i = new Intent(getApplicationContext(),
									LoginActivity.class);
							startActivity(i);
						} else {

						}
					}
				});
		
		alert.show();
	}

	/**
	 * Creates post request on execute. With list of data to send to server.
	 * Pre-loader created when executed. Redirecting to login screen on post
	 * execute, if and only if success message is "true". It depends on user
	 * input. Corresponding pop up window will appear with corresponding
	 * message, whether its false its error message which we get from server
	 * otherwise its congratulations message taken from string.xml
	 * 
	 */
	private class RegisterAsyncTask extends
			AsyncTask<List<NameValuePair>, Void, JSONObject> {
		private ProgressDialog progress;

		public RegisterAsyncTask(ProgressDialog progress) {
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

			JSONObject resultJson = HttpRequest.makePostRequest(SharedPreferencesEditor.getBaseUrl(getApplicationContext()), REGISTER_URL_EXT, data);

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

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
}