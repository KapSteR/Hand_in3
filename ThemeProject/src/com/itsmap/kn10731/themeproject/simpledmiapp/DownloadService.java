package com.itsmap.kn10731.themeproject.simpledmiapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class DownloadService extends Service {

	private static final String TAG = "DownloadService";

	public static final String BROADCAST_RECEIVER_MAIN = "com.kn10731.themeproject.simpledmiapp.downloadIntentMain";
	public static final String BROADCAST_RECEIVER_CITY = "com.kn10731.themeproject.simpledmiapp.downloadIntentCity";
	public static final String FORECAST_TEXT = "RegionText";
	public static final String FORECAST_BITMAP = "RegionBitmap";
	public static final String TWO_DAY_BITMAP = "TwoDayBitmap";
	public static final String NINE_DAY_BITMAP = "NineDayBitmap";
	public static final String FIFTEEN_DAY_BITMAP = "FifteenDayBitmap";
	public static final String REGION = "Region";
	public static final String CONNECTION_ERROR = "ConnectionError";
	public static final String TEXT_CONNECTION_ERROR = "ConnectionError";

	protected LocationManager locationManager;
	private boolean isGPSEnabled = false;
	private boolean isNetworkEnabled = false;
	private Location location;

	public Position position = new Position(this);

	private Thread backgroundThread;

	private Runnable downloadTask = new Runnable() {
		private static final String POSTAL_CODE = "postnumre";
		private static final String POLICE_COMMUNITY = "politikredse";

		public void run() {
			try {
				if (location != null) {

					// Usersettings
					SharedPreferences sharedPrefs = PreferenceManager
							.getDefaultSharedPreferences(getApplication());

					boolean useCurrentLocation = sharedPrefs.getBoolean(
							getString(R.string.pref_use_location), true);

					if (!useCurrentLocation) {
						position.setPostCode(sharedPrefs.getString(
								getString(R.string.pref_default_city), "-1"));
						position.setRegion(Integer.valueOf(sharedPrefs
								.getString(
										getString(R.string.pref_default_region),
										"-1")));
						Log.d(TAG, sharedPrefs.getString(
								getString(R.string.pref_default_city),
								"default"));
					} else {

						String latitude = String
								.valueOf(location.getLatitude());
						String longitude = String.valueOf(location
								.getLongitude());

						// Get data for Region
						JSONObject jObject = getGeoData(latitude, longitude,
								POLICE_COMMUNITY);
						if (jObject != null) {
							parseRegion(jObject);
						}

						jObject = getGeoData(latitude, longitude, POSTAL_CODE);
						if (jObject != null) {
							parsePostalCode(jObject);
						}
					}
					downloadRegionInfo();
					downloadCityInfo();
				}
			} catch (InterruptedException e) {
				Log.d(TAG, "downloadTask: " + e.toString());
				stopSelf();
			}

		}

		private void downloadCityInfo() throws InterruptedException {
			String postalCode = position.getPostCode();
			if (postalCode == null) {
				Log.d(TAG, "postalCode is not set");
				return;
			}

			downloadAndSaveCityBitmaps(
					postalCode,
					PreferenceManager.getDefaultSharedPreferences(
							getApplication()).getBoolean(
							getString(R.string.pref_uncertainty), true));

			Activity currentActivity = ((DMIApplication) getApplicationContext())
					.getCurrentActivity();

			if (currentActivity == null) {
				Log.d(TAG, "currentActivity is not set");
				return;
			}
			Log.d(TAG, "Activity: "
					+ currentActivity.getComponentName().getClassName());

			if (currentActivity.getClass().equals(CityActivity.class)) {
				Log.d(TAG, "Current activity is CityActivity");
				Intent intent = new Intent(BROADCAST_RECEIVER_CITY);
				LocalBroadcastManager.getInstance(getBaseContext())
						.sendBroadcast(intent);
			}
		}

		private void downloadRegionInfo() throws InterruptedException {
			if (position.getRegion() != null) {
				String foreCastText;
				Bitmap forecastBitmap;

				foreCastText = getTextForecast(position.getTextName());
				if (foreCastText == null) {
					foreCastText = getString(R.string.forecastTextError);
				}

				forecastBitmap = downlaodBitmap("http://www.dmi.dk/dmi/femdgn_"
						+ position.getPngName() + ".png");
				if (forecastBitmap == null) {
					Log.d(TAG, "Bitmap is null");
				}

				Intent intent = new Intent(BROADCAST_RECEIVER_MAIN);
				intent.putExtra(REGION, position.getRegion());
				intent.putExtra(FORECAST_TEXT, foreCastText);
				intent.putExtra(FORECAST_BITMAP, forecastBitmap);
				LocalBroadcastManager.getInstance(getBaseContext())
						.sendBroadcast(intent);
			} else {
				Log.d(TAG, "region is null or empty.");
			}
		}

		private void downloadAndSaveCityBitmaps(String postalCode,
				boolean showUncertanties) throws InterruptedException {
			String twoDayUrl, nineDayUrl, fifteenDayUrl;
			Bitmap twoDayBitmap, nineDayBitmap, fifteenDayBitmap;

			if (showUncertanties == true) {
				twoDayUrl = "http://servlet.dmi.dk/byvejr/servlet/byvejr_dag1?by="
						+ postalCode + "&mode=long&eps=true";
				nineDayUrl = "http://servlet.dmi.dk/byvejr/servlet/byvejr?by="
						+ postalCode + "&tabel=dag3_9&eps=true";
				fifteenDayUrl = "http://servlet.dmi.dk/byvejr/servlet/byvejr?by="
						+ postalCode + "&tabel=dag10_15&eps=true";

			} else {
				twoDayUrl = "http://servlet.dmi.dk/byvejr/servlet/byvejr_dag1?by="
						+ postalCode + "&mode=long";
				nineDayUrl = "http://servlet.dmi.dk/byvejr/servlet/byvejr?by="
						+ postalCode + "&tabel=dag3_9";
				fifteenDayUrl = "http://servlet.dmi.dk/byvejr/servlet/byvejr?by="
						+ postalCode + "&tabel=dag10_15";
			}
			twoDayBitmap = downlaodBitmap(twoDayUrl);
			nineDayBitmap = downlaodBitmap(nineDayUrl);
			fifteenDayBitmap = downlaodBitmap(fifteenDayUrl);

			FileOutputStream out;
			try {
				out = new FileOutputStream(MainActivity.tmpFile);
				twoDayBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				nineDayBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				fifteenDayBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
				Log.d(TAG, "Bitmaps save in cache file.");
			} catch (FileNotFoundException e) {
				Log.d(TAG, e.toString());
			} catch (IOException e) {
				Log.d(TAG, e.toString());
				sendErrorBroadcast(getString(R.string.internet_connection_error));
				throw new InterruptedException();

			}
		}

		private String getTextForecast(String region)
				throws InterruptedException {

			URL url = null;
			try {
				url = new URL(
						"http://www.dmi.dk/dmi/index/danmark/regionaludsigten/"
								+ region + ".htm");
			} catch (MalformedURLException e) {
				Log.d(TAG, e.toString());
			}
			BufferedReader reader = null;
			StringBuilder builder = new StringBuilder();
			try {
				reader = new BufferedReader(new InputStreamReader(
						url.openStream(), "ISO-8859-1"));

				for (int i = 1; i < 678; i++) { // skip first 677 lines
					reader.readLine();
				}
				builder.append(reader.readLine().trim()); // Line 678 is the
															// weather Forecast
				builder.append(reader.readLine().trim()); // Or line 679 is the
															// weather Forecast

			} catch (UnsupportedEncodingException e) {
				Log.d(TAG, e.toString());
			} catch (IOException e) {
				Log.d(TAG, e.toString());
				sendErrorBroadcast(getString(R.string.internet_connection_error));
				throw new InterruptedException();
			} finally {
				if (reader != null)
					try {
						reader.close();
					} catch (IOException e) {
						Log.d(TAG, e.toString());
					}
			}
			try {
				String start = "<td class=\"broedtekst\"><td>";
				String end = "</td>";
				if (builder == null) {
					Log.d(TAG, "Builder is null");
					return null;
				}
				String part = builder.substring(builder.indexOf(start)
						+ start.length() + 1);
				String forecast = part.substring(0, part.indexOf(end));

				// Replace wrongly encoded nordic characters
				forecast = forecast.replaceAll("&aelig;", "æ");
				forecast = forecast.replaceAll("&oslash;", "ø");
				forecast = forecast.replaceAll("&aring;", "å");

				return forecast;

			} catch (Exception e) {
				Log.d(TAG, e.toString());
			}
			return null;
		}

		private Bitmap downlaodBitmap(String urlString)
				throws InterruptedException {
			Bitmap bitmap = null;

			try {
				URL url = new URL(urlString);
				InputStream in = new BufferedInputStream(url.openStream());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int n = 0;
				while (-1 != (n = in.read(buf))) {
					out.write(buf, 0, n);
				}
				out.close();
				in.close();
				byte[] response = out.toByteArray();
				bitmap = BitmapFactory.decodeByteArray(response, 0,
						response.length);
				return bitmap;
			} catch (IOException e) {
				Log.d(TAG, e.toString());
				sendErrorBroadcast(getString(R.string.internet_connection_error));
				throw new InterruptedException();
			}
		}

		public JSONObject getGeoData(String lat, String lng, String type)
				throws InterruptedException {
			// Takes types "postnumre" or "politikredse"

			URI myURI = null;

			try {
				myURI = new URI("http://geo.oiorest.dk/" + type + "/" + lat
						+ "," + lng + ".json");
			} catch (URISyntaxException e) {
				Log.d(TAG, e.toString());
			}
			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is
			// established.
			// The default value is zero, that means the timeout is not used.
			int timeoutConnection = 2000;
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			int timeoutSocket = 4000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
			HttpGet getMethod = new HttpGet(myURI);
			// Depends on your web service

			InputStream inputStream = null;
			String result = null;
			HttpResponse response = null;
			try {
				response = httpclient.execute(getMethod);
			} catch (ClientProtocolException e) {
				Log.d(TAG, e.toString());
			} catch (SocketTimeoutException e) {
				Log.d(TAG, e.toString());
			} catch (IOException e) {
				Log.d(TAG, e.toString());
			}

			if (response == null) {
				Log.d(TAG, "HttpResponse is null");
				sendErrorBroadcast(getString(R.string.internet_connection_error));
				throw new InterruptedException();
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

		public void parsePostalCode(JSONObject jObject) {
			try {
				position.setCity(jObject.getString("navn"));
				position.setPostCode(jObject.getString("fra"));
			} catch (JSONException e) {
			}
		}

		public void parseRegion(JSONObject jObject) {
			int index = 0;
			try {
				index = Integer.parseInt(jObject.getString("nr"));
			} catch (JSONException e) {
			}
			position.setRegion(index);
		}
	};

	private Location getLocation() {
		// Get the location manager
		try {
			SharedPreferences sharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(getApplication());

			boolean useGPS = sharedPrefs.getBoolean(
					getString(R.string.pref_gps_on_off), true);

			locationManager = (LocationManager) getApplicationContext()
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// TODO: no network provider is enabled
			} else {
				// First get location from Network Provider
				if (isNetworkEnabled) {
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						Log.d(TAG, "Getting location with Network");
					}
				}
				// if GPS Enabled get location using GPS Services
				if (isGPSEnabled && useGPS) {
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
			Log.d(TAG, e.toString());
		}
		return location;
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
			String lat = String.valueOf(location.getLatitude());
			String lng = String.valueOf(location.getLongitude());
			Log.d(TAG, "Lat: " + lat + ". Lng: " + lng);
		}

		backgroundThread = new Thread(downloadTask) {
			@Override
			public void run() {
				try {
					super.run();
				} catch (Exception e) {
					Log.d(TAG, "backgroundThread:" + e.toString());
				} finally {
					stopSelf();
					Log.d(TAG, "DownloadService stopped.");

				}
			}
		};
		backgroundThread.start();
	}

	public File getTempFile(Context context, String url) {
		File file;
		try {
			String fileName = Uri.parse(url).getLastPathSegment();
			file = File.createTempFile(fileName, null, context.getCacheDir());
			return file;
		} catch (IOException e) {
			Log.d(TAG, "Could not create file.");
			return null;
		}
	}

	private void sendErrorBroadcast(String errorText) {
		Intent intent = new Intent(BROADCAST_RECEIVER_MAIN);
		intent.putExtra(CONNECTION_ERROR, "Error");
		intent.putExtra(TEXT_CONNECTION_ERROR, errorText);
		LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(
				intent);
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "OnDestroy");
		backgroundThread.interrupt();
		super.onDestroy();
	}
}
