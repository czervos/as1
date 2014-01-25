/**
* Copyright 2014 Costa Zervos
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package ca.ualberta.cmput301.as1.czervos_notes;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * The main activity of the application.
 * 
 * @author Costa Zervos
 */
public class CounterListActivity extends Activity {
	
	private CounterListModel counterList;
	private ListView counterListView;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_list);
		counterList = new CounterListModel();
		// Creates the list containing the counters
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counter_list, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		CounterModel newCounter;
		// Defines a new counter
		
		/* Updates counter list if needed */
		Intent intent = this.getIntent();
		// Gets the intent
		Bundle bundle = intent.getExtras();
		// Gets the bundle form the intent
		if (bundle != null) {
			// If bundle is not empty
			newCounter = (CounterModel) bundle.getSerializable("newCounter");
			// Gets the new counter from the bundle
			counterList.addCounter(newCounter);
			// Adds the counter to the list
		}
		
		/* Displays List of Counters */
		counterListView = (ListView) findViewById(R.id.counterList);
		// Associates variable with listview resource (view)
		ArrayList<CounterModel> tempCounterList = new ArrayList<CounterModel>();
		tempCounterList = counterList.getCounterList();
		// Retrieves list of counters from the model
		ArrayList<String> counterNameList = new ArrayList<String>();
		for (int i=0; i<tempCounterList.size(); i++ ) {
			counterNameList.add(tempCounterList.get(i).getCounterName());
		}
		// Puts names of counters in array to be retrieved by adapter
		adapter = new ArrayAdapter<String>(this,R.layout.list_item,
				counterNameList);
		counterListView.setAdapter(adapter);
		// Adapter sets up and displays listview
		
	}
	
	/**
	 * Responds to action button presses.
	 * 
	 * CODE REUSE:
	 * This code was modified from
	 * URL: http://developer.android.com/training/basics/actionbar/adding-buttons.html#Respond
	 * Date: Jan. 24, 2014
	 * License: Creative Commons 2.5 Attribution License (http://creativecommons.org/licenses/by/2.5/)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	    	// Handles pressing add counter button
	        case R.id.counter_add:
	            Intent intent = new Intent(this, AddCounterActivity.class);
	            startActivity(intent);
	            return true;
	        case R.id.action_settings:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
