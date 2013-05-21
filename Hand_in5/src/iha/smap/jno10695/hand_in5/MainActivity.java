package iha.smap.jno10695.hand_in5;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DatabaseHandler db = new DatabaseHandler(this);

		// Inserting Costumers
		Log.d("Insert: ", "Inserting ..");
		db.addCostumer(new Costumer(1, "Ravi", "9100000000"));
		db.addCostumer(new Costumer(2, "Srinivas", "9199999999"));
		db.addCostumer(new Costumer(3, "Tommy", "9522222222"));
		db.addCostumer(new Costumer(4, "Karthik", "9533333333"));
		/*
		 * // Reading all costumers Log.d("Reading: ",
		 * "Reading all costumers.."); List<Costumer> costumers =
		 * db.getAllCostumers();
		 * 
		 * for (Costumer cn : costumers) { String log = "Id: " + cn.getID() +
		 * " ,Name: " + cn.getName() + " ,Phone: " + cn.getAddress(); // Writing
		 * costumers to log Log.d("Name: ", log);
		 * 
		 * 
		 * }
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
