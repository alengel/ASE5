package com.team5.courseassignment;

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

import com.team5.courseassignment.ImageLoader.ImageLoadedListener;

public class FollowerVenueAdapter extends ArrayAdapter<FollowerProfileVenue> {
	private int resourceId = 0;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = new ImageLoader();

	public FollowerVenueAdapter(Context context, int resourceId,
			List<FollowerProfileVenue> mediaItems) {
		super(context, 0, mediaItems);
		this.resourceId = resourceId;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view;
		TextView locationName;
		TextView review;
		TextView rating;

		final ImageView image;

		view = inflater.inflate(resourceId, parent, false);

		try {
			locationName = (TextView) view.findViewById(R.id.location_name);
			review = (TextView) view.findViewById(R.id.review);
			rating = (TextView) view.findViewById(R.id.rating);

			image = (ImageView) view.findViewById(R.id.location_image);

		} catch (ClassCastException e) {
			throw e;
		}

		FollowerProfileVenue item = getItem(position);
		Bitmap cachedImage = null;

		try {
			cachedImage = imageLoader.loadImage(item.getLocationImage(),
					new ImageLoadedListener() {
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
