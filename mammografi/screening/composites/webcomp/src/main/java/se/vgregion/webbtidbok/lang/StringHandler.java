/**
 * Copyright 2009 Vastra Gotalandsregionen
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
 */
package se.vgregion.webbtidbok.lang;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHandler {

    // Swedish characters as unicode literals, to prevent matching problems caused by bad compiles.
	private final static String NAMES_REGEX = "(?i)[a-z\u00E5\u00E4\u00F6\u00C5\u00C4\u00D6]+";
	

	public static String toFirstLetterToUpperCase(String s) {

		String firstLetter = s.substring(0, 1);
		firstLetter = firstLetter.toUpperCase();

		String subString = s.substring(1, s.length());

		String returnString = firstLetter + subString;
		return returnString;
	}

	public static String capitalizeName(final String name) {
		Pattern thePattern = Pattern.compile(NAMES_REGEX);
		Matcher theMatcher = thePattern.matcher(name);
		StringBuffer theResult = new StringBuffer();
		while (theMatcher.find()) {
			String theMatch = theMatcher.group().toLowerCase();
			theMatch = theMatch.substring(0, 1).toUpperCase()
					+ theMatch.substring(1, theMatch.length());
			theMatcher.appendReplacement(theResult, theMatch);
		}
		theMatcher.appendTail(theResult);
		
		return theResult.toString();
	}
	
	public static String formatCalendar(String pattern, Calendar cal) {
	    return cal == null ? "" : formatDate(pattern, cal.getTime());
	}
	
	public static String formatDate(String pattern, Date date) {
		Locale localeSweden = new Locale("sv", "SE");
        SimpleDateFormat format = new SimpleDateFormat(pattern,localeSweden);
        return date == null ? "" : format.format(date);
	}
}