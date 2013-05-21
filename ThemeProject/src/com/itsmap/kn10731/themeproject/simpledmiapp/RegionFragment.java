package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RegionFragment extends Fragment {

	private static final String TAG = "RegionFragment.class";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG,"OnCreate");
		return inflater.inflate(R.layout.fragment_region, container, false);
	}

	public void setRegionBitmap(Bitmap regionBitmap) {
		ImageView imageView = (ImageView)getView().findViewById(R.id.imageView1); 
		imageView.setImageBitmap(regionBitmap);
	}
	
	public void setTextViev(String text){
		View view = getView();
		Log.d(TAG,"View = null:" + String.valueOf(view == null));
		TextView textView = (TextView) getView().findViewById(R.id.regionTextView);
//		Log.d(TAG, String.valueOf(textView == null));
		textView.setText(text);
	}


}
