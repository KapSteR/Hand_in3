package itsmap.f13.stud10731.hand_in3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContentFragment extends Fragment {
	
	TextView textView;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		View parent = getActivity().findViewById(R.id.content_fragment_container);
		//Creating layout and textView runtime:
		ViewGroup layout=new LinearLayout(getActivity().getApplicationContext());
	    layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
	    
		textView = new TextView(getActivity().getApplicationContext());
		textView.setText("hey");
		
		layout.addView(textView);
		((ViewGroup) parent).addView(layout);
	}
	
}
