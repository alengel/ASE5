package com.team5.courseassignment;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
    
    private Activity activity;
    private String[]  data  ;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    
    public ListViewAdapter(Activity a, List<VenueReview> reviews) {
    	//data=   reviews.toArray(new String[reviews.size()]);
    	
    	data= new String[reviews.size()];
    	for (int i=0; i < reviews.size(); i++) {
    	   data[i] = reviews.get(i).toString();
    	}
    	
    	activity = a;
       // data=reviews;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.row, null);

        TextView text=(TextView)vi.findViewById(R.id.context);;
        ImageView image=(ImageView)vi.findViewById(R.id.user_picture);
        text.setText(" "+position);
        
        imageLoader.DisplayImage(data[position], image);
        return vi;
    }
}