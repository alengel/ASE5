package com.team5.courseassignment.adapters;

import java.net.MalformedURLException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team5.courseassignment.R;
import com.team5.courseassignment.data.FollowerProfileVenue;
import com.team5.courseassignment.utilities.ImageLoader;
import com.team5.courseassignment.utilities.ImageLoader.ImageLoadedListener;

public class FollowerVenueAdapter extends ArrayAdapter<FollowerProfileVenue> {
	private int resourceId = 0;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = new ImageLoader();

	/**
	 * The constructor method of follower venue adapter.
	 * 
	 * @param context
	 *            - the context.
	 * @param resourceId
	 *            - id of the source - integer.
	 * @param mediaItems
	 *            - list of venues visited by follower with all media data.
	 */
	public FollowerVenueAdapter(Context context, int resourceId,
			List<FollowerProfileVenue> mediaItems) {
		super(context, 0, mediaItems);
		this.resourceId = resourceId;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * GetView method sets list view to have this custom adapter. It creates
	 * views (Text, Image), binds list view adapter, layout style.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view;
		TextView locationName;
		TextView review;
		TextView rating;

		final ImageView image;

		view = inflater.inflate(resourceId, parent, false);

		try {
			locationName = (TextView) view.findViewById(R.id.locationName);
			review = (TextView) view.findViewById(R.id.review);
			rating = (TextView) view.findViewById(R.id.rating);

			image = (ImageView) view.findViewById(R.id.locationImage);

		} catch (ClassCastException e) {
			throw e;
		}

		FollowerProfileVenue item = getItem(position);
		Bitmap cachedImage = null;

		try {
			cachedImage = imageLoader.loadImage(item.getLocationImage(),
					new ImageLoadedListener() {
						@Override
						public void imageLoaded(Bitmap imageBitmap) {
							image.setImageBitmap(imageBitmap);
							notifyDataSetChanged();
						}
					});
		} catch (MalformedURLException e) {
			// Log.e(TAG, "Bad remote image URL: " + item.getProfileImage(), e);
		}

		locationName.setText(item.getLocationName());
		rating.setText("Rating: " + item.getRating() + "  stars");
		review.setText(item.getReview());

		if (cachedImage != null) {
			image.setImageBitmap(cachedImage);
		}

		return view;
	}
}
