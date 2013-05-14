package com.kn10731.iha.handin4;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.kn10731.iha.handin4.DownloadService.LocalBinder;

public class MainActivity extends ListActivity implements OnClickListener {

	private final String downloadIntent = "com.kn10731.iha.handin4.downloadIntent";
	private final String TAG = "MainActivity";
	private ArrayAdapter<String> adapter = null;
	private EditText filterText = null;
	DownloadService mService;
	boolean mBound = false;

	/** Defines callbacks for service binding, passed to bindService() */
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			// We've bound to LocalService, cast the IBinder and get
			// LocalService instance
			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();
			mBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mBound = false;
		}
	};

	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "Received broadcast");
			ArrayList<String> list = mService.getDownloadedList();

			if (!list.isEmpty()) {
				adapter = new ArrayAdapter<String>(getApplicationContext(),
						R.layout.list_layout, android.R.id.text1, list);
				setListAdapter(adapter);
			} else {
				Log.d(TAG, "List is empty.");
			}
		}
	};

	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (adapter != null) {
				adapter.getFilter().filter(s);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LocalBroadcastManager.getInstance(this).registerReceiver(
				mMessageReceiver, new IntentFilter(downloadIntent));

		Button updateButton = (Button) findViewById(R.id.button1);
		updateButton.setOnClickListener(this);

		filterText = (EditText) findViewById(R.id.editText1);
		filterText.addTextChangedListener(filterTextWatcher);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Bind to LocalService
		Intent intent = new Intent(this, DownloadService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onClick(View v) {
		Log.d(TAG, "Starting download");
		mService.startDownload();
	}

	@Override
	protected void onDestroy() {
		filterText.removeTextChangedListener(filterTextWatcher);
		unbindService(mConnection);
		super.onDestroy();
	}
}
