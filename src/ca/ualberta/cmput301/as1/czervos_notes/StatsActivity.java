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
import java.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class StatsActivity extends Activity {
	private CounterModel counter;
	private ArrayList<Calendar> timeList = new ArrayList<Calendar>();
	private ArrayList<String> statsList = new ArrayList<String>();
	private ArrayList<String> statsCountsList = new ArrayList<String>();
	private ListView statsListView;
	private TextView statsTitle;
	private CustomStatsAdapter customStatsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats); // Show the Up button in the action bar.
		setupActionBar();
		
		/* Receives bundle */
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		counter = (CounterModel) bundle.getSerializable("CounterStats");
		timeList = counter.getTimeList(); // gets list of calendars from counter 
		
		
		/* Retrieves Counter Statistics from Calendar Data */
		setMonthStats(timeList);
		setDayStats(timeList);
		setWeekStats(timeList);
		setHourStats(timeList);
		
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
	
	public void setMonthStats(ArrayList<Calendar> timeList) {
		int len;
		int x;
		int y;
		LogModel tempLog;
		ArrayList<LogModel> monthList = new ArrayList<LogModel>();
		
		len = timeList.size();
		for (x=0; x < len; x++) {
			// for every calendar in counterlist
			tempLog = new LogModel(timeList.get(x));
			// create a temp log of that calendar
			for (y=0; y < monthList.size(); y++) {
				// for every log in the list of logs
				if (monthList.get(y).getMonth().equals(tempLog.getMonth())) {
					// if the log = the current calendar info
					monthList.get(y).increment();
					// increment the count of the log
					tempLog = null;
					// destroy temp log
					break;
				}
			}
			if (tempLog != null) {
				// no log found that matches current calendar
				monthList.add(tempLog);
				// add current calendar to the log list
			}
		}
		/* Add months to the stats list */
		statsList.add("Counts Per Month");
		for (x=0; x < monthList.size(); x++) {
			statsList.add("    " + monthList.get(x).getMonth());
		}
		
		/* Add month counts to count list */
		statsCountsList.add(" ");
		for (x=0; x < monthList.size(); x++) {
			statsCountsList.add(monthList.get(x).getCount());
		}
	}
	
	public void setDayStats(ArrayList<Calendar> timeList) {
		int len;
		int x;
		int y;
		LogModel tempLog;
		ArrayList<LogModel> dayList = new ArrayList<LogModel>();
		
		len = timeList.size();
		for (x=0; x < len; x++) {
			// for every calendar in counterlist
			tempLog = new LogModel(timeList.get(x));
			// create a temp log of that calendar
			for (y=0; y < dayList.size(); y++) {
				// for every log in the list of logs
				if (dayList.get(y).getDay().equals(tempLog.getDay())) {
					// if the log = the current calendar info
					dayList.get(y).increment();
					// increment the count of the log
					tempLog = null;
					// destroy temp log
					break;
				}
			}
			if (tempLog != null) {
				// no log found that matches current calendar
				dayList.add(tempLog);
				// add current calendar to the log list
			}
		}
		/* Add months to the stats list */
		statsList.add("Counts Per Day");
		for (x=0; x < dayList.size(); x++) {
			statsList.add("    " + dayList.get(x).getDay());
		}
		
		/* Add month counts to count list */
		statsCountsList.add(" ");
		for (x=0; x < dayList.size(); x++) {
			statsCountsList.add(dayList.get(x).getCount());
		}
	}
	
	public void setHourStats(ArrayList<Calendar> timeList) {
		int len;
		int x;
		int y;
		LogModel tempLog;
		ArrayList<LogModel> hourList = new ArrayList<LogModel>();
		
		len = timeList.size();
		for (x=0; x < len; x++) {
			// for every calendar in counterlist
			tempLog = new LogModel(timeList.get(x));
			// create a temp log of that calendar
			for (y=0; y < hourList.size(); y++) {
				// for every log in the list of logs
				if (hourList.get(y).getHour().equals(tempLog.getHour())) {
					// if the log = the current calendar info
					hourList.get(y).increment();
					// increment the count of the log
					tempLog = null;
					// destroy temp log
					break;
				}
			}
			if (tempLog != null) {
				// no log found that matches current calendar
				hourList.add(tempLog);
				// add current calendar to the log list
			}
		}
		/* Add months to the stats list */
		statsList.add("Counts Per Hour");
		for (x=0; x < hourList.size(); x++) {
			statsList.add("    " + hourList.get(x).getHour());
		}
		
		/* Add month counts to count list */
		statsCountsList.add(" ");
		for (x=0; x < hourList.size(); x++) {
			statsCountsList.add(hourList.get(x).getCount());
		}
	}
	
	public void setWeekStats(ArrayList<Calendar> timeList) {
		int len;
		int x;
		int y;
		LogModel tempLog;
		ArrayList<LogModel> weekList = new ArrayList<LogModel>();
		
		len = timeList.size();
		for (x=0; x < len; x++) {
			// for every calendar in counterlist
			tempLog = new LogModel(timeList.get(x));
			// create a temp log of that calendar
			for (y=0; y < weekList.size(); y++) {
				// for every log in the list of logs
				if (weekList.get(y).getWeek().equals(tempLog.getWeek())) {
					// if the log = the current calendar info
					weekList.get(y).increment();
					// increment the count of the log
					tempLog = null;
					// destroy temp log
					break;
				}
			}
			if (tempLog != null) {
				// no log found that matches current calendar
				weekList.add(tempLog);
				// add current calendar to the log list
			}
		}
		/* Add months to the stats list */
		statsList.add("Counts Per Week");
		for (x=0; x < weekList.size(); x++) {
			statsList.add("    Week " + weekList.get(x).getWeek() + " of " + weekList.get(x).getMonth());
		}
		
		/* Add month counts to count list */
		statsCountsList.add(" ");
		for (x=0; x < weekList.size(); x++) {
			statsCountsList.add(weekList.get(x).getCount());
		}
	}
}


















