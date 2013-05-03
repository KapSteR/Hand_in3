package itsmap.f13.stud10731.hand_in3;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;

public class ContentActivity extends FragmentActivity {
	
	private String TAG = "ContentActivity.class";
	private ContentFragment contentFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		
		if (findViewById(R.id.content_fragment_container) != null) {
	        
        	if (savedInstanceState != null) { 
                return;
            }
        	
	        FragmentManager fragmentManager = getSupportFragmentManager();
	        FragmentTransaction fragTrans = fragmentManager.beginTransaction();
	        contentFragment = new ContentFragment();
	        fragTrans.add(R.id.content_fragment_container, contentFragment);
	        fragTrans.commit();
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content, menu);
		return true;
	}

	@Override
	protected void onResume() {
		if(contentFragment == null){
			Log.d(TAG,"content fragment not found");
		} else {
			contentFragment.setText(getIntent().getStringExtra("item"));
		}
		super.onResume();
	}
}
