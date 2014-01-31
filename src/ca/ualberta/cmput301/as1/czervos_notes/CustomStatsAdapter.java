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
public class CustomStatsAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private ArrayList<String> statsList;
	private ArrayList<String> statsCountsList;
	
	private class ViewHolder {
		TextView textView1;
		TextView textView2;
	}
	
	public CustomStatsAdapter(Context context, ArrayList<String> sList, ArrayList<String> scList) {
		inflater = LayoutInflater.from(context);
		this.statsList = sList;
	    this.statsCountsList = scList;
	}
	
	public int getCount() {
		return statsList.size();
	}

	public String getStatDate(int position) {
		return statsList.get(position);
		}
	
	public String getStatCount(int position) {
		return statsCountsList.get(position);
		}
	
	public String getItem(int position) {
		return statsList.get(position);
	}

	public long getItemId(int position) {
		return position;
		}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.stats_list_item, null);
			holder.textView1 = (TextView) convertView.
					findViewById(R.id.stats_list_counter_date);
			holder.textView2 = (TextView) convertView.
					findViewById(R.id.stats_list_count);
			convertView.setTag(holder);
		} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.textView1.setText(statsList.get(position));
		holder.textView2.setText(statsCountsList.get(position));
		return convertView;
   }

}
