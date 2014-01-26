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
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * The main activity of the application.
 * 
 * @author Costa Zervos
 */
public class CounterListActivity extends Activity {
	
	private CounterListModel counterList;
	private ListView counterListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_list);
		//counterList = new CounterListModel();
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
		
		counterList = loadCounterList();
		
		if (counterList == null){
			counterList = new CounterListModel();
		}
		
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
			
			saveCounterList(counterList);
			// Saves the list
			intent.removeExtra("newCounter");
			// Removes the bundle when done with it
		}
		
		/* Displays List of Counters */
		counterListView = (ListView) findViewById(R.id.counterList);
		// Associates variable with listview resource (view)
		CustomAdapter customAdapter = new CustomAdapter(this,counterList);
		// Initializes custom adapter (utilizing two textviews)
		counterListView.setAdapter(customAdapter);
		// Draws listview
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
	
	/**
	 * Method takes in the counter list model, and saves the counters in the 
	 * counter list.
	 * 
	 * @param counterListModel
	 */
	public void saveCounterList(CounterListModel counterListModel) {
		ArrayList<CounterModel> counterList = new ArrayList<CounterModel>();
		// initializes an array to store counters
		counterList = counterListModel.getCounterList();
		// gets the list of counters from the counterListModel
		Gson gson = new Gson();
		// initializes gson
		String json = gson.toJson(counterList);
		// converts list of counters to a JSON string object
		
		
		SharedPreferences savedList = getPreferences(MODE_PRIVATE);
		// initializes shared preferences
		SharedPreferences.Editor editor = savedList.edit();
		// initializes shared preferences editor
		editor.putString("savedList",json);
		// saves the JSON string object as "savedList"
		editor.commit();
		// commits the save to shared preferences
	}
	
	/**
	 * Method loads the stored array list and returns the counterListModel
	 * with the loaded counters. If there is nothing saved, returns null.
	 * 
	 * @return
	 */
	public CounterListModel loadCounterList() {
		Gson gson = new Gson();
		// initializes gson
		CounterListModel counterListModel = new CounterListModel();
		// initializes counter list model
		SharedPreferences savedList = getPreferences(MODE_PRIVATE);
		// initializes shared preferences
		String json = savedList.getString("savedList","noValue");
		// loads the JSON string object saved as "savedList"
		// returns "noValue" if no JSON string object located 
		if (json == "noValue") {
			return null;
			// method returns null if no JSON string object was located
		}
		
		java.lang.reflect.Type collectionType = new TypeToken<ArrayList<CounterModel>>(){}.getType();
		// Sets collection type to ArrayList<CounterModel>
		ArrayList<CounterModel> loadedList = gson.fromJson(json, collectionType);
		// converts JSON string object to ArrayList<CounterModel>
		counterListModel.setCounterList(loadedList);
		// sets counter list in model to the loaded list
		return counterListModel;		
	}

}
