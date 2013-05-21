package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class WeatherService extends Service {
	
	private static final String TAG = "Weather Service";
	private String forecastDate;
	private String forecastTime;
	private String forecastText;
	
	@Override
	public void onCreate() {
		Log.d(TAG,"OnCreate");
		super.onCreate();
	}

	public WeatherService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	
}

