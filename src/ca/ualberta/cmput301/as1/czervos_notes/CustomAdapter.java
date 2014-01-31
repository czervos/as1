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
 *
 */
public class CustomAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private CounterListModel counterListModel;
	private ArrayList<CounterModel> counters;
	
	/**
	 * Initializes textview objects.
	 */
	private class ViewHolder {
		TextView textView1;
		TextView textView2;
	}
	
	/**
	 * Constructor that sets the layout inflater, retrieves the counter list,
	 * and gets the counters from the counter list.
	 * @param context the context of the activity.
	 * @param clModel the CounterListModel object containing counters.
	 */
	public CustomAdapter(Context context, CounterListModel clModel) {
		inflater = LayoutInflater.from(context);
		this.counterListModel = clModel;
	    this.counters = counterListModel.getCounterList();
	}
	
	// Getters
	
	/**
	 * Gets the number of counters in the array of counter models.
	 * @return the number of counters in the list as an int.
	 */
	public int getCount() {
		return counters.size();
	}
	
	/**
	 * Gets the counter at a specific position.
	 * @return the counter as a CounterModel.
	 */
	public CounterModel getItem(int position) {
		return counters.get(position);
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
			convertView = inflater.inflate(R.layout.list_item, null);
			// Add the two textviews used in each list entry to the holder
			holder.textView1 = (TextView) convertView.
					findViewById(R.id.list_counter_name);
			holder.textView2 = (TextView) convertView.
					findViewById(R.id.list_counter_count);
			// Add the holder data to the view
			convertView.setTag(holder);
		} 
		else {
			// If View is not empty, set viewholder to this view via tag
			holder = (ViewHolder) convertView.getTag();
		}
		// Grabs the strings to put in each textview
		holder.textView1.setText(counters.get(position).getCounterName());
		holder.textView2.setText(Integer.toString(counters.get(position).
				getCount()));
		
		return convertView;
   }
}
