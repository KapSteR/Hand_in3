package com.itsmap.kn10731.themeproject.simpledmiapp;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

public class CityActivity extends Activity {

	private static final String TAG = "CityActivity.class";
	protected DMIApplication mDMIApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "Oncreate");
		mDMIApplication = (DMIApplication) this.getApplicationContext();
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_city);

		if (MainActivity.tmpFile != null) {
			showBitmaps();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		clearActivityReference();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");
		clearActivityReference();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				mMessageReceiver);
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume");
		mDMIApplication.setCurrentActivity(this);
		LocalBroadcastManager.getInstance(this).registerReceiver(
				mMessageReceiver,
				new IntentFilter(getString(R.string.broadcast_receiver_city)));
		super.onResume();
	}

	private void clearActivityReference() {
		Activity currentActivity = mDMIApplication.getCurrentActivity();
		if (currentActivity != null && currentActivity.equals(this))
			mDMIApplication.setCurrentActivity(null);
	}

	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "Received broadcast");
			showBitmaps();
		}
	};

	private void showBitmaps() {
		FileInputStream in;
		Bitmap twoDayBitmap, nineDayBitmap, fifteenDayBitmap;
		try {
			in = new FileInputStream(MainActivity.tmpFile);
			BufferedInputStream bin = new BufferedInputStream(in);

			twoDayBitmap = BitmapFactory.decodeStream(bin);
			nineDayBitmap = BitmapFactory.decodeStream(bin);
			fifteenDayBitmap = BitmapFactory.decodeStream(bin);

			ImageView imageView = (ImageView) findViewById(R.id.imageViewTwo);
			imageView.setImageBitmap(twoDayBitmap);
			imageView = (ImageView) findViewById(R.id.imageViewNine);
			imageView.setImageBitmap(nineDayBitmap);
			imageView = (ImageView) findViewById(R.id.imageViewFifteen);
			imageView.setImageBitmap(fifteenDayBitmap);

		} catch (FileNotFoundException e) {
			Log.d(TAG, e.toString());
		}
	}
}
