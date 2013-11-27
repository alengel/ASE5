package com.team5.courseassignment;

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
import android.graphics.Matrix;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ReviewActivity extends Activity {
	
	//variables for the POST call
	private static String REVIEW_URL;
	private static String REVIEW_URL_EXT = "review";
	
	//key of user for connecting to the server
	private String kKey;
    private final static String KEY_JSON ="key";
	private final static String SUCCESS_JSON = "success";
	
	//venue details
	private String venueName;
	private final static String VENUE_NAME = "name";
	private String venueId;
	private final static String VENUE_ID = "id";
	
	private final static String RATING = "rating";
	private final static String REVIEW = "review";
	private final static String IMAGE ="location_image";
	
	private AlertDialog.Builder alert;
	private ImageView imageView;
	private static int RESULT_LOAD_IMAGE = 1;
	private static final int CAMERA_REQUEST = 1888; 
	private float curScale = 1F;
	private float curRotate = 0F;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        //Get the key and venue details
        kKey = this.getIntent().getStringExtra(KEY_JSON);
    	venueName = this.getIntent().getStringExtra(VENUE_NAME);
    	venueId = this.getIntent().getStringExtra(VENUE_ID);
    	
    	//get the base url
    	REVIEW_URL = getResources().getString(R.string.base_url) + REVIEW_URL_EXT;
        
    	//Set layout
    	setContentView(R.layout.review);
    	TextView name = (TextView) findViewById(R.id.venue_name_review);
    	name.setText(venueName);
    	alert = new AlertDialog.Builder(this);
    	
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
        
        this.imageView = (ImageView)this.findViewById(R.id.imgView);
        Button photoButton = (Button) this.findViewById(R.id.takeAphoto);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
            }
        });
    	
    	//Setting up review button.
    	Button checkinButton = (Button) findViewById(R.id.review_button);
    	checkinButton.setOnClickListener(new OnClickListener() {
			
    		@SuppressWarnings("unchecked")
			public void onClick(View v) {
    			
    			//get data for call
    			
    			imageView.buildDrawingCache();
    			Bitmap venuePicture = imageView.getDrawingCache();
    			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
    			venuePicture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    			byte[] b = baos.toByteArray();

    			String encodedImage = Base64.encodeToString(b , Base64.DEFAULT);
    			
				String review = ((EditText) findViewById(R.id.venue_review)).getEditableText().toString();
				RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_bar);
				String rating = String.valueOf(ratingBar.getRating());
				
				List<NameValuePair> data = new ArrayList<NameValuePair>(5);
				data.add(new BasicNameValuePair(KEY_JSON, kKey));
				data.add(new BasicNameValuePair("venue_name", venueName));
				data.add(new BasicNameValuePair("venue_id", venueId));
				data.add(new BasicNameValuePair(RATING, rating));
				data.add(new BasicNameValuePair(REVIEW, review));
				data.add(new BasicNameValuePair(IMAGE,encodedImage));
				
				//make POST call
				ProgressDialog progress = ProgressDialog.show(ReviewActivity.this, "Please wait", "Loading ...");
				new ReviewAsyncTask(progress).execute(data);
			}
		});	
    }
	
	@Override
    public void onBackPressed() {
    	Intent start = new Intent(ReviewActivity.this,CheckinActivity.class);
        startActivity(start);
        finishActivity(0);
    }
    
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			
			ImageView imageView = (ImageView) findViewById(R.id.imgView);
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize=8;      // 1/8 of original image
			Bitmap b = BitmapFactory.decodeFile(picturePath,options);
			
		    int bmpWidth = b.getWidth();
			int bmpHeight = b.getHeight();
	
			Matrix matrix = new Matrix();
			matrix.postScale(curScale, curScale);
			matrix.postRotate(curRotate);
	
			Bitmap resizedBitmap = Bitmap.createBitmap(b, 0, 0, bmpWidth, bmpHeight, matrix, true);
			  
			imageView.setImageBitmap(resizedBitmap);
		}
		
		if (requestCode == CAMERA_REQUEST) {  
            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
            imageView.setImageBitmap(photo);
        } 
    
    
    }
    
	
    private class ReviewAsyncTask extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
    	private ProgressDialog progress;
    	public ReviewAsyncTask(ProgressDialog progress) {
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
			
			
			List<NameValuePair> data = params[0];
			
			JSONObject resultJson = HttpRequest.makePostRequest(REVIEW_URL, data);
			
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
						
						showReviewSuccessMessage((getResources().getString(R.string.review_success_title)),getResources().getString(R.string.review_success));
						
					} else {
						//TODO: if the server responds with error, display message
					} 
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			} 
		}	
    }
    
    private void showReviewSuccessMessage(final String title,final String message) {
		
    	alert.setTitle(title);
		
		alert.setMessage(message);
		
		alert.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if((getResources().getString(R.string.review_success)).equals(message)){
					Intent i = new Intent(getApplicationContext(), MapActivity.class);
					i.putExtra(KEY_JSON, kKey);
					startActivity(i);
				}else{
					// TODO: If the user clicks neither button.
				}
			}
		});
		
		alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if((getResources().getString(R.string.review_success)).equals(message)){
					Intent i = new Intent(getApplicationContext(),  CheckinActivity.class);
					
					i.putExtra(KEY_JSON, kKey);
					i.putExtra(VENUE_NAME, venueName);
					i.putExtra(VENUE_ID, venueId);
					
					startActivity(i);
				}else{
					// TODO: If the user clicks neither button. 
				}
			}
		});
		
		alert.show();
	}
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
	
	//set actionbar icons
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			
			switch (item.getItemId()) {
		    case R.id.action_profile:
		    	Intent openProfile = new Intent(getApplicationContext(), ProfileActivity.class);
		    	openProfile.putExtra(KEY_JSON, kKey);
				startActivity(openProfile);
				break;
				
		    case R.id.action_settings:
		    	Intent openSettings = new Intent(getApplicationContext(), SettingsActivity.class);
		    	openSettings.putExtra(KEY_JSON, kKey);
				startActivity(openSettings);
				break;

		    default:
		      break;
		    }

		    return true;
	    }
}
