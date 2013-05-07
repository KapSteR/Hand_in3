package itsmap.f13.stud10731.hand_in3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContentFragment extends Fragment {

	private String TAG = "ContentFragment.class";

	TextView textView;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View fragmentContainerDualView = getActivity().findViewById(
				R.id.content_fragment_container_large);
		boolean isDualPane = (fragmentContainerDualView != null && fragmentContainerDualView
				.getVisibility() == View.VISIBLE);

		View parent;
		if (isDualPane) {
			parent = fragmentContainerDualView;
		} else {
			parent = getActivity()
					.findViewById(R.id.content_fragment_container);
		}
		// Creating layout and textView runtime:
		ViewGroup layout = new LinearLayout(getActivity()
				.getApplicationContext());
		layout.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		if (textView == null) {
			textView = new TextView(getActivity().getApplicationContext());
		}

		// Reloading text into textView, if this has been saved.
		if (savedInstanceState == null) {
			Log.d(TAG, "Bundle is null or does not contain itemText.");
		} else {
			if (savedInstanceState.containsKey("itemText")) {
				String item = (String) savedInstanceState
						.getCharSequence("itemText");
				if (item != null) {
					textView.setText(item);
				}
			}
		}

		layout.addView(textView);
		((ViewGroup) parent).addView(layout);
		Log.d(TAG, "ContentFragment onCreate finished.");
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "OnDestroy");
		super.onDestroy();
	}

	@Override
	public void onPause() {
		Log.d(TAG, "OnPause");
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.d(TAG, "OnResume");
		super.onResume();
	}

	// Saving text, (because I can), so textView is not cleared when orientation
	// changed.
	// (Could also have been implemented, by making sure that fragment is not
	// recreated on change.)
	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (textView != null) {
			outState.putCharSequence("itemText", textView.getText());
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onStart() {
		Log.d(TAG, "OnStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.d(TAG, "OnStop");
		super.onStop();
	}

	public void setText(String item) {
		if (textView == null) {
			Log.d(TAG, "TextView is null");
			textView = new TextView(getActivity().getApplicationContext());
			textView.setText(item);
			Log.d(TAG, "textView set = " + item);
		} else {
			textView.setText(item);
			Log.d(TAG, "textView set = " + item);
		}
	}
}
