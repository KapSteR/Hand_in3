package itsmap.f13.stud10731.hand_in3;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends FragmentActivity {
	
//	ArrayList<String> listItems = new ArrayList<String>();
//	ArrayAdapter<String> adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragTrans = fragmentManager.beginTransaction();
        fragTrans.add(R.id.fragment1, new MenuFragment());
        fragTrans.commit();
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
