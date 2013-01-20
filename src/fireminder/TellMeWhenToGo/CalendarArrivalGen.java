package fireminder.TellMeWhenToGo;

import java.util.Calendar;

import android.util.Log;

public class CalendarArrivalGen{

	private int hour;
	private int minute;
	private int day;
	private int month;
	private int year;
	
	public CalendarArrivalGen(){
		
	}
	public CalendarArrivalGen(int minute, int hour, int day, int month, int year){
		this.setHour(hour);
		this.setMinute(minute);
		this.setDay(day);
		this.setMonth(month);
		this.setYear(year);
		
	}
	
	

	public long getTimeInMillis(){
		Calendar arrivalCalendar = Calendar.getInstance();
		
		arrivalCalendar.set(Calendar.DAY_OF_MONTH, this.day);
		arrivalCalendar.set(Calendar.MONTH, this.month);
		arrivalCalendar.set(Calendar.YEAR, this.year);
		arrivalCalendar.set(Calendar.HOUR_OF_DAY, this.hour);
		arrivalCalendar.set(Calendar.MINUTE, this.minute);
		
		Log.d("timeinmillis", ""+arrivalCalendar.getTimeInMillis());
		return arrivalCalendar.getTimeInMillis();
	}
	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getHour() {
		return hour;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getMinute() {
		return minute;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getDay() {
		return day;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getMonth() {
		return month;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getYear() {
		return year;
	}
}