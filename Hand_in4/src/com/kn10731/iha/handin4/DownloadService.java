package com.kn10731.iha.handin4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class DownloadService extends Service {

	private final String downloadIntent = "com.kn10731.iha.handin4.downloadIntent";
	private final String TAG = "DownloadService";
	private final IBinder mBinder = new LocalBinder();
	ArrayList<String> sTogList = new ArrayList<String>();

	public class LocalBinder extends Binder {
		DownloadService getService() {
			return DownloadService.this;
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	public ArrayList<String> getDownloadedList() {
		return sTogList;
	}

	public void startDownload() {
		new DownloadTask().execute("");
	}

	public class DownloadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			URI myURI = null;

			try {
				myURI = new URI(
						"http://stog.itog.dk/itog/action/list/format/json");
			} catch (URISyntaxException e) {
				Log.d(TAG, e.toString());
			}
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(myURI);
			HttpResponse webServerResponse = null;
			try {
				webServerResponse = httpClient.execute(getMethod);
			} catch (ClientProtocolException e) {
				Log.d(TAG, e.toString());
			} catch (IOException e) {
				Log.d(TAG, e.toString());
			}

			if (webServerResponse == null) {
				return null;
			}

			HttpEntity httpEntity = webServerResponse.getEntity();

			if (httpEntity != null) {
				try {
					BufferedReader in = new BufferedReader(
							new InputStreamReader(httpEntity.getContent()));

					StringBuilder SB = new StringBuilder();
					String line;
					while ((line = in.readLine()) != null) {
						SB.append(line);
					}

					JSONArray ja = new JSONArray(SB.toString());
					sTogList.clear();
					for (int i = 0; i < ja.length(); i++) {
						JSONObject jo = (JSONObject) ja.get(i);
						sTogList.add(jo.getString("name"));
//						Log.d(TAG, jo.getString("name"));
					}
				} catch (IOException e) {
					Log.d(TAG, e.toString());
				} catch (JSONException e) {
					Log.d(TAG, e.toString());
				}
			}

			Intent intent = new Intent(downloadIntent);
			LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(
					intent);
			return null;
		}
	}
}