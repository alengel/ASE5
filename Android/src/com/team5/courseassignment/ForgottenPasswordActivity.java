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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.team5.courseassignment.R;


public class ForgottenPasswordActivity extends Activity {
	
	//variables for the POST call
	private static String FORGOTTEN_PASSWORD_URL;
	private final static String FORGOTTEN_PASSWORD_URL_EXT = "change-password/";
	private final static String EMAIL_KEY = "email";
	
	//variables for the POST answer
	private final static String SUCCESS_JSON = "success";
	  
		protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        //set layout
	        setContentView(R.layout.forgotten_password);
	        
	        //get the base url
	        FORGOTTEN_PASSWORD_URL = getResources().getString(R.string.base_url) + FORGOTTEN_PASSWORD_URL_EXT;
	        
	        //set button Actions
	        Button continueButton = (Button) findViewById(R.id.login_button_forgottenPassword);
	        continueButton.setOnClickListener(new OnClickListener() {
				
				@SuppressWarnings("unchecked")
				@Override
				public void onClick(View v) {
					
					//get data for call
					String email = ((EditText) findViewById(R.id.email_box_forgottenPassword)).getEditableText().toString();
					
					//TODO: maybe user input checking
					List<NameValuePair> data = new ArrayList<NameValuePair>(1);
					data.add(new BasicNameValuePair(EMAIL_KEY, email));
					
					//make POST call
					ProgressDialog progress = ProgressDialog.show(ForgottenPasswordActivity.this, "Please wait", "Loading ...");
					new ForgottenPasswordAsyncTask(progress).execute(data);
					
					//TODO: show waiting dialog to user
				}
			});
	    }   


	 private void showInvalidInput(String message) {
			
			Log.d("b_logic", "Login.showInvalidInput() with argument: " + message);
			
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
	    
	    
	    
	    private class ForgottenPasswordAsyncTask extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
	    	private ProgressDialog progress;
	    	public ForgottenPasswordAsyncTask(ProgressDialog progress) {
	    	    this.progress = progress;
	    	  }

	    	  public void onPreExecute() {
	    	    progress.show();
	    	  }

	    	  protected void onProgressUpdate(Integer... progress) {
	 	         setProgress(progress[0]);
	 	     }
	    	  
	    	@Override
			protected JSONObject doInBackground(List<NameValuePair>... params) {
			
				List<NameValuePair> data = params[0];
				
				JSONObject resultJson = HttpRequest.makePostRequest(FORGOTTEN_PASSWORD_URL, data);
				
				return resultJson;
			}

			@Override
			protected void onPostExecute(JSONObject result) {
				
				super.onPostExecute(result);
				progress.dismiss();
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
