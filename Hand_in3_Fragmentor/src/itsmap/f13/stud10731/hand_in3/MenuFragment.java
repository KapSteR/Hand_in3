package itsmap.f13.stud10731.hand_in3;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends ListFragment {
	
//	public interface MenuFragmentCallback {
//		public void onMenuFragmentCallback();
//	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] values = new String[] { "Ceres", "Tuborg", "Carlsberg", "Royal", "Heineken"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String)getListAdapter().getItem(position);
		
	}
	
	
}
