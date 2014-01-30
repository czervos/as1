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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
	private String[] menuItems = {"Stats", "Rename", "Reset", "Delete"};
	private CustomAdapter customAdapter;
	private static int renamePosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counter_list, menu);
		return true;
	}
	
	/**
	 * Creates context menu for longclicks.
	 * 
	 * CODE REUSE:
	 * This code was modified from
	 * Author: Mike Plate
	 * URL: http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
	 * Date: Jan. 26, 2014
	 * License: No license
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, 
			ContextMenuInfo menuInfo) {
		// NOTE: needed to enable listview to register longclicks - code in
		// onResume()
		if (view.getId() == R.id.counterList) {
			// If the view that trigger the longclick is the counter listview
			AdapterView.AdapterContextMenuInfo info = (AdapterView
					.AdapterContextMenuInfo) menuInfo;
			// Casts listview item selected info into format readable by adapter
			ArrayList<CounterModel> tempList = counterList.getCounterList();
			// Gets counter list from counter list model
			menu.setHeaderTitle(tempList.get(info.position).getCounterName());
			// Grabs name of counter selected using the info
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
				// displays the context menu items
				}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		CounterModel newCounter;
		String newName;
		// Defines a new counter
		
		counterList = loadCounterList();
		
		if (counterList == null){
			counterList = new CounterListModel();
		}
		
		/* Retrieves Intents and Bundles */
		Intent intent = this.getIntent();
		// Gets the intent
		Bundle bundle = intent.getExtras();
		// Gets the bundle form the intent
		if (bundle != null) {
			// If bundle is not empty
			newCounter = (CounterModel) bundle.getSerializable("newCounter");
			// Gets the new counter from the bundle
			if (newCounter == null) {
				// If new counter is null, bundle is a rename string
				newName = (String) bundle.getSerializable("renameCounter");
				// Retrieves new name
				ArrayList<CounterModel> tempList = counterList.getCounterList();
				// Gets list of counters
				tempList.get(renamePosition).setCounterName(newName);
				// Changes the name of the counter
				counterList.setCounterList(tempList);
				// Sets counter list model to the updated set of counters
				intent.removeExtra("renameCounter");
				// Removes the bundle when done with it
			}
			else {
				counterList.addCounter(newCounter);
				// Adds the counter to the list
				intent.removeExtra("newCounter");
				// Removes the bundle when done with it
			}
			saveCounterList(counterList);
			// Saves the list

		}
		
		/* Displays List of Counters */
		counterListView = (ListView) findViewById(R.id.counterList);
		// Associates variable with listview resource (view)
		customAdapter = new CustomAdapter(this,counterList);
		// Initializes custom adapter (utilizing two textviews)
		counterListView.setAdapter(customAdapter);
		// Draws listview
		registerForContextMenu(counterListView);
		// Enables listview to register longclicks for context menu
		
		/**
		 * Listens for listview clicks.
		 */
		counterListView.setOnItemClickListener(new AdapterView.
				OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id) {
				
				ArrayList<CounterModel> tempList = counterList.getCounterList();
				// Gets list of counters
				tempList.get(position).increment();
				// Increments clicked counter by using position of list item
				// clicked which = array position
				counterList.setCounterList(tempList);
				// Sets the CounterListModel to the updated list
				saveCounterList(counterList);
				// Saves the list
				customAdapter.notifyDataSetChanged();
				// Re-draws the list
			}
		});
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
	        case R.id.counter_add:
	        	// Handles pressing add counter button
	            Intent intent = new Intent(this, AddCounterActivity.class);
	            startActivity(intent);
	            return true;
	        case R.id.counter_sort:
	        	// Handles counter sort button press
	        	ArrayList<CounterModel> tempList = counterList.getCounterList();
	        	tempList = sort(tempList);
	        	counterList.setCounterList(tempList);
	    		saveCounterList(counterList);
	    		// Saves list
	    		customAdapter.notifyDataSetChanged();
	    		// Updates adapter
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
	
	/**
	 * Responds to context menu longclicks.
	 * 
	 * CODE REUSE:
	 * This code was modified from
	 * Author: Mike Plate
	 * URL: http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
	 * Date: Jan. 26, 2014
	 * License: No license
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView
				.AdapterContextMenuInfo)item.getMenuInfo();
		// Casts contextmenu item selected info into format readable by adapter
		int menuItemIndex = item.getItemId();
		// Gets id of context menu item selected
		ArrayList<CounterModel> tempList = counterList.getCounterList();
		// Gets list of counters
		
		switch(menuItemIndex) {
		case 0:
			// Stats
			Intent intent1 = new Intent(this, StatsActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("CounterStats", tempList.get(info.position));
			intent1.putExtras(bundle);
			startActivity(intent1);
			break;
		case 1:
			// Rename
			renamePosition = info.position;
			// Remembers position of counter being renamed
			Intent intent2 = new Intent(this, RenameCounterActivity.class);
			startActivity(intent2);
			break;
		case 2:
			// Reset
			tempList.get(info.position).zero();
			break;
		case 3:
			// Remove
			tempList.remove(info.position);
			break;
		}
		counterList.setCounterList(tempList);
		// Sets the CounterListModel to the updated list 
		saveCounterList(counterList);
		// Saves list
		customAdapter.notifyDataSetChanged();
		// Updates adapter
		return true;
	}
	
	/**
	 * Selection sort algorithm.
	 * 
	 * @param array
	 * @return
	 */
	public ArrayList<CounterModel> sort(ArrayList<CounterModel> array) {
		
		for (int i=0; i < array.size()-1; i++) {
			CounterModel maxCount = array.get(i);
			int maxIndex = i;
			for (int j=i+1; j < array.size(); j++) {
				if (array.get(j).getCount() > maxCount.getCount()) {
					maxCount = array.get(j);
					maxIndex = j;
				}			
			}
			CounterModel tempCount;
			tempCount = array.get(i);
			array.set(i,maxCount);
			array.set(maxIndex,tempCount);	
		}
		return array;
	}
}
