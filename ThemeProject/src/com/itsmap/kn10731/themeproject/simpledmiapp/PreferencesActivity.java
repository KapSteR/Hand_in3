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
		updateCityPreference(getString(R.string.pref_use_location));
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

	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		updateCityPreference(key);
	}

	@SuppressWarnings("deprecation")
	private void updateCityPreference(String key) {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		if (key.equals(getString(R.string.pref_use_location))) {

			if (sharedPrefs.getBoolean(getString(R.string.pref_use_location),
					false)) {
				findPreference(getString(R.string.pref_default_city))
						.setEnabled(false);
				findPreference(getString(R.string.pref_default_region))
						.setEnabled(false);
			} else {
				findPreference(getString(R.string.pref_default_city))
						.setEnabled(true);
				findPreference(getString(R.string.pref_default_region))
						.setEnabled(true);
			}
		} else if (key.equals(getString(R.string.pref_default_city))) {
			findPreference(getString(R.string.pref_default_city))
					.setSummary(
							getString(R.string.pref_default_city_summary));
		}
		
		String defaultCity = sharedPrefs.getString(getString(R.string.pref_default_city),"-1");
		String defaultRegion = sharedPrefs.getString(getString(R.string.pref_default_region),"-1");
		
		if(!defaultCity.equals("-1")){
			findPreference(getString(R.string.pref_default_city))
			.setSummary(
					getString(R.string.pref_default_city_summary) + " " + defaultCity);
		} else {
			findPreference(getString(R.string.pref_default_city))
			.setSummary(
					getString(R.string.pref_default_city_summary_choose));
		}
		if(!defaultRegion.equals("-1")){
			Position position = new Position(getBaseContext());
			position.setRegion(Integer.valueOf(defaultRegion));
			
			findPreference(getString(R.string.pref_default_region))
			.setSummary(
					getString(R.string.pref_default_region_summary) + " " + position.getRegion());
		} else {
			findPreference(getString(R.string.pref_default_region))
			.setSummary(
					getString(R.string.pref_default_region_summary_choose));
		}
	}
}
