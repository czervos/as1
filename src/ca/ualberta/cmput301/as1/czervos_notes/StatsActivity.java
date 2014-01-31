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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class StatsActivity extends Activity {
	private CounterModel counter;
	private ArrayList<Calendar> timeList = new ArrayList<Calendar>();
	private ArrayList<String> hourList = new ArrayList<String>();
	private String format;
	private Map monthHash;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> statsList = new ArrayList<String>();
	private ArrayList<String> statsCountsList = new ArrayList<String>();
	private ListView statsListView;
	private TextView statsTitle;
	private CustomStatsAdapter customStatsAdapter;
	private ArrayList<LogModel> monthList = new ArrayList<LogModel>();
	private int lengthTime;
	private int x;
	private int y;
	private LogModel tempLog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		// Show the Up button in the action bar.
		setupActionBar();
		
		/* Receives bundle */
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		counter = (CounterModel) bundle.getSerializable("CounterStats");
		timeList = counter.getTimeList(); 
		// gets list of calendars from counter 
		
		/* Month Counters */
		lengthTime = timeList.size();
		for (x=0; x < lengthTime; x++) {
			tempLog = new LogModel(timeList.get(x));
			for (y=0; y < monthList.size(); y++) {
				if (monthList.get(y).getMonth().equals(tempLog.getMonth())) {
					monthList.get(y).increment();
					tempLog = null;
					break;
				}
			}
			if (tempLog != null) {
				monthList.add(tempLog);
			}
		}
		
		/* Add months to the stats list */
		statsList.add("Counts Per Month");
		for (x=0; x < monthList.size(); x++) {
			statsList.add(monthList.get(x).getMonth());
		}
		
		/* Add month counts to count list */
		statsCountsList.add(" ");
		for (x=0; x < monthList.size(); x++) {
			statsCountsList.add(monthList.get(x).getCount());
		}
		
		/* Draws ListView of counts */
		statsListView = (ListView) findViewById(R.id.listview_stats_list);
		customStatsAdapter = new CustomStatsAdapter(this, statsList, statsCountsList);
		statsListView.setAdapter(customStatsAdapter);
		
		/* Sets Statistics Screen Title */
		statsTitle = (TextView) findViewById(R.id.textview_stats_title);
		statsTitle.setText(counter.getCounterName() + " Counter Statistics");
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}


















