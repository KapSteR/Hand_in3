package itsmap.f13.stud10731.hand_in3;

import itsmap.f13.stud10731.hand_in3.MenuFragment.OnMenuFragmentClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnMenuFragmentClickListener {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (findViewById(R.id.fragment_container) != null) {
        
        	if (savedInstanceState != null) {
                return;
            }
        	
	        FragmentManager fragmentManager = getSupportFragmentManager();
	
	        FragmentTransaction fragTrans = fragmentManager.beginTransaction();
	        fragTrans.add(R.id.fragment_container, new MenuFragment());
	        fragTrans.commit();
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
	}


    
}
