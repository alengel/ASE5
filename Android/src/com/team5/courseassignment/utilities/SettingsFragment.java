package com.team5.courseassignment.utilities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.team5.courseassignment.R;
import com.team5.courseassignment.activities.ProfileActivity;

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
	private String kKey;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.layout.preferences);
		
		// get the key
					kKey = SharedPreferencesEditor.getKey();
		
		Preference pref = (Preference) findPreference("pref_key_my_account");
		pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
		  @Override
		  public boolean onPreferenceClick(Preference preference) {
		    
			  
			 // startActivity(preference.getIntent(), kKey);
		    
		    
		    return true;
		  }
		});
	}
	
	
}
