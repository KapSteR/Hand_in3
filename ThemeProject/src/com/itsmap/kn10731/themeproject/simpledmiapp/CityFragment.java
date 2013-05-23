package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CityFragment extends Fragment{

	private static final String TAG = "CityFragment.class";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG,"OnCreate");
		return inflater.inflate(R.layout.fragment_region, container, false);
	}

	public void setRegionBitmap(Bitmap bitmap) {
//		ImageView imageView = (ImageView)getView().findViewById(R.id.); 
//		imageView.setImageBitmap(bitmap);
	}
}
