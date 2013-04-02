/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */

package se.vgregion.webbtidbok.lang;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateHandler {

	public static final String DEFAULT_TIMEZONE = "Europe/Stockholm";

	public static String setCalendarDateFormat(Calendar selectedCalendar) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String formattedDate = format.format(selectedCalendar.getTime());
		return formattedDate;
	}

	/**
	 * Create a new Calendar object for a given date.
	 */
	public static Calendar calendarFor(int year, int month, int day) {
		return calendarFor(year, month, day, 0, 0, 0);
	}

	/**
	 * Create a new Calendar object for a given date.
	 */
	public static Calendar calendarFor(int year, int month, int day, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month - 1, day, hour, minute, second);
		cal.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
		return cal;
	}

	/**
	 * Create a new XMLGregorianCalendar for a given date.
	 */
	public static XMLGregorianCalendar xmlCalendarFor(int year, int month, int day) {
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendarDate(year, month, day, 0);
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create a new XMLGregorianCalendar for a given date.
	 */
	public static XMLGregorianCalendar xmlCalendarFor(int year, int month, int day, int hour, int minute, int second) {
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(year, month, day, hour, minute, second, 0, 0);
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public static Calendar calendarFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static XMLGregorianCalendar xmlCalendarFromDate(Date date) {
		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public static Calendar cloneCalendar(Calendar cal) {
		if (cal != null) {
			// Sloppy clone, but has to do for now.
			Calendar newCal = Calendar.getInstance();
			newCal.setTime(cal.getTime());
			return newCal;
		} else {
			return null;
		}
	}
}
