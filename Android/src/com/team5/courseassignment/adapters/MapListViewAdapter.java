package com.team5.courseassignment.adapters;

import java.util.List;

import com.team5.courseassignment.R;
import com.team5.courseassignment.R.id;
import com.team5.courseassignment.data.FourSquareVenue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MapListViewAdapter extends ArrayAdapter<FourSquareVenue> {
	private int resourceId = 0;
	private LayoutInflater inflater;

	/**
	 * The constructor method of map list venue adapter.
	 * 
	 * @param context
	 *            - the context.
	 * @param resourceId
	 *            - id of the source - integer.
	 * @param mediaItems
	 *            - list of venues visited by follower with all media data.
	 */
	public MapListViewAdapter(Context context, int resourceId,
			List<FourSquareVenue> mediaItems) {
		super(context, 0, mediaItems);
		this.resourceId = resourceId;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * GetView method sets list view to have this custom adapter. It creates
	 * TextView's, binds list view adapter, changes style of layout of each row
	 * item.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view;
		TextView textTitle;
		TextView textTitle1;

		view = inflater.inflate(resourceId, parent, false);

		try {
			textTitle = (TextView) view.findViewById(R.id.name);
			textTitle1 = (TextView) view.findViewById(R.id.distance);

		} catch (ClassCastException e) {

			throw e;
		}

		FourSquareVenue item = getItem(position);

		textTitle.setText(item.getName());
		textTitle1.setText(item.getDistance() + "  meters from here");

		return view;
	}

}
