package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private final String downloadIntentString = "com.kn10731.themeproject.simpledmiapp.downloadIntentString";

	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "Received broadcast");
			// TODO: Update something..
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: check for savedInstance, and orientation.
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LocalBroadcastManager.getInstance(this).registerReceiver(
				mMessageReceiver, new IntentFilter(downloadIntentString));

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

}
