package com.team5.courseassignment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.team5.courseassignment.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !!!
 */


public class RegistrationActivity extends Activity {
	
	//variables for the POST call
	private final static String REGISTER_URL = "http://switchcodes.in/sandbox/projectpackets/t5/user/register";
	private final static String EMAIL_KEY = "email";
	private final static String PASSWORD_KEY = "passwd";
	private final static String FIRSTNAME_KEY = "first_name";
	private final static String LASTNAME_KEY = "last_name";
	
	
	//variables for the POST answer
	private final static String SUCCESS_JSON = "success";
	
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //set layout
        setContentView(R.layout.registration);	 
        
        
        //set button actions
        Button continueButton = (Button) findViewById(R.id.register_button_registration);
        continueButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//TODO: show the user that progress is happening and set text fields to be unchangeable!
				
				//get Strings from the EditText fields
				String firstName = ((EditText) findViewById(R.id.first_name_registration)).getEditableText().toString();
				String lastName = ((EditText) findViewById(R.id.last_name_registration)).getEditableText().toString();
				String email = ((EditText) findViewById(R.id.email_box_registration)).getEditableText().toString();
				String password = ((EditText) findViewById(R.id.password_box_registration)).getEditableText().toString();
				String retypePassword = ((EditText) findViewById(R.id.retype_password_box_registration)).getEditableText().toString();
				
				//ensure that the password Strings are equal
				if(! password.equals(retypePassword)) {
					showInvalidInput(getResources().getString(R.string.passwords_not_equal));
				}
				
				//TODO: maybe more user input checking
				
				String encryptedPassword = Utilities.encryptString(password);
				
				List<NameValuePair> data = new ArrayList<NameValuePair>(4);
				data.add(new BasicNameValuePair(FIRSTNAME_KEY, firstName));
				data.add(new BasicNameValuePair(LASTNAME_KEY, lastName));
				data.add(new BasicNameValuePair(EMAIL_KEY, email));
				data.add(new BasicNameValuePair(PASSWORD_KEY, encryptedPassword));
				

				//make POST call
				new RegisterAsyncTask().execute(data);
				

				
				
			}

			
		});
    } 
    
    
    
    private void showInvalidInput(String message) {
		
		Log.d("b_logic", "RegistrationActivity.showInvalidInput() with argument: " + message);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(getResources().getString(R.string.errorMessage));
		alert.setMessage(message);
		alert.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//TODO: maybe delete content of some fields
				
			}
		});
		alert.show();
	}
    
    
    
    private class RegisterAsyncTask extends AsyncTask<List<NameValuePair>, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(List<NameValuePair>... params) {
			
			
			List<NameValuePair> data = params[0];
			
			HttpClient client =new DefaultHttpClient();
			HttpPost post = new HttpPost(REGISTER_URL);
			
			HttpResponse response = null;
			
			try {
				
				HttpEntity entity = new UrlEncodedFormEntity(data, "UTF-8");   
				post.setEntity(entity);
				
				
				Log.d("b_logic", "post: " + post);
				
				response = client.execute(post);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			JSONObject resultJson = null;
			
			if(response != null) {
				try {
					
					Log.d("b_logic", "response: " + response);
					
					String resultString = EntityUtils.toString(response.getEntity());
					
					Log.d("b_logic", "resultString: " + resultString);
					
					resultJson = new JSONObject(resultString);
					
					Log.d("b_logic", "resultJson: " + resultJson.toString());
					
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
			
			if(result != null) {
				
				try {
					
					String success = result.getString(SUCCESS_JSON);
					
					if(success.equals("true")) {
						//launch LoginActivity
						Intent i = new Intent(getApplicationContext(), LoginActivity.class);
						startActivity(i);
						
					} else {
						showInvalidInput(getResources().getString(R.string.invalid_input_generic));
					} //TODO: do more error checking stuff when Sandeep has extended his API
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			} 
		}


		
		

    	
    }
}
