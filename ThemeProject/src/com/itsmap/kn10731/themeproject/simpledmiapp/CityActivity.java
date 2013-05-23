package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

public class CityActivity extends Activity {

	private static final String TAG = "CityActivity.class";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG,"Oncreate");
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_city);

//		final View contentView = findViewById(R.id.cityFragment);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			finish();
		}
	}
}
