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

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * The main activity of the application.
 * 
 * @author Costa Zervos
 *
 */
public class CounterListActivity extends Activity {

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
	 * Responds to action button presses
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
	            // TODO add counter functionality
	            return true;
	        case R.id.action_settings:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
