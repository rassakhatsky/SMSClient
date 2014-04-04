package com.jss.smsclient.utils;

import java.util.Date;

public final class StringUtils {

	public static final String EMPTY_CHAR = "";

	public static final String SPACE_CHAR = " ";	

	public static final String DOUBLE_QUOTE_CHAR = "\"";

	public static final String NULL_STR = "null";

	public StringUtils() {
		// empty implementation.
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}

		return str.trim().isEmpty();
	}

	public static boolean isNullString(String str) {
		return NULL_STR.equalsIgnoreCase(str);
	}

	public static String removeDoubleQuotes(String str) {
		String result = str;

		if (isNotEmpty(result)) {
			result = result.replaceAll(DOUBLE_QUOTE_CHAR, EMPTY_CHAR);
		}

		return result;
	}

	public static String getStringOrNull(Object obj) {
		if (obj == null) {
			return null;
		}

		String str = obj.toString();
		if (isNullString(str)) {
			return null;
		}

		return str;
	}

	public static Integer parseToIntOrNull(String str) {
		if (isEmpty(str) || isNullString(str)) {
			return null;
		}

		try {
			return new Integer(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Long parseToLongOrNull(String str) {
		if (isEmpty(str) || isNullString(str)) {
			return null;
		}

		try {
			return new Long(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Date parseToDateOrNull(String str) {
		if (isEmpty(str) || isNullString(str)) {
			return null;
		}

		long milliseconds = parseToLongOrNull(str);
		return new Date(milliseconds);
	}
}
