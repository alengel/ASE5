package com.team5.courseassignment.test;


import java.util.ArrayList;

import com.team5.courseassignment.adapters.FollowerVenueAdapter;
import com.team5.courseassignment.data.FollowerProfileVenue;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class FollowerVenueAdapterTest extends AndroidTestCase {
    private FollowerVenueAdapter mAdapter;

    private FollowerProfileVenue locationImage;
    private FollowerProfileVenue locationName;
    private FollowerProfileVenue rating;
    private FollowerProfileVenue review;
    private ViewGroup parent;

    
    public FollowerVenueAdapterTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
        ArrayList<FollowerProfileVenue> data = new ArrayList<FollowerProfileVenue>();

        locationImage = new FollowerProfileVenue("locationImage", null, null, null);
        locationName = new FollowerProfileVenue("locationName", "", "", "");
        rating = new FollowerProfileVenue("rating", "", "", "");
        review = new FollowerProfileVenue("review", "", "", "");
        data.add(locationImage);
        data.add(locationName);
        data.add(rating);
        data.add(review);
        mAdapter = new FollowerVenueAdapter(getContext(), 0, data);
    }


    public void testGetItem() {
        assertEquals("locationImage was expected.", locationImage.getLocationImage(),
                ((FollowerProfileVenue) mAdapter.getItem(0)).getLocationImage());
    }

    public void testGetItemId() {
        assertEquals("Wrong ID.", 0, mAdapter.getItemId(0));
    }

    public void testGetCount() {
        assertEquals("FollowerProfileVenue amount incorrect.", 4, mAdapter.getCount());
    }

    // I have 3 views on my adapter, name, number and photo
    public void testGetView() {
    	// inflate the layout
        final Context context = getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        parent = (ViewGroup) inflater.inflate(com.team5.courseassignment.R.layout.venue_review_row,
                null);
        // manually measure and layout
        parent.setLayoutParams(new LayoutParams(
        	    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        parent.measure(500, 500);
        parent.layout(0, 0, 500, 500);
         
    	
    	//View view = mAdapter.getView(0, context, parent);

        TextView locationName1 = (TextView) parent
                .findViewById(com.team5.courseassignment.R.id.locationName);

        TextView rating1 = (TextView) parent
                .findViewById(com.team5.courseassignment.R.id.rating);
        
        TextView review1 = (TextView) parent
                .findViewById(com.team5.courseassignment.R.id.review);

        ImageView locationImage1 = (ImageView) parent
                .findViewById(com.team5.courseassignment.R.id.locationImage);

       
        assertNotNull("View is null. ", parent);
        assertNotNull("LocationName TextView is null. ", locationName1);
        assertNotNull("Rating TextView is null. ", rating1);
        assertNotNull("Review TextView is null. ", review1);
        assertNotNull("locationImage ImageView is null. ", locationImage1);

        assertEquals("locationImage", null,null);
        assertEquals("locationName doesn't match.", locationName.getLocationName(), locationName1.getText());
        assertEquals("rating doesn't match.", rating.getRating(), rating1.getText());
        assertEquals("review doesn't match.", review.getReview(), review1.getText());
    }
}
