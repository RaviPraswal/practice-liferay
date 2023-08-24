package com.liferay.test.employees.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.test.employees.mvc.command.action.EmployeeRegistrationAction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * This method takes the date string, date format and return type like: -
	 * Date, Calendar and String
	 * @param date
	 * @param format
	 * @param returnType
	 * @return
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 */
	public static Object dateFormater(String date, String format, String returnType) throws ClassNotFoundException, ParseException {
		log.debug("Date: "+date+" Format: "+format+" Return Type"+returnType);
		Class<?> returnTypeClass = Class.forName(returnType);
		Date newDate = new Date();
		String formattedDate = StringPool.BLANK;
		Calendar cal = null;
		int year = 0;
		int month = 0;
		int day = 0;

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat = new SimpleDateFormat(format);
		String newDateString = dateFormat.format(sdf1.parse(date));
		newDate = dateFormat.parse(newDateString);
		if (Validator.isNotNull(date)) {
			if(returnTypeClass.getName().equalsIgnoreCase(Date.class.getName())) {
				log.debug("New Date : "+newDate);
				return newDate;
			}
			else if(returnTypeClass.getName().equalsIgnoreCase(Calendar.class.getName())) {
				cal = Calendar.getInstance();
				cal.setTime(newDate);
				year = cal.get(Calendar.YEAR);
				month = cal.get(Calendar.MONTH);
				day = cal.get(Calendar.DAY_OF_MONTH);
				log.debug("New Date : "+day+"-"+month+"-"+year);
				return cal;
			}
			else if(returnTypeClass.getName().equalsIgnoreCase(String.class.getName())) {
				formattedDate = dateFormat.format(date);
				log.debug("New Date : "+formattedDate);
				return formattedDate;
			}
		}
		return returnTypeClass;
	}
	private static Log log = LogFactoryUtil.getLog(DateUtil.class.getName());
}
