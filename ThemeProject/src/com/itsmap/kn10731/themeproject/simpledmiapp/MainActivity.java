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
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "MainActivity";
	public static final String FORECAST_TEXT = "forecastText";
	public static final String FORECAST_BITMAP = "forecastBitmap";
	private final String downloadIntentString = "com.kn10731.themeproject.simpledmiapp.downloadIntentString";

	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "Received broadcast");

			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.frameLayout, new RegionFragment(),
							"regionFragment").commit();
			getSupportFragmentManager().executePendingTransactions();

			RegionFragment regionFragment = (RegionFragment) getSupportFragmentManager()
					.findFragmentByTag("regionFragment");
			
			if(intent == null){Log.d(TAG,"intent is null");}
			String str = intent.getExtras().getString(FORECAST_TEXT);
			if(str == null){Log.d(TAG,"str is null");}
			Log.d(TAG,str);
			
			regionFragment.setTextViev(str);
//			regionFragment.setRegionBitmap((Bitmap)intent.getParcelableExtra("FORECAST_BITMAP"));
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

		setContentView(R.layout.activity_main);

		LocalBroadcastManager.getInstance(this).registerReceiver(
				mMessageReceiver, new IntentFilter(downloadIntentString));

		updateButtonClick();
		feedbackClick();
		Intent intent = new Intent(getApplicationContext(),
				LocationService.class);
		startService(intent);
		Log.d(TAG, "onCreateMain");

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Show new activity
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				mMessageReceiver);
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
