package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RegionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_region);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.region, menu);
		return true;
	}
	
	// TODO fix this after figuring out location
	// TODO save appropriate links in string resources
	/*
	public void drawForecastRegion() {
		ImageView forecastRegionIv = new ImageView;

		URL url = new URL(address);
		InputStream content = (InputStream)url.getContent();
		Drawable d = Drawable.createFromStream(content , "src"); 
		iv.setImageDrawable(d)
		
	}
*/
}

