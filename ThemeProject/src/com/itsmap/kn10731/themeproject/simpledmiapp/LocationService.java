package com.itsmap.kn10731.themeproject.simpledmiapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class LocationService extends Service {

	private static final String TAG = "LocationService";
	private final String downloadIntentString = "com.kn10731.themeproject.simpledmiapp.downloadIntentString";
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	Location location; // location
	protected LocationManager locationManager;
	private String by;
	private String postnr;
	private String region;

	private Runnable downloadTask = new Runnable() {
		private final String POSTNUMRE = "postnumre";
		private final String POLITIKREDSE = "politikredse";

		public void run() {
			if (location != null) {
				String latitude = String.valueOf(location.getLatitude());
				String longitude = String.valueOf(location.getLongitude());

				JSONObject object = getGeoData(latitude, longitude, POSTNUMRE);
				if (object != null) {
					parsePostnumre(object);
				}

				object = getGeoData(latitude, longitude, POLITIKREDSE);
				if (object != null) {
					parseRegion(object);
				}
				if (object != null) {
					Intent intent = new Intent(downloadIntentString);
					intent.putExtra("By", by);
					intent.putExtra("Postnr", postnr);
					intent.putExtra("Region", region);
					LocalBroadcastManager.getInstance(getBaseContext())
							.sendBroadcast(intent);
				}
			}
		}

		public JSONObject getGeoData(String lat, String lng, String type) {
			// Takes types "postnumre" or "politikredse"

			URI myURI = null;

			try {
				myURI = new URI("http://geo.oiorest.dk/" + type + "/" + lat
						+ "," + lng + ".json");
			} catch (URISyntaxException e) {
				Log.d(TAG, e.toString());
			}
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(myURI);
			// Depends on your web service
			Log.d(TAG, myURI.getPath());

			InputStream inputStream = null;
			String result = null;
			HttpResponse response = null;
			try {
				response = httpclient.execute(getMethod);
			} catch (ClientProtocolException e) {
				Log.d(TAG, e.toString());
			} catch (IOException e) {
				Log.d(TAG, e.toString());
			}

			if (response == null) {
				Log.d(TAG, "HttpResponse is null");
				// TODO: Internet connection must be enabled!!
				// UnknownHostException
				return null;
			}

			HttpEntity entity = response.getEntity();

			try {
				inputStream = entity.getContent();
			} catch (IllegalStateException e) {
				Log.d(TAG, e.toString());
			} catch (IOException e) {
				Log.d(TAG, e.toString());
			}
			// json is UTF-8 by default i believe
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(inputStream,
						"UTF-8"), 8);
			} catch (UnsupportedEncodingException e) {
				Log.d(TAG, e.toString());
			}
			StringBuilder sb = new StringBuilder();

			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				Log.d(TAG, e.toString());
			}
			result = sb.toString();
			Log.d(TAG, result);

			JSONObject jObject = null;
			try {
				jObject = new JSONObject(result);
			} catch (JSONException e) {
				Log.d(TAG, e.toString());
			}

			return jObject;
		}

		public void parsePostnumre(JSONObject jObject) {
			try {
				by = jObject.getString("navn");
				postnr = jObject.getString("fra");
				Log.d(TAG, "By: " + by + ". Postnr: " + postnr);
			} catch (JSONException e) {
			}

		}

		public void parseRegion(JSONObject jObject) {
			int index = 0;
			try {
				index = Integer.parseInt(jObject.getString("nr"));
			} catch (JSONException e) {
			}

			switch (index) {
			case 1:
				region = getString(R.string.nordj);
				break;
			case 10:
				region = getString(R.string.kbh);
				break;
			case 11:
				region = getString(R.string.kbh);
				break;
			case 12:
				region = getString(R.string.born);
				break;
			case 2:
				region = getString(R.string.ostj);
				break;
			case 3:
				region = getString(R.string.midtj);
				break;
			case 4:
				region = getString(R.string.sydj);
				break;
			case 5:
				region = getString(R.string.sydj);
				break;
			case 6:
				region = getString(R.string.fyn);
				break;
			case 7:
				region = getString(R.string.vestsj);
				break;
			case 8:
				region = getString(R.string.midtj);
				break;
			case 9:
				region = getString(R.string.kbh);
				break;
			default:
				Log.d(TAG, "Errornous region number");
			}
			Log.d(TAG, "Region: " + region);
		}
	};

	private Location getLocation() {
		// Get the location manager
		try {
			locationManager = (LocationManager) getApplicationContext()
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				this.canGetLocation = true;
				// First get location from Network Provider
				if (isNetworkEnabled) {
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						Log.d(TAG, "Getting location with Network");
					}
				}
				// if GPS Enabled get location using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							Log.d(TAG, "Getting location with GPS");
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}

	public void printLocation(Location location) {
		String lat = String.valueOf(location.getLatitude());
		String lng = String.valueOf(location.getLongitude());
		Log.d(TAG, "Lat: " + lat + ". Lng: " + lng);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");

		location = getLocation();
		if (location != null) {
			printLocation(location);
		}

		runBackgroundThread();
	}

	private void runBackgroundThread() {
		Thread backgroundThread = new Thread(downloadTask) {
			@Override
			public void run() {
				try {
					downloadTask.run();
				} finally {
				}
			}
		};
		backgroundThread.start();
	}

}
