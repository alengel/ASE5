package com.team5.courseassignment.utilities;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.team5.courseassignment.R;

/*
 * IMPORTANT NOTICE: We are only using DEBUG certificates as signature for the Google Maps API !!!
 */

public class SettingsFragment extends PreferenceFragment {

	/**
	 * Called when the activity is first created. This is where we do all of our
	 * normal static set up: create views, bind data to lists, etc. This method
	 * also provides a Bundle containing the activity's previously frozen state,
	 * if there was one.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.layout.preferences);
	}
}
