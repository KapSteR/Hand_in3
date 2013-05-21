package com.itsmap.kn10731.themeproject.simpledmiapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class WeatherService extends Service {
	
	private static final String TAG = "Weather Service";
	private String forecastDate;
	private String forecastTime;
	private String forecastText;
	
	
	public WeatherService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	
	
	public void getTextForecast(String region) {
		URL url = null;
		try {
			url = new URL("http://www.dmi.dk/dmi/index/danmark/regionaludsigten/" + region +".htm");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    BufferedReader reader = null;
	    StringBuilder builder = new StringBuilder();
	    try {
	        reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
	        for (String line; (line = reader.readLine()) != null;) {
	            builder.append(line.trim());
	        }
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
	    }

	    String start = "<div class=\"post-text\"><p>";
	    String end = "</p>";
	    String part = builder.substring(builder.indexOf(start) + start.length());
	    String question = part.substring(0, part.indexOf(end));
	    Log.d(TAG, question);
	}
}
