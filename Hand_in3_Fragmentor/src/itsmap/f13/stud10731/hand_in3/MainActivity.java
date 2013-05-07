package itsmap.f13.stud10731.hand_in3;

import itsmap.f13.stud10731.hand_in3.MenuFragment.OnMenuFragmentClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnMenuFragmentClickListener {
	
	private boolean isDualPane;
	private final String TAG = "MainActivity.class"; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        View frag = findViewById(R.id.content_fragment_container_large);
        isDualPane = (frag != null && frag.getVisibility() == View.VISIBLE);
        Log.d(TAG,"isDualPane = " + isDualPane);
        
        if (findViewById(R.id.menu_fragment_container) != null) {
        
        	if (savedInstanceState != null) {
                return;
            }
        	
//        	getSupportFragmentManager().beginTransaction()
//            .add(R.id.menu_fragment_container, new MenuFragment(),"menuFragment").commit();
	        
	        if(isDualPane) {
	        	getSupportFragmentManager().beginTransaction()
                .add(R.id.content_fragment_container_large, new ContentFragment(),"contentFragment").commit();
	        }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void onMenuFragmentClick(String item) {
		Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
		
		if(isDualPane) {
			ContentFragment contentFragment = (ContentFragment) getSupportFragmentManager().findFragmentByTag("contentFragment");
			if(contentFragment != null && item != null){
				contentFragment.setText(item);
			} else {
				Log.d(TAG,"contentFragment or item is null.");
			}	
			
		} else {
			Intent intent = new Intent(getBaseContext(),ContentActivity.class);
			intent.putExtra("item", item);
			startActivity(intent);
		}
		
	}
}
