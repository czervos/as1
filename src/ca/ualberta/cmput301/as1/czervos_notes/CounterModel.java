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
 * This class acts as the model for the counters.
 * 
 * @author Costa Zervos
 */
public class CounterModel implements Serializable{
	
	private int count = 0;
	private String name;
	private ArrayList<Calendar> timeList = new ArrayList<Calendar>();
	private static final long serialVersionUID = 1L;
	
	// Constructor
	
	public CounterModel(String text) {
		this.name = text;
	}
	
	// Getters
	
	/**
	 * Returns the value of the count.
	 * 
	 * @return count
	 */
	public Integer getCount() {
		return this.count;
	}
	
	/**
	 * Returns the name of the counter.
	 * 
	 * @return
	 */
	public String getCounterName() {
		return this.name;
	}
	
	/**
	 * Returns the list of timestamps.
	 * 
	 * @return
	 */
	public ArrayList<Calendar> getTimeList() {
		return timeList;
	}
	
	// Setters
	
	/**
	 * This method increments the counter and adds a timestamp to the timelist.
	 * 
	 * @param count
	 */
	public void increment() {
		this.count++;
		this.timeList.add(Calendar.getInstance());
	}
	
	/**
	 * This method sets the name of the counter.
	 * 
	 * @param text
	 */
	public void setCounterName(String text) {
		this.name = text;
	}
	
	/**
	 * This method zeros the counter.
	 */
	public void zero() {
		timeList.clear();
		this.count = 0;
	}
}
