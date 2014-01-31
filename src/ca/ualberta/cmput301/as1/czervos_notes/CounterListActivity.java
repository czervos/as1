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
 * The main activity of the application. Acts as a hub to all other activities
 * and maintains the counter list.
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
	 * Creates a context menu when the user long clicks an item in the list
	 * view. Note: for this to work the listview must be enabled to register
	 * long clicks - this is found in onResume under the "Display List of
	 * Counters" section.
	 * 
	 * CODE REUSE:
	 * This code was modified from
	 * @author Mike Plate
	 * @URL http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
	 * @date Jan. 26, 2014
	 * @license No license
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, 
			ContextMenuInfo menuInfo) {
		// If the view that trigger the longclick is the counter listview
		if (view.getId() == R.id.counterList) {
			// Casts listview item selected info into format readable by adapter
			AdapterView.AdapterContextMenuInfo info = (AdapterView
					.AdapterContextMenuInfo) menuInfo;
			// Gets counter list from counter list model
			ArrayList<CounterModel> tempList = counterList.getCounterList();
			// Grabs name of counter selected using the info
			menu.setHeaderTitle(tempList.get(info.position).getCounterName());
			// displays the context menu items
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}
	
	/**
	 * Retrieves and processes intents and bundles, loads and saves data,
	 * displays the list view, and listens for button presses.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		CounterModel newCounter;
		String newName;
		
		
		/* Sets Up Counter List */
		counterList = loadCounterList();
		if (counterList == null){
			counterList = new CounterListModel();
		}
		
		
		/* Retrieves Intents and Bundles */
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		// If bundle wasn't empty, need to figure out what type of bundle it is
		if (bundle != null) {
			// Attempts to get a new counter bundle
			newCounter = (CounterModel) bundle.getSerializable("newCounter");
			// If there is no new counter bundle, bundle must be a rename bundle
			if (newCounter == null) {
				// Gets the new name
				newName = (String) bundle.getSerializable("renameCounter");
				// Copies counter list
				ArrayList<CounterModel> tempList = counterList.getCounterList();
				// Changes the name of the counter in the copied list
				tempList.get(renamePosition).setCounterName(newName);
				// Sets list to copied list containing renamed counter
				counterList.setCounterList(tempList);
				// Removes the bundle when done with it
				intent.removeExtra("renameCounter");
			}
			// Definitely a new counter bundle
			else {
				counterList.addCounter(newCounter);
				intent.removeExtra("newCounter");
			}
			saveCounterList(counterList);
		}
		
		
		/* Displays List of Counters */
		counterListView = (ListView) findViewById(R.id.counterList);
		customAdapter = new CustomAdapter(this,counterList);
		// Draws listview with custom adapter
		counterListView.setAdapter(customAdapter);
		// Enables listview to register longclicks for context menu
		registerForContextMenu(counterListView);

		
		/* Listens and Processes listview clicks */
		counterListView.setOnItemClickListener(new AdapterView.
				OnItemClickListener() {
			/**
			 * Acts upon a clicked item in the list view.
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id) {
				// Grabs a copy of the list of counters
				ArrayList<CounterModel> tempList = counterList.getCounterList();
				// Increments count value of clicked counter in copied list
				tempList.get(position).increment();
				// Sets the copied list w/ updated count as the actual list
				counterList.setCounterList(tempList);
				saveCounterList(counterList);
				// Redraws the list with updated values
				customAdapter.notifyDataSetChanged();
			}
		});
	}
	
	/**
	 * Responds to action button presses.
	 * 
	 * CODE REUSE:
	 * This code was modified from
	 * @author Android Developer Training
	 * @URL http://developer.android.com/training/basics/actionbar/adding-buttons.html#Respond
	 * @date Jan. 24, 2014
	 * @license Creative Commons 2.5 Attribution License (http://creativecommons.org/licenses/by/2.5/)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    	// Add counter
	        case R.id.counter_add:
	            Intent intent = new Intent(this, AddCounterActivity.class);
	            startActivity(intent);
	            return true;
	        // Sort counters
	        case R.id.counter_sort:
	        	ArrayList<CounterModel> tempList = counterList.getCounterList();
	        	// Sorts the list of counters
	        	tempList = sort(tempList);
	        	counterList.setCounterList(tempList);
	    		saveCounterList(counterList);
	    		// Updates adapter after sorting
	    		customAdapter.notifyDataSetChanged();
	        	return true;
	        // Settings (found in overflow - unimplemented)
	        case R.id.action_settings:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/**
	 * Saves the list of counters from the counter list model.
	 * @param counterListModel the list of counter models
	 */
	public void saveCounterList(CounterListModel counterListModel) {
		ArrayList<CounterModel> counterList = new ArrayList<CounterModel>();
		counterList = counterListModel.getCounterList();
		// Initialize GSON
		Gson gson = new Gson();
		// Converts list of counters to a JSON string object
		String json = gson.toJson(counterList);
		
		// Initializes shared preferences
		SharedPreferences savedList = getPreferences(MODE_PRIVATE);
		// Initializes shared preferences editor
		SharedPreferences.Editor editor = savedList.edit();
		// Saves the JSON string object as "savedList"
		editor.putString("savedList",json);
		// Commits the save
		editor.commit();
	}
	
	/**
	 * Loads the stored array list and returns the counterListModel
	 * with the loaded counters. If there is nothing saved, returns null.
	 * @return counterListModel containing counters OR null
	 */
	public CounterListModel loadCounterList() {
		Gson gson = new Gson();
		CounterListModel counterListModel = new CounterListModel();
		SharedPreferences savedList = getPreferences(MODE_PRIVATE);
		// Loads the JSON string object saved as "savedList" and returns
		// "noValue" if no JSON string object located 
		String json = savedList.getString("savedList","noValue");
		if (json == "noValue") {
			return null;
		}
		
		// Sets collection type to ArrayList<CounterModel>
		java.lang.reflect.Type collectionType = new TypeToken<ArrayList<CounterModel>>(){}.getType();
		// Converts JSON string object to ArrayList<CounterModel>
		ArrayList<CounterModel> loadedList = gson.fromJson(json, collectionType);
		// Puts loaded list into the counter list model
		counterListModel.setCounterList(loadedList);
		return counterListModel;		
	}
	
	/**
	 * Responds to context menu longclicks.
	 * 
	 * CODE REUSE:
	 * This code was modified from
	 * @author Mike Plate
	 * @URL http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
	 * @date Jan. 26, 2014
	 * @license No license
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Casts contextmenu item selected info into format readable by adapter
		AdapterView.AdapterContextMenuInfo info = (AdapterView
				.AdapterContextMenuInfo)item.getMenuInfo();
		// Gets id of context menu item selected
		int menuItemIndex = item.getItemId();
		ArrayList<CounterModel> tempList = counterList.getCounterList();
		
		switch(menuItemIndex) {
		case 0:
			// Stats
			Intent intent1 = new Intent(this, StatsActivity.class);
			Bundle bundle = new Bundle();
			// Bundles counter selected for statistics display
			bundle.putSerializable("CounterStats", tempList.get(info.position));
			intent1.putExtras(bundle);
			startActivity(intent1);
			break;
		case 1:
			// Rename
			// Remembers position of counter being renamed
			renamePosition = info.position;
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
		// Sets the list in CounterListModel to the updated list
		counterList.setCounterList(tempList);
		saveCounterList(counterList);
		// Updates adapter
		customAdapter.notifyDataSetChanged();
		return true;
	}
	
	/**
	 * Selection sort algorithm to sort an array of counters.
	 * @param array the list of counter models.
	 * @return the array of counters.
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
