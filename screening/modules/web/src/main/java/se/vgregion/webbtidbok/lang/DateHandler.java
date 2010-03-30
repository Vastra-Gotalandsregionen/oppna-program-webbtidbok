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
import java.util.Date;
import java.util.Locale;
import java.util.*;

public class DateHandler {


	public static String setLocaleDateFormat(Date date){
		
		Locale locale = new Locale("sv","SE");
		SimpleDateFormat simpleFormat = new SimpleDateFormat("",locale);
		//DateFormat dateFormat = new DateFormat("",locale);
		
		return simpleFormat.format(date);
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
}
