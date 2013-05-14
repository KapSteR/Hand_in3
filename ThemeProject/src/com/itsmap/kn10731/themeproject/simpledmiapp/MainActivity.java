package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		feedbackClick();

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
						.fromParts("mailto", getString(R.string.mail_addr), null));
				
				// emailIntent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
				startActivity(Intent
						.createChooser(emailIntent, "Send email..."));

			}
		});
	}

}
