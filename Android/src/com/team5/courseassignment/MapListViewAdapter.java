package com.team5.courseassignment;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MapListViewAdapter extends ArrayAdapter<FourSquareVenue> {
	  private int resourceId = 0;
	  private LayoutInflater inflater;
	 // private ImageLoader imageLoader = new ImageLoader();
	
	
	  public MapListViewAdapter(Context context, int resourceId, List<FourSquareVenue> mediaItems) {
	    super(context, 0, mediaItems);
	    this.resourceId = resourceId;
	    
	    
	    inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  }
	 
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	 
	    View view;
	    TextView textTitle;
	    TextView textTitle1;
	    
	    
	    
	    
	 
	    view = inflater.inflate(resourceId, parent, false);
	 
	    try {
	      textTitle = (TextView)view.findViewById(R.id.name);
	      textTitle1 = (TextView)view.findViewById(R.id.distance);
	      
	      
	     
	       
	      
	    } catch( ClassCastException e ) {
	     // Log.e(TAG, "Your layout must provide an image and a text view with ID's icon and text.", e);
	      throw e;
	    }
	 
	    FourSquareVenue item = getItem(position);
	    
	    textTitle.setText(item.getName());
	    textTitle1.setText(item.getDistance()+"  meters from here");

	    
	 
	    
	 
	    return view;
	  }
	  
	 

	}
