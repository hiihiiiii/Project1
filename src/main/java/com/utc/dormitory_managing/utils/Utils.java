package com.utc.dormitory_managing.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;
@Service
public class Utils {
	public static String convertDateToString(Date date) {
		 Date now = new Date();
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		 String dateString = formatter.format(now);
		 return dateString;
	}
	
	public static Date setTimeContract() {
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(new Date());
		 calendar.add(Calendar.MONTH, 5);
		 calendar.set(Calendar.DATE, 25);
		 calendar.set(Calendar.HOUR_OF_DAY, 0);
		 calendar.set(Calendar.MINUTE, 0);
		 calendar.set(Calendar.SECOND, 0);
		 calendar.set(Calendar.MILLISECOND, 0);
		 Date date = calendar.getTime();
		 return date;
	}
}
