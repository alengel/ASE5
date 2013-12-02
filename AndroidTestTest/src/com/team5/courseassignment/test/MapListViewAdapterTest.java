package com.team5.courseassignment.test;



import java.util.ArrayList;

import com.team5.courseassignment.adapters.MapListViewAdapter;
import com.team5.courseassignment.data.FourSquareVenue;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class MapListViewAdapterTest extends AndroidTestCase {
    private MapListViewAdapter mAdapter;

    private FourSquareVenue name;
    private FourSquareVenue id;
    private FourSquareVenue distance;
    private ViewGroup parent;
    
    public MapListViewAdapterTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
        ArrayList<FourSquareVenue> data = new ArrayList<FourSquareVenue>();

        name = new FourSquareVenue("", "", 0);
        id = new FourSquareVenue("", "", 0);
        distance = new FourSquareVenue("", "", 0);
        
        data.add(name);
        data.add(id);
        data.add(distance);
        
        mAdapter = new MapListViewAdapter(getContext(), 0, data);
    }


    public void testGetItem() {
        assertEquals("Name was expected.", name.getName(),
                ((FourSquareVenue) mAdapter.getItem(0)).getName());
    }

    public void testGetItemId() {
        assertEquals("Wrong ID.", 0, mAdapter.getItemId(0));
    }

    public void testGetCount() {
        assertEquals("FollowerProfileVenue amount incorrect.", 3, mAdapter.getCount());
    }

    
    public void testGetView() {
    	// inflate the layout
        final Context context = getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        parent = (ViewGroup) inflater.inflate(com.team5.courseassignment.R.layout.map_row,
                null);
        // manually measure and layout
        parent.setLayoutParams(new LayoutParams(
        	    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        parent.measure(500, 500);
        parent.layout(0, 0, 500, 500);
        //View view = mAdapter.getView(0, null, null);

        TextView name1 = (TextView) parent
                .findViewById(com.team5.courseassignment.R.id.name);

        TextView distance1 = (TextView) parent
                .findViewById(com.team5.courseassignment.R.id.distance);
        
        

       
        assertNotNull("View is null. ", parent);
        assertNotNull("LocationName TextView is null. ", name1);
        assertNotNull("Rating TextView is null. ", distance1);
        

        assertEquals("Names doesn't match.", name.getName(),name1.getText());
        assertEquals("locationName doesn't match.", distance.getDistance(), 0);
        
    }
}
