package com.liferay.test.employees.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


	public static Object dateFormater(String date, String format, String returnType) throws ClassNotFoundException, ParseException {
		
		Class<?> forName = Class.forName(returnType);
		Date newDate = new Date();
		String formattedDate = StringPool.BLANK;
		Calendar cal = null;
		int year = 0;
		int month = 0;
		int day = 0;
		
		
		DateFormat dateFormat = new SimpleDateFormat(format);
		newDate = dateFormat.parse(date);
		if (Validator.isNotNull(date)) {
			if(forName.getName().equalsIgnoreCase(Date.class.getName())) {
				return newDate;
			}
			else if(forName.getName().equalsIgnoreCase(Calendar.class.getName())) {
				cal = Calendar.getInstance();
				cal.setTime(newDate);
				year = cal.get(Calendar.YEAR);
				month = cal.get(Calendar.MONTH);
				day = cal.get(Calendar.DAY_OF_MONTH);
				return cal;
			}
			else if(forName.getName().equalsIgnoreCase(String.class.getName())) {
				formattedDate = dateFormat.format(date);
				return formattedDate;
			}
		}
		return forName;
	}

}
