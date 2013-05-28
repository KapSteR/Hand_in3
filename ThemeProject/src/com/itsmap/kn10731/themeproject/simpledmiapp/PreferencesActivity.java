package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class PreferencesActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	private static final String TAG = "PreferencesActivity.class";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		// Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(
						(OnSharedPreferenceChangeListener) this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		super.onPause();
		// Unregister the listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(
						(OnSharedPreferenceChangeListener) this);
	}

	@SuppressWarnings("deprecation")
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		if (key.equals(getString(R.string.pref_use_location))) {

			if (sharedPrefs.getBoolean(getString(R.string.pref_use_location),
					false)) {
				findPreference(getString(R.string.pref_default_city))
						.setEnabled(false);
			} else {
				findPreference(getString(R.string.pref_default_city))
						.setEnabled(true);
			}
		} else if (key.equals(getString(R.string.pref_default_city))) {
			int postalCode;
			
			try{
				postalCode = Integer.parseInt(sharedPrefs.getString(
					getString(R.string.pref_default_city), "-1"));
			} catch (Exception e) {
				Log.d(TAG,e.toString());
			}
		}
	}
}
