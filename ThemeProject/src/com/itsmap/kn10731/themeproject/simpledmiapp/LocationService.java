package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service {

	private static final String TAG = "LocationService";
	private LocationManager locationManager;
	private String provider;
	private String lat;
	private String lng;

	private Runnable locationTask = new Runnable() {
		public void run() {
			// Get the location manager
			locationManager = (LocationManager) getBaseContext()
					.getSystemService(Context.LOCATION_SERVICE);
			// Define the criteria how to select the location provider -> use
			// default
			Criteria criteria = new Criteria();
						
			provider = locationManager.getBestProvider(criteria, false);
			if (provider != null) {
				Location location = locationManager
						.getLastKnownLocation(provider);

				if (location != null) {
					System.out.println("Provider " + provider
							+ " has been selected.");
					onLocationChanged(location);
					Log.d(TAG, "Lat: " + lat + " Lng: " + lng);
				} else {
					Log.d(TAG, "Failure");

				}
			} else {
				Log.d(TAG,"Provider is null");
			}
		}

		public void onLocationChanged(Location location) {
			String lat = String.valueOf(location.getLatitude());
			String lng = String.valueOf(location.getLongitude());
			Log.d(TAG, "Lat: " + lat + ". Lng: " + lng);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		getLocation();
		Log.d(TAG, "onCreate");
	}

	private void getLocation() {
		Thread backgroundThread = new Thread(locationTask) {
			@Override
			public void run() {
				try {
					locationTask.run();
				} finally {
				}
			}
		};
		backgroundThread.start();
	}

}
