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
import android.widget.TextView;

/**
 * Creates a custom adapter for displaying two textviews for each item in 
 * a listview.
 * 
 * CODE REUSE:
 * This code was modified from
 * Author: FabiF
 * URL: http://stackoverflow.com/questions/11106418/how-to-set-adapter-in-case-of-multiple-textviews-per-listview
 * Date: Jan. 25, 2014
 * License: Creative Commons 3.0 Attribution-ShareAlike (http://creativecommons.org/licenses/by-sa/3.0/)
 */

public class CustomAdapter {
	
	private LayoutInflater inflater;
	private CounterListModel counterListModel;
	private ArrayList<CounterModel> counters;
	
	private class ViewHolder {
		TextView textView1;
		TextView textView2;
	}
	
	public CustomAdapter(Context context, CounterListModel counterListModel) {
		inflater = LayoutInflater.from(context);
		this.counterListModel = counterListModel;
	    this.counters = counterListModel.getCounterList();
	}
	
	public int getCount() {
		return counters.size();
	}

	public CounterModel getItem(int position) {
		return counters.get(position);
		}

	public long getItemId(int position) {
		return position;
		}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item, null);
			holder.textView1 = (TextView) convertView.
					findViewById(R.id.list_counter_name);
			holder.textView2 = (TextView) convertView.
					findViewById(R.id.list_counter_count);
			convertView.setTag(holder);
		} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.textView1.setText(counters.get(position).getCounterName());
		holder.textView2.setText(counters.get(position).getCount());
		return convertView;
   }
}
