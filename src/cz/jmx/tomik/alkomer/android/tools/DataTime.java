package cz.jmx.tomik.alkomer.android.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Context;
import android.util.Log;

public class DataTime {
	
	static public int getDateHour(Date d) {
    	Calendar calendar = GregorianCalendar.getInstance();
    	calendar.setTime(d);
    	return calendar.get(Calendar.HOUR_OF_DAY);
    }        
    
	static public Date getNextHour(Date d, int hours) {
    	Calendar calendar = GregorianCalendar.getInstance();
    	calendar.setTime(d); 
    	calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+hours);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	return calendar.getTime();
    }    
    
	static public String getWeekDay(Date d) {
    	final String[] weekDays = {"??", "NE", "PO", "ÚT", "ST", "ÈT", "PÁ", "SO"};
    	
    	Calendar calendar = GregorianCalendar.getInstance();
    	calendar.setTime(d);   	
    	return weekDays[calendar.get(Calendar.DAY_OF_WEEK)];
    }
    
	static public String getFormalDate(Date d) {
    	Calendar calendar = GregorianCalendar.getInstance();
    	calendar.setTime(d);   	
    	return calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+".";
    }    
    
	static public Date getNextDay(Date d, int days) {
    	Calendar calendar = GregorianCalendar.getInstance();
    	calendar.setTime(d); 
    	calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+days);
    	calendar.set(Calendar.HOUR_OF_DAY, 12);
    	calendar.set(Calendar.MINUTE, 12);
    	calendar.set(Calendar.SECOND, 12);
    	calendar.set(Calendar.MILLISECOND, 12);
    	return calendar.getTime();
    } 	
	
	static public Date convertFromStringToDate(String dateTime, Context context) {
		SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = iso8601Format.parse(dateTime);
		} catch (ParseException e) {
			Log.e("Parsing date", e.getMessage());
		}

		return date;
    }
	
	static public String convertFromDateToString(Date date) {
		SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return iso8601Format.format(date);
    }	

}
