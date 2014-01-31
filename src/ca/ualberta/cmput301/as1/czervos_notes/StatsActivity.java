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

/**
 * Displays the counter statistics. Retrieves a counter's data and parses that
 * data into useful information to be displayed to the user.
 * @author Costa Zervos
 */
public class StatsActivity extends Activity {
	private CounterModel counter;
	private ArrayList<Calendar> timeList = new ArrayList<Calendar>();
	private ArrayList<String> statsList = new ArrayList<String>();
	private ArrayList<String> statsCountsList = new ArrayList<String>();
	private ListView statsListView;
	private TextView statsTitle;
	private CustomStatsAdapter customStatsAdapter;

	/**
	 * Processes data in received bundle then displays it.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats); // Show the Up button in the action bar.
		setupActionBar();
		
		/* Receives bundle */
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		counter = (CounterModel) bundle.getSerializable("CounterStats");
		// Gets list of calendars from counter 
		timeList = counter.getTimeList();
		
		
		/* Retrieves Counter Statistics from Calendar Data */
		setMonthStats(timeList);
		setWeekStats(timeList);
		setDayStats(timeList);
		setHourStats(timeList);
		
		/* Draws ListView of counts */
		statsListView = (ListView) findViewById(R.id.listview_stats_list);
		customStatsAdapter = new CustomStatsAdapter(this, statsList, 
				statsCountsList);
		// Draws ListView using custom adapter
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
	
	/**
	 * Takes in a list of calendar objects and iterates through the list,
	 * converting each into a LogModel object for easier sorting. It then sorts 
	 * them into groups by months (taking into account the year as well). Then
	 * adds the date and count info to the date string and count string that
	 * the adapter uses to put into the two textviews that are displayed in
	 * each ListView item. 
	 * @param timeList the list of calendars for the current counter.
	 */
	public void setMonthStats(ArrayList<Calendar> timeList) {
		int len;
		int x;
		int y;
		LogModel tempLog;
		ArrayList<LogModel> monthList = new ArrayList<LogModel>();
		
		len = timeList.size();
		for (x=0; x < len; x++) {
			// For every calendar in counterlist, convert to a LogModel object
			tempLog = new LogModel(timeList.get(x));
			// For every LogModel in the list of logs
			for (y=0; y < monthList.size(); y++) {
				// If log in the list == current log's info, increment log count
				if (monthList.get(y).getMonth().equals(tempLog.getMonth())) {
					monthList.get(y).increment();
					// Get rid of current log since its already in the list
					tempLog = null;
					// Stop iterating through the list of logs
					break;
				}
			}
			// If current log did not match with a log in the list
			if (tempLog != null) {
				// Add log to the log list
				monthList.add(tempLog);
			}
		}
		/* Add Month Data to the Stats List */
		// Create month list header
		statsList.add("Counts Per Month");
		for (x=0; x < monthList.size(); x++) {
			// Pulls month data from every log in log list
			statsList.add("    " + monthList.get(x).getMonth());
		}
		
		/* Add Month Counts to Count List */
		// First entry corresponds to month title thus leave empty
		statsCountsList.add(" ");
		for (x=0; x < monthList.size(); x++) {
			// Pulls month data from every log in log list
			statsCountsList.add(monthList.get(x).getCount());
		}
	}
	
	/**
	 * Takes in a list of calendar objects and iterates through the list,
	 * converting each into a LogModel object for easier sorting. It then sorts 
	 * them into groups by days (taking into account the year & month as well). 
	 * Then adds the date and count info to the date string and count string 
	 * that the adapter uses to put into the two textviews that are displayed in
	 * each ListView item. 
	 * @param timeList the list of calendars for the current counter.
	 */
	public void setDayStats(ArrayList<Calendar> timeList) {
		int len;
		int x;
		int y;
		LogModel tempLog;
		ArrayList<LogModel> dayList = new ArrayList<LogModel>();
		
		len = timeList.size();
		for (x=0; x < len; x++) {
			tempLog = new LogModel(timeList.get(x));
			for (y=0; y < dayList.size(); y++) {
				if (dayList.get(y).getDay().equals(tempLog.getDay())) {
					dayList.get(y).increment();
					tempLog = null;
					break;
				}
			}
			if (tempLog != null) {
				dayList.add(tempLog);
			}
		}

		statsList.add("Counts Per Day");
		for (x=0; x < dayList.size(); x++) {
			statsList.add("    " + dayList.get(x).getDay());
		}
		
		statsCountsList.add(" ");
		for (x=0; x < dayList.size(); x++) {
			statsCountsList.add(dayList.get(x).getCount());
		}
	}
	
	/**
	 * Takes in a list of calendar objects and iterates through the list,
	 * converting each into a LogModel object for easier sorting. It then sorts 
	 * them into groups by hours (taking into account the year, month, & day as 
	 * well). Then adds the date and count info to the date string and count 
	 * string that the adapter uses to put into the two textviews that are 
	 * displayed in each ListView item. 
	 * @param timeList the list of calendars for the current counter.
	 */
	public void setHourStats(ArrayList<Calendar> timeList) {
		int len;
		int x;
		int y;
		LogModel tempLog;
		ArrayList<LogModel> hourList = new ArrayList<LogModel>();
		
		len = timeList.size();
		for (x=0; x < len; x++) {
			tempLog = new LogModel(timeList.get(x));
			for (y=0; y < hourList.size(); y++) {
				if (hourList.get(y).getHour().equals(tempLog.getHour())) {
					hourList.get(y).increment();
					tempLog = null;
					break;
				}
			}
			if (tempLog != null) {
				hourList.add(tempLog);
			}
		}

		statsList.add("Counts Per Hour");
		for (x=0; x < hourList.size(); x++) {
			statsList.add("    " + hourList.get(x).getHour());
		}
		
		statsCountsList.add(" ");
		for (x=0; x < hourList.size(); x++) {
			statsCountsList.add(hourList.get(x).getCount());
		}
	}
	
	/**
	 * Takes in a list of calendar objects and iterates through the list,
	 * converting each into a LogModel object for easier sorting. It then sorts 
	 * them into groups by weeks (taking into account the year & month as well). 
	 * Then adds the date and count info to the date string and count string 
	 * that the adapter uses to put into the two textviews that are displayed in
	 * each ListView item. 
	 * @param timeList the list of calendars for the current counter.
	 */
	public void setWeekStats(ArrayList<Calendar> timeList) {
		int len;
		int x;
		int y;
		LogModel tempLog;
		ArrayList<LogModel> weekList = new ArrayList<LogModel>();
		
		len = timeList.size();
		for (x=0; x < len; x++) {
			tempLog = new LogModel(timeList.get(x));
			for (y=0; y < weekList.size(); y++) {
				if (weekList.get(y).getWeek().equals(tempLog.getWeek())) {
					weekList.get(y).increment();
					tempLog = null;
					break;
				}
			}
			if (tempLog != null) {
				weekList.add(tempLog);
			}
		}

		statsList.add("Counts Per Week");
		for (x=0; x < weekList.size(); x++) {
			statsList.add("    Week " + weekList.get(x).getWeek() + " of " + weekList.get(x).getMonth());
		}
		
		statsCountsList.add(" ");
		for (x=0; x < weekList.size(); x++) {
			statsCountsList.add(weekList.get(x).getCount());
		}
	}
}