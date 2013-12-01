package com.team5.courseassignment.adapters;

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

import com.team5.courseassignment.ImageLoader;
import com.team5.courseassignment.R;
import com.team5.courseassignment.UserFollowers;
import com.team5.courseassignment.ImageLoader.ImageLoadedListener;
import com.team5.courseassignment.R.id;

public class ProfileListAdapter extends ArrayAdapter<UserFollowers> {
	private int resourceId = 0;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = new ImageLoader();
	@SuppressWarnings("unused")
	private Context context;

	/**
	 * The constructor method of profile list adapter.
	 * 
	 * @param context
	 *            - the context.
	 * @param resourceId
	 *            - id of the source - integer.
	 * @param mediaItems
	 *            - list of user followers with all media data.
	 */
	public ProfileListAdapter(Context context, int resourceId,
			List<UserFollowers> mediaItems) {
		super(context, 0, mediaItems);
		this.resourceId = resourceId;
		this.context = context;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * GetView method sets list view to have this custom adapter. It creates
	 * views (Text, Image), binds list view adapter, changes style of layout of
	 * each row item.
	 */
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
			 * String selectedText = voteUp.getText().toString(); Intent i = new
			 * Intent(this, VenueReview.class); i.putExtra("cakedata",
			 * selectedText); startActivity(i); ////////////////////
			 * 
			 * 
			 * } else { buttonView.setText("Follow") }
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

				}
			});

		} catch (ClassCastException e) {

			throw e;
		}

		UserFollowers item = getItem(position);
		Bitmap cachedImage = null;
		try {
			cachedImage = imageLoader.loadImage(item.getProfileImage(),
					new ImageLoadedListener() {
						@Override
						public void imageLoaded(Bitmap imageBitmap) {
							image.setImageBitmap(imageBitmap);
							notifyDataSetChanged();
						}
					});
		} catch (MalformedURLException e) {

		}

		textTitle.setText(item.getName());
		textTitle1.setText(item.getLastName() + " ");

		if (cachedImage != null) {
			image.setImageBitmap(cachedImage);
		}

		return view;
	}

}
