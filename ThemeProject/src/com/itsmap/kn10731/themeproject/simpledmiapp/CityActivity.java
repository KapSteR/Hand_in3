package com.itsmap.kn10731.themeproject.simpledmiapp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class CityActivity extends Activity {

	private static final String TAG = "CityActivity.class";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "Oncreate");
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_city);

		// final View contentView = findViewById(R.id.cityFragment);

		FileInputStream in;
		Bitmap bitmap;
		try {
			in = new FileInputStream(new File(getCacheDir(),
					LocationService.TEMP_FILE_URL));
			BufferedInputStream bin = new BufferedInputStream(in);

			bitmap = BitmapFactory.decodeStream(bin);
			Log.d(TAG, "Bitmap read from cache file.");

			ImageView imageView = (ImageView) findViewById(R.id.imageViewTwo);
			imageView.setImageBitmap(bitmap);

		} catch (FileNotFoundException e) {
			Log.d(TAG, e.toString());
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			finish();
		}
	}
}
