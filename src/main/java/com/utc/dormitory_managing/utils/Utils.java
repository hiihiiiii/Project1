package com.utc.dormitory_managing.utils;

import java.text.SimpleDateFormat;
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
}
