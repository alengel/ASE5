package com.team5.courseassignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesEditor {
	
	
	//unique name for the SharedPreferences
	private final static String kSHARED_PREFERENCES_NAME = "DEFAULT_PREFERENCES";
	
	//settings names, must be unique
	private final static String kINTERVAL = "interval";
	private final static String kLOGGING_PERIOD = "logging_period";
	private final static String kDISTANCE = "distance";
	private final static String kLOCAL_STORAGE_LIMIT = "local_storage_limit";
	
	
	//store the access to the SharedPreferences
	private SharedPreferences kSharedPreferences;
		
		
	
	public SharedPreferencesEditor(Context context) {
		super();
		this.kSharedPreferences = context.getSharedPreferences(kSHARED_PREFERENCES_NAME, 0);
	}
	
	
	
	public int getInterval() {
		//TODO
		return -1;
	}
	
	public boolean setInterval(int newInterval) {
		//TODO
		
		Editor editor = kSharedPreferences.edit();
		//editor.put;
		editor.commit();
		
		return false;
	}
	
	public int getLoggingPeriod() {
		//TODO
		return -1;
	}
	
	public boolean setLoggingPeriod(int newLoggingPeriod) {
		//TODO
		
		Editor editor = kSharedPreferences.edit();
		//editor.put;
		editor.commit();
		
		return false;
	}
	
	public int getDistance() {
		//TODO
		return -1;
	}
	
	public boolean setDistance(int newDistance) {
		//TODO
		
		Editor editor = kSharedPreferences.edit();
		//editor.put;
		editor.commit();
		
		return false;
	}
	
	public int getLocalStorageLimit() {
		//TODO
		return -1;
	}
	
	public boolean setLocalStorageLimit() {
		//TODO
		
		Editor editor = kSharedPreferences.edit();
		//editor.put;
		editor.commit();
		
		return false;
	}
	
}
