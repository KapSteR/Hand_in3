package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "MainActivity";

	private Bitmap twoDayBitmap, nineDayBitmap, fifteenDayBitmap;

	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "Received broadcast");

			int index = intent.getExtras().getInt(LocationService.INDEX);
			switch (index) {
			case LocationService.INDEX_REGION:
				Log.d(TAG, "Index is region");
				FragmentManager fragMang = getSupportFragmentManager();
				fragMang.beginTransaction()
						.replace(R.id.frameLayout, new RegionFragment(),
								"regionFragment").commit();
				fragMang.executePendingTransactions();
				Log.d(TAG, "1");
				RegionFragment regionFragment = (RegionFragment) fragMang
						.findFragmentByTag("regionFragment");

				regionFragment.setTextViev(intent.getExtras().getString(
						LocationService.FORECAST_TEXT));
				Log.d(TAG, "2");
				regionFragment.setRegionBitmap((Bitmap) intent
						.getParcelableExtra(LocationService.FORECAST_BITMAP));
				fragMang.executePendingTransactions();

				Log.d(TAG, "3");
				break;
			case LocationService.INDEX_CITY:
				Log.d(TAG, "Index is city");
				twoDayBitmap = (Bitmap) intent
						.getParcelableExtra(LocationService.TWO_DAY_BITMAP);
				nineDayBitmap = (Bitmap) intent
						.getParcelableExtra(LocationService.NINE_DAY_BITMAP);
				fifteenDayBitmap = (Bitmap) intent
						.getParcelableExtra(LocationService.FIFTEEN_DAY_BITMAP);
				break;
			default:
				Log.d(TAG, "Index not set");
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: check for savedInstance, and orientation.

		super.onCreate(savedInstanceState);
		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.frameLayout, new LoadingFragment(), "loadingFragment")
				.commit();
		getSupportFragmentManager().executePendingTransactions();

		setContentView(R.layout.activity_main);

		LocalBroadcastManager.getInstance(this).registerReceiver(
				mMessageReceiver,
				new IntentFilter(LocationService.BROADCAST_RECEIVER));

		updateButtonClick();
		feedbackClick();
		Intent intent = new Intent(getApplicationContext(),
				LocationService.class);
		startService(intent);
		Log.d(TAG, "onCreateMain");

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Show new fragment
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Intent intent = new Intent(getBaseContext(), CityActivity.class);
			intent.putExtra(LocationService.TWO_DAY_BITMAP, twoDayBitmap);
			intent.putExtra(LocationService.NINE_DAY_BITMAP, nineDayBitmap);
			intent.putExtra(LocationService.FIFTEEN_DAY_BITMAP,
					fifteenDayBitmap);
			Log.d(TAG, "Starting CityActivity");
			startActivity(intent);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO: stop service etc.
		Log.d(TAG, "OnDestroy");
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				mMessageReceiver);
		stopService(new Intent(getApplicationContext(), LocationService.class));
		super.onDestroy();
	}

	public void feedbackClick() {
		final TextView feedback = (TextView) findViewById(R.id.feedback);
		feedback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Fire intent to open e-mail app
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri
						.fromParts("mailto", getString(R.string.mail_addr),
								null));

				// emailIntent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
				startActivity(Intent
						.createChooser(emailIntent, "Send email..."));

			}
		});
	}

	public void updateButtonClick() {
		TextView updateButton = (TextView) findViewById(R.id.updateButton);
		updateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
	}
}
