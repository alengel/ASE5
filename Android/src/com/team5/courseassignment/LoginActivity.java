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
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !!!
 */


public class LoginActivity extends Activity {
	
	//variables for the POST call
	private final static String LOGIN_URL = "http://switchcodes.in/sandbox/projectpackets/t5/user/login";
	private final static String EMAIL_KEY = "email";
	private final static String PASSWORD_KEY = "passwd";
	
	
	//variables for the POST answer
	private final static String SUCCESS_JSON = "success";
	private final static String KEY_JSON ="key";  
		
		

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        //set layout
        setContentView(R.layout.login);	  
        
        
        //set Button actions
        TextView newToAppTextView = (TextView) findViewById(R.id.register_here_login);
        newToAppTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
				startActivity(i);
				
			}
		});
        
        Button loginButton = (Button) findViewById(R.id.login_button_login);
        loginButton.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				// TODO connect to server to login
				
				//get data for call
				String email = ((EditText) findViewById(R.id.email_box_login)).getEditableText().toString();
				String password = ((EditText) findViewById(R.id.password_box_login)).getEditableText().toString();
				
				//TODO: maybe user input checking
				
				String encryptedPassword = Utilities.encryptString(password);
				
				List<NameValuePair> data = new ArrayList<NameValuePair>(2);
				data.add(new BasicNameValuePair(EMAIL_KEY, email));
				data.add(new BasicNameValuePair(PASSWORD_KEY, encryptedPassword));
				
				//make POST call
				new LoginAsyncTask().execute(data);
				
			}
		});
        
        
        TextView forgottenPassword = (TextView) findViewById(R.id.forgotten_password_login);
        forgottenPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getApplicationContext(), ForgottenPasswordActivity.class);
				startActivity(i);
				
			}
		});
    }   
    
    
private void showInvalidInput(String message) {
		
		Log.d("login", "Login.showInvalidInput() with argument: " + message);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(getResources().getString(R.string.errorMessage));
		alert.setMessage(message);
		alert.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//TODO: update the api to handle the wrong password|email.
				
			}
		});
		alert.show();
	}
    
    
    
    private class LoginAsyncTask extends AsyncTask<List<NameValuePair>, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(List<NameValuePair>... params) {
			
			
			List<NameValuePair> data = params[0];
			
			HttpClient client =new DefaultHttpClient();
			HttpPost post = new HttpPost(LOGIN_URL);
			
			HttpResponse response = null;
			
			try {
				
				HttpEntity entity = new UrlEncodedFormEntity(data, "UTF-8");   
				post.setEntity(entity);
				
				
				Log.d("login", "post: " + post);
				
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
					
					Log.d("login", "response: " + response);
					
					String resultString = EntityUtils.toString(response.getEntity());
					
					Log.d("login", "resultString: " + resultString);
					
					resultJson = new JSONObject(resultString);
					
					Log.d("login", "resultJson: " + resultJson.toString());
					
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
						
						//TODO: get key, store it
						String key = result.getString(KEY_JSON);
						if(key == null || key.isEmpty()) {
							showInvalidInput(getResources().getString(R.string.key_not_retrieved));
						}
						
						//launch LoginActivity
						Intent i = new Intent(getApplicationContext(), MapActivity.class);
						i.putExtra(KEY_JSON, key);
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
