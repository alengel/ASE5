package com.team5.courseassignment;

import java.net.MalformedURLException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team5.courseassignment.ImageLoader.ImageLoadedListener;

public class ProfileListAdapter extends ArrayAdapter<UserFollowers> {
	private int resourceId = 0;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = new ImageLoader();
	@SuppressWarnings("unused")
	private Context context;

	public ProfileListAdapter(Context context, int resourceId,
			List<UserFollowers> mediaItems) {
		super(context, 0, mediaItems);
		this.resourceId = resourceId;
		this.context = context;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view;
		TextView textTitle;
		TextView textTitle1;

		final ImageView image;

		view = inflater.inflate(resourceId, parent, false);

		try {
			textTitle = (TextView) view.findViewById(R.id.firstName);
			textTitle1 = (TextView) view.findViewById(R.id.lastName);

			/*
			 * final CheckBox unfollow = (CheckBox)view.
			 * findViewById(R.id.unfollow);
			 * 
			 * 
			 * unfollow.setOnCheckedChangeListener(new OnCheckedChangeListener()
			 * { public void onCheckedChanged(CompoundButton buttonView, boolean
			 * isChecked) {
			 * 
			 * 
			 * 
			 * if ( isChecked) {
			 * 
			 * buttonView.setText("Unfollow");
			 * 
			 * //TODO /** String selectedText = voteUp.getText().toString();
			 * Intent i = new Intent(this, VenueReview.class);
			 * i.putExtra("cakedata", selectedText); startActivity(i);
			 * ////////////////////
			 * 
			 * 
			 * } else { buttonView.setText("Follow") }
			 * 
			 * 
			 * 
			 * }
			 * 
			 * });
			 */

			image = (ImageView) view.findViewById(R.id.profile_image);
			image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					/**
					 * TODO // launch ProfilePageActivity Intent i = new
					 * Intent(getApplicationContext(),
					 * ProfilePageActivity.class); // need to create comments
					 * activity
					 * 
					 * i.putExtra(KEY_JSON, kKey); //need to know api call for
					 * it i.putExtra(user_NAME, user_id);// i.putExtra(VENUE_ID,
					 * venueId);//
					 * 
					 * startActivity(i);
					 */
				}
			});

		} catch (ClassCastException e) {
			// Log.e(TAG,
			// "Your layout must provide an image and a text view with ID's icon and text.",
			// e);
			throw e;
		}

		UserFollowers item = getItem(position);
		Bitmap cachedImage = null;
		try {
			cachedImage = imageLoader.loadImage(item.getProfileImage(),
					new ImageLoadedListener() {
						public void imageLoaded(Bitmap imageBitmap) {
							image.setImageBitmap(imageBitmap);
							notifyDataSetChanged();
						}
					});
		} catch (MalformedURLException e) {
			// Log.e(TAG, "Bad remote image URL: " + item.getProfileImage(), e);
		}

		textTitle.setText(item.getName());
		textTitle1.setText(item.getLastName() + " ");

		if (cachedImage != null) {
			image.setImageBitmap(cachedImage);
		}

		return view;
	}

}
