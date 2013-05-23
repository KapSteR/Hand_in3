package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.app.Activity;
import android.app.Application;

public class DMIApplication extends Application {
	public void onCreate() {
		super.onCreate();
	}

	private Activity mCurrentActivity = null;

	public Activity getCurrentActivity() {
		return mCurrentActivity;
	}

	public void setCurrentActivity(Activity mCurrentActivity) {
		this.mCurrentActivity = mCurrentActivity;
	}
}