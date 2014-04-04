package com.jss.smsclient.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

	private DateUtils() {
		// empty implementation.
	}

	public static String format(Date date, String pattern) {
		String result = null;

		if (date != null && StringUtils.isNotEmpty(pattern)) {
			DateFormat dateFormat = new SimpleDateFormat(pattern);
			result = dateFormat.format(date);			
		}

		return result;
	}
}
