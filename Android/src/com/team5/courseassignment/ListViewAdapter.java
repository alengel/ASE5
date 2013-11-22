package com.team5.courseassignment;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.team5.courseassignment.ImageLoader.ImageLoadedListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<VenueReview> {
	  private final static String TAG = "MediaItemAdapter";
	  private int resourceId = 0;
	  private LayoutInflater inflater;
	  private Context context;
	 
	  private ImageLoader imageLoader = new ImageLoader();
	 
	  public ListViewAdapter(Context context, int resourceId, List<VenueReview> mediaItems) {
	    super(context, 0, mediaItems);
	    this.resourceId = resourceId;
	    inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    this.context = context;
	  }
	 
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	 
	    View view;
	    TextView textTitle;
	    TextView textTitle1;
	    TextView textTitle2;
	    TextView textTitle3;
	    
	    
	    final ImageView image;
	 
	    view = inflater.inflate(resourceId, parent, false);
	 
	    try {
	      textTitle = (TextView)view.findViewById(R.id.firstName);
	      textTitle1 = (TextView)view.findViewById(R.id.lastName);
	      textTitle2 = (TextView)view.findViewById(R.id.rating);
	      textTitle3 = (TextView)view.findViewById(R.id.review);
	      
	     ImageButton ib = (ImageButton)view.findViewById(R.id.commentButton);
	      ib.setOnClickListener(new OnClickListener(){
	    	  @Override
	    	  public void onClick(View v){
	    		/**TODO 
	    		  // launch CommentsActivity
					Intent i = new Intent(getApplicationContext(), CommentsActivity.class); // need to create comments activity
					i.putExtra(KEY_JSON, kKey); //need to know api call for it
					i.putExtra(user_NAME, user_id);//
					i.putExtra(VENUE_ID, venueId);//
					startActivity(i);
					*/
	    		  
	    	  }
	      });
	      
	      CheckBox votes = (CheckBox)view. findViewById(R.id.btnCustomCheckBoxLike);
	    	votes.setOnCheckedChangeListener(new OnCheckedChangeListener()
	    	{
	    	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	    	    {
	    	        if ( isChecked )
	    	        {
	    	            //TODO perform voting logic
	    	        }else{
	    	        	
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
	     
	    }
	 
	    textTitle.setText(item.getFirstName());
	    textTitle1.setText(item.getLastName()+"  was here");
	    textTitle2.setText("Rating: "+item.getRating()+"  stars");
	    textTitle3.setText("Review: "+item.getReview());
	    
	 
	    if( cachedImage != null ) {
	      image.setImageBitmap(cachedImage);
	    }
	 
	    return view;
	  }
	}
