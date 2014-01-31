package ca.ualberta.cmput301.as1.czervos_notes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogModel {
	private int count = 1;
	private int hour;
	private int day;
	private int week;
	private int month;
	private int year;
	private String hourID;
	private String dayID;
	private String weekID;
	private String monthID;
	private SimpleDateFormat sdf;
	
	public LogModel(Calendar calendar) {
		hour = calendar.get(Calendar.HOUR_OF_DAY); // 12 pm = 24
		day = calendar.get(Calendar.DATE); // first day = 1
		week = calendar.get(Calendar.WEEK_OF_YEAR); // first week = 1
		month = calendar.get(Calendar.MONTH); // Jan = 0
		year = calendar.get(Calendar.YEAR);
		
		sdf = new SimpleDateFormat("yyyy-MM-dd-hh:00");
		hourID = sdf.format(calendar.getTime());
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		dayID = sdf.format(calendar.getTime());
		
		sdf = new SimpleDateFormat("yyyy-MM-W");
		weekID = sdf.format(calendar.getTime());
		
		sdf = new SimpleDateFormat("yyyy-MM");
		monthID = sdf.format(calendar.getTime());
	}
	public String getHour() {
		return hourID;
	}
	
	public String getDay() {
		return dayID;
	}
	
	public String getWeek() {
		return weekID;
	}
	
	public String getMonth() {
		return monthID;
	}
	
	public String getCount() {
		String strValue=String.valueOf(count);
		return strValue;
	}
	
	public void increment(){
		count++;	
	}
}
