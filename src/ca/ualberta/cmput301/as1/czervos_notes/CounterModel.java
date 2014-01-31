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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class acts as the model for the counters. Stores a counter name and
 * the counter's count, along with a list of CalendarObjects for each count
 * for statistical purposes. Class is serializable to allow for bundling and
 * passing through intents.
 * @author Costa Zervos
 */
public class CounterModel implements Serializable{
	
	private int count = 0;
	private String name;
	private ArrayList<Calendar> timeList = new ArrayList<Calendar>();
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor method that sets the name of the counter.
	 * @param text the String to name the counter.
	 */
	public CounterModel(String text) {
		this.name = text;
	}
	
	// Getters
	
	/**
	 * Returns the value of the count.
	 * @return Count value.
	 */
	public Integer getCount() {
		return this.count;
	}
	
	/**
	 * Returns the name of the counter.
	 * @return Counter name.
	 */
	public String getCounterName() {
		return this.name;
	}
	
	/**
	 * Returns the list of timestamps.
	 * @return Array of calendar objects.
	 */
	public ArrayList<Calendar> getTimeList() {
		return timeList;
	}
	
	// Setters
	
	/**
	 * Increments the counter and adds a timestamp to the timelist.
	 */
	public void increment() {
		this.count++;
		this.timeList.add(Calendar.getInstance());
	}
	

	/**
	 * Sets the name of the counter.
	 * @param text the String to use as a name for the counter.
	 */
	public void setCounterName(String text) {
		this.name = text;
	}
	
	/**
	 * Resets the counter value
	 */
	public void zero() {
		timeList.clear();
		this.count = 0;
	}
}
