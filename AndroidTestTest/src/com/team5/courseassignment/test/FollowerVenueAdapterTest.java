package com.team5.courseassignment.test;


import java.util.ArrayList;

import com.team5.courseassignment.adapters.FollowerVenueAdapter;
import com.team5.courseassignment.data.FollowerProfileVenue;

import android.test.AndroidTestCase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FollowerVenueAdapterTest extends AndroidTestCase {
    private FollowerVenueAdapter mAdapter;

    private FollowerProfileVenue locationImage;
    private FollowerProfileVenue locationName;
    private FollowerProfileVenue rating;
    private FollowerProfileVenue review;
    private View convertView;
    private ViewGroup parent;
    
    public FollowerVenueAdapterTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
        ArrayList<FollowerProfileVenue> data = new ArrayList<FollowerProfileVenue>();

        locationImage = new FollowerProfileVenue("locationImage", "+34123456789", "uri", null);
        locationName = new FollowerProfileVenue("locationName", "+34111222333", "uri", null);
        rating = new FollowerProfileVenue("rating", "+34111222333", "uri", null);
        review = new FollowerProfileVenue("review", "+34111222333", "uri", null);
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
        View view = mAdapter.getView(0, convertView, parent);

        TextView locationName1 = (TextView) view
                .findViewById(com.team5.courseassignment.R.id.locationName);

        TextView rating1 = (TextView) view
                .findViewById(com.team5.courseassignment.R.id.rating);
        
        TextView review1 = (TextView) view
                .findViewById(com.team5.courseassignment.R.id.review);

        ImageView locationImage1 = (ImageView) view
                .findViewById(com.team5.courseassignment.R.id.locationImage);

       
        assertNotNull("View is null. ", view);
        assertNotNull("LocationName TextView is null. ", locationName1);
        assertNotNull("Rating TextView is null. ", rating1);
        assertNotNull("Review TextView is null. ", review1);
        assertNotNull("locationImage ImageView is null. ", locationImage1);

        assertEquals("Names doesn't match.", locationImage.getLocationImage(),locationImage1.getContext());
        assertEquals("locationName doesn't match.", locationName.getLocationName(), locationName1.getText());
        assertEquals("rating doesn't match.", rating.getRating(), rating1.getText());
        assertEquals("review doesn't match.", review.getReview(), review1.getText());
    }
}
