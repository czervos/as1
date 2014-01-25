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

/**
 * This class acts as the model for the counters.
 * 
 * @author Costa Zervos
 */
public class CounterModel {
	
	private int count = 0;
	private String name;
	
	// Constructor
	
	public CounterModel() {}
	
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
	
	// Setters
	
	/**
	 * This method increments the counter.
	 * 
	 * @param count
	 */
	public void increment() {
		this.count++;
	}
	
	/**
	 * This method sets the name of the counter.
	 * 
	 * @param text
	 */
	public void setCounterName(String text) {
		this.name = text;
	}
}