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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Creates a custom adapter for displaying two textviews for each item in a
 * listview.
 * 
 * CODE REUSE:
 * This code was modified from
 * @author FabiF
 * @URL http://stackoverflow.com/questions/11106418/how-to-set-adapter-in-case-of-multiple-textviews-per-listview
 * @date Jan. 25, 2014
 * @license Creative Commons 3.0 Attribution-ShareAlike (http://creativecommons.org/licenses/by-sa/3.0/)
 */
public class CustomStatsAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private ArrayList<String> statsList;
	private ArrayList<String> statsCountsList;
	
	/**
	 * Initializes textview objects to be added to the ListView.
	 */
	private class ViewHolder {
		TextView textView1;
		TextView textView2;
	}
	
	/**
	 * Constructor that sets the layout inflater, retrieves the list of date
	 * strings and the list of counts associated with those dates
	 * and gets the counters from the counter list.
	 * @param context the layout of the activity.
	 * @param sList the list of date strings.
	 * @param scList the list of counter number strings.
	 */	
	public CustomStatsAdapter(Context context, ArrayList<String> sList, 
			ArrayList<String> scList) {
		inflater = LayoutInflater.from(context);
		this.statsList = sList;
	    this.statsCountsList = scList;
	}
	
	// Getters
	
	/**
	 * Gets the number of dates to be displayed in the listview.
	 * @return the number of dates to be displayed.
	 */
	public int getCount() {
		return statsList.size();
	}

	/**
	 * Gets the date at a specific position.
	 * @param position in the array.
	 * @return date at that position.
	 */
	public String getItem(int position) {
		return statsList.get(position);
	}

	/**
	 * Gets the position (method needed by class type).
	 * @return position as a long.
	 */
	public long getItemId(int position) {
		return position;
		}

	/**
	 * Gets the view to be displayed by the listView.
	 * @return view to be displayed. 
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null) {
			// If view is empty, create a new viewholder
			holder = new ViewHolder();
			// Add list_item data to the view
			convertView = inflater.inflate(R.layout.stats_list_item, null);
			// Add the two textviews used in each list entry to the holder
			holder.textView1 = (TextView) convertView.
					findViewById(R.id.stats_list_counter_date);
			holder.textView2 = (TextView) convertView.
					findViewById(R.id.stats_list_count);
			// Add the holder data to the view
			convertView.setTag(holder);
		} 
		else {
			// If View is not empty, set viewholder to this view via tag
			holder = (ViewHolder) convertView.getTag();
		}
		// Grabs the strings to put in each textview
		holder.textView1.setText(statsList.get(position));
		holder.textView2.setText(statsCountsList.get(position));
		
		return convertView;
   }

}
