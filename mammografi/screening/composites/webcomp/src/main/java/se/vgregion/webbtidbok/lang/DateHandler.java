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

import java.lang.*;
import java.text.*;
import java.util.*;

import javax.xml.datatype.*;

public class DateHandler {


	public static String setLocaleDateFormat(Date date){
		
		Locale locale = new Locale("sv","SE");
		SimpleDateFormat simpleFormat = new SimpleDateFormat("",locale);
		//DateFormat dateFormat = new DateFormat("",locale);
		
		return simpleFormat.format(date);
	}
	
	
	public static String setCalendarDateFormat(Calendar selectedCalendar){
		
		String pattern = "yyyy-MM-dd";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
	    String formattedDate = format.format(selectedCalendar.getTime());
	    return formattedDate;
	}
	
	public static Calendar setCalendarFromGregorianCalendar(XMLGregorianCalendar c){
		
		System.out.println("XMLGregorianCalendar: " + c.toString());
		
		
		
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.set(Calendar.YEAR, c.getYear());
		tmpCalendar.set(Calendar.MONTH, c.getMonth() - 1);
		tmpCalendar.set(Calendar.DATE, c.getDay());
		tmpCalendar.set(Calendar.HOUR, c.getHour());
		tmpCalendar.set(Calendar.MINUTE, c.getMinute());
		
		return tmpCalendar;
	}
	
	
	public static String setCalendarDateFormat(String selectedDate){
		try{
			
		
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date toBeFormatted = format.parse(selectedDate);
			String strFormatted = format.format(toBeFormatted.getTime());
			return strFormatted;
	    
		}catch(ParseException e){
			e.printStackTrace();
		}
		
		return "";
	}

	
	public static String setCalendarTimeFormat(Calendar selectedCalendar){
		
		String pattern = "HH:mm";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
	    String toBeFormatted = format.format(selectedCalendar.getTime());
	    return toBeFormatted;
	}
	
	public static Date setCalendarTimeFormat(String selectedTime){
		try{
			
			
			String pattern = "HH:mm";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date toBeFormatted = format.parse(selectedTime);
			
			
			return toBeFormatted;
	    
		}catch(ParseException e){
			e.printStackTrace();
		}
		
		return new Date();
	}
	
	public static Date setLocaleDate(Date date){
		
		Locale locale = new Locale("sv","SE");
		//SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss",locale);
		
		DateFormat simpleFormat = new SimpleDateFormat("EEEE d MMMM yyyy HH:mm:ss Z",locale);
		try{
			
			System.out.println("setLocaleDate: " + date.toString());
			
			System.out.println("parsa date" + simpleFormat.format(date));
			
			String formatString = simpleFormat.format(date);
			Date newObject = simpleFormat.parse(formatString);
				
			System.out.println("new Object " + newObject.toString());
			
			return simpleFormat.parse(simpleFormat.format(date));
			
		}catch(ParseException pe){
			pe.printStackTrace();
			return null;
		}
	}
	
	public static String setLocaleString(Date date) {
		
		Locale locale = new Locale("sv","SE");
		//SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss",locale);
		
		DateFormat simpleFormat = new SimpleDateFormat("EEEE d MMMM yyyy', klockan ' HH:mm", locale);
		try {
			
			
			String formatString = simpleFormat.format(date);
			Date newObject = simpleFormat.parse(formatString);
			
			System.out.println("new Object " + newObject.toString());
			
			return simpleFormat.format(date);
			
		} catch(ParseException pe) {
			pe.printStackTrace();
			return null;
		}	
	}
	

	/**
	 * Create a new Date object for a given date.
	 */
    public static Date dateFor(int year, int month, int day) {
        return calendarFor(year, month, day).getTime();
    }

    /**
     * Create a new Calendar object for a given date.
     */
    public static Calendar calendarFor(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month, day, 0, 0, 0);
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
	
    public static Calendar calendarFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
