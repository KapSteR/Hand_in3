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
	
	
	@Override
	public void onCreate() {
		getTextForecast("ostjylland");
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
	
	public String getTextForecast(String region) {
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
	        reader = new BufferedReader(new InputStreamReader(url.openStream(), "ISO-8859-1"));
	        
	        for(int i = 1; i<678 ;i++){ //skip first 677 lines
	        	reader.readLine();
	        }
	        builder.append(reader.readLine().trim()); //Line 678 is the weather Forecast
	        
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
	    }
	    
	    try{
	    String start = "<td class=\"broedtekst\"><td>";
	    String end = "</td>";
	    String part = builder.substring(builder.indexOf(start) + start.length() + 1);
	    String question = part.substring(0, part.indexOf(end));   
	    Log.d(TAG, question);
	    
	    return question;
	    
	    } catch(Exception e){
	    	Log.d(TAG,e.toString());
	    }
		return null;
	    
	}
}

