package com.team5.courseassignment.utilities;

import android.content.Context;

public class PinMeApplication extends android.app.Application
{
	private static PinMeApplication instance;

	public PinMeApplication()
	{
		instance = this;
	}

	public static Context getContext()
	{
		return instance;
	}
}
