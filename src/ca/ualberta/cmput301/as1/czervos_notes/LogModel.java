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
import java.util.Calendar;

/**
 * This class acts as the model for the logs that store timestamp data. 
 * @author Costa Zervos
 */
public class LogModel {
	private int count = 1;
	private String hourID;
	private String dayID;
	private String weekID;
	private String monthID;
	private SimpleDateFormat sdf;
	
	/**
	 * Constructor takes in a calendar object and parses the data into 
	 * month, week, day, and hour data.
	 * 
	 * @param calendar object to parse info from.
	 */
	public LogModel(Calendar calendar) {
		sdf = new SimpleDateFormat("MMM. dd, yyyy - hh:00 aa");
		hourID = sdf.format(calendar.getTime());
		
		sdf = new SimpleDateFormat("MMM. dd, yyyy");
		dayID = sdf.format(calendar.getTime());
		
		sdf = new SimpleDateFormat("W");
		weekID = sdf.format(calendar.getTime());
		
		sdf = new SimpleDateFormat("MMM. yyyy");
		monthID = sdf.format(calendar.getTime());
	}
	
	// Getters
	
	/**
	 * Retrieves the hour data from the calendar formatted as a string.
	 * @return Hour data.
	 */
	public String getHour() {
		return hourID;
	}
	
	/**
	 * Retrieves the day data from the calendar formatted as a string.
	 * @return Day data.
	 */
	public String getDay() {
		return dayID;
	}
	
	/**
	 * Retrieves the week data from the calendar formatted as a string.
	 * @return Week data.
	 */
	public String getWeek() {
		return weekID;
	}
	
	/**
	 * Retrieves the month data from the calendar formatted as a string.
	 * @return Hour data.
	 */
	public String getMonth() {
		return monthID;
	}
	
	/**
	 * Gets the count value for this log and returns it as a string.
	 * @return The count as a string.
	 */
	public String getCount() {
		String strValue=String.valueOf(count);
		return strValue;
	}
	
	// Setters
	
	/**
	 * Increments the count of this log.
	 */
	public void increment(){
		count++;	
	}
}
