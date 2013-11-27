package com.team5.courseassignment;

import java.net.MalformedURLException;
import java.util.List;

import com.team5.courseassignment.ImageLoader.ImageLoadedListener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<VenueReview> {
	  private int resourceId = 0;
	  private LayoutInflater inflater;
	  private ImageLoader imageLoader = new ImageLoader();
	private Context context;
	
	  public ListViewAdapter(Context context, int resourceId, List<VenueReview> mediaItems) {
	    super(context, 0, mediaItems);
	    this.resourceId = resourceId;
	    this.context = context;
	    
	    inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  }
	 
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	 
	    View view;
	    TextView firstName;
	    TextView lastName;
	    TextView rating;
	    TextView review;
	    TextView voteNumber;
	    
	    
	    final ImageView image;
	 
	    view = inflater.inflate(resourceId, parent, false);
	 
	    try {
	      firstName = (TextView)view.findViewById(R.id.firstName);
	      lastName = (TextView)view.findViewById(R.id.lastName);
	      rating = (TextView)view.findViewById(R.id.rating);
	      review = (TextView)view.findViewById(R.id.review);
	      voteNumber = (TextView)view.findViewById(R.id.voteNumber);
	      
	     Button ib = (Button)view.findViewById(R.id.commentButton);
	      ib.setOnClickListener(new OnClickListener(){
	    	  @Override
	    	  public void onClick(View v){
	    		  showCommentPopup();
	    	  }
	      });
	      
	      final CheckBox voteUp = (CheckBox)view. findViewById(R.id.voteUp);
	      final CheckBox voteDown = (CheckBox)view. findViewById(R.id.voteDown);
	      
	    	voteUp.setOnCheckedChangeListener(new OnCheckedChangeListener()
	    	{
	    	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	    	    {
	    	    	
	    	    	
	    	    	
	    	    	if ( isChecked)
	    	        {
	    	    		voteUp.setEnabled(true);
	    	        	//voteUp.setText(1);
	    	        	voteDown.setEnabled(false);
	    	        	//TODO
	    	        	/**
	    	        	 *  String selectedText = voteUp.getText().toString();
                    		Intent i = new Intent(this, VenueReview.class);
                    		i.putExtra("cakedata", selectedText);
                    		startActivity(i);
	    	        	 */
	    	        	
	    	        	
	    	        } else { 
	    	        	voteUp.setEnabled(false);
	    	        	//voteUp.setText(1);
	    	        	voteDown.setEnabled(true);
	    	        	//TODO
	    	        }
	    	    	
	    	    	
	    	    	
	    	     }    
	    	    
	    	});
	    	
	    	voteDown.setOnCheckedChangeListener(new OnCheckedChangeListener()
	    	{
	    	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	    	    {
	    	    	
	    	
	    	if ( voteDown.isChecked() )
	        {
	    		voteDown.setEnabled(true);
	        	//voteUp.setText(1);
	        	voteUp.setEnabled(false);
	        	//TODO
	        	/**
	        	 *  String selectedText = voteUp.getText().toString();
            		Intent i = new Intent(this, VenueReview.class);
            		i.putExtra("cakedata", selectedText);
            		startActivity(i);
	        	 */
	        	
	        	
	        } else { 
	        	voteUp.setEnabled(true);
	        	//voteUp.setText(1);
	        	voteDown.setEnabled(false);
	        	//TODO
	        	}
	    		
	    	    }    
	    	    
	    	});
	    	
	      
	      image = (ImageView)view.findViewById(R.id.profile_image);
	      image.setOnClickListener(new OnClickListener() 
	        {
	            @Override
	            public void onClick(View v) 
	            {
	            	/**TODO 
		    		  // launch ProfilePageActivity
						Intent i = new Intent(getApplicationContext(), ProfilePageActivity.class); // need to create comments activity
						
						i.putExtra(KEY_JSON, kKey); //need to know api call for it
						i.putExtra(user_NAME, user_id);//
						i.putExtra(VENUE_ID, venueId);//
						
						startActivity(i);
						*/
	            }
	        });
	      
	    } catch( ClassCastException e ) {
	     // Log.e(TAG, "Your layout must provide an image and a text view with ID's icon and text.", e);
	      throw e;
	    }
	 
	    VenueReview item = getItem(position);
	    Bitmap cachedImage = null;
	    try {
	      cachedImage = imageLoader.loadImage(item.getProfileImage(), new ImageLoadedListener() {
	      public void imageLoaded(Bitmap imageBitmap) {
	      image.setImageBitmap(imageBitmap);
	      notifyDataSetChanged();                }
	      });
	    } catch (MalformedURLException e) {
	     // Log.e(TAG, "Bad remote image URL: " + item.getProfileImage(), e);
	    }
	 
	    firstName.setText(item.getFirstName());
	    lastName.setText(item.getLastName());
	    rating.setText("Rating: "+item.getRating()+ " stars");
	    review.setText(item.getReview());
	    voteNumber.setText(item.getVotes()); // Vote number need to get from server.
	    
	 
	    if( cachedImage != null ) {
	      image.setImageBitmap(cachedImage);
	    }
	 
	    return view;
	  }
	  
	  private void showCommentPopup() {
		  
		  AlertDialog.Builder helpBuilder = new AlertDialog.Builder(context);
		  helpBuilder.setTitle("Enter your comment here");
		  helpBuilder.setMessage(" ");
		  final EditText input = new EditText(context);
		  input.setSingleLine();
		  input.setText("");
		  helpBuilder.setView(input);

		  helpBuilder.setPositiveButton("Continue",
		    new DialogInterface.OnClickListener() {

			     public void onClick(DialogInterface dialog, int which) {
			    	 //Send the comment to the server...
			     }
		    });

		  helpBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
			   dialog.cancel();
		   }
		  });
		  
		  AlertDialog helpDialog = helpBuilder.create();
		  	helpDialog.show();
		 }

	}
