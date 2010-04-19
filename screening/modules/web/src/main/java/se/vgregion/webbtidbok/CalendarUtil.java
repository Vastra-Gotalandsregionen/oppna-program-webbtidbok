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
package se.vgregion.webbtidbok;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import se.vgregion.webbtidbok.ws.ArrayOfCalendar;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.ObjectFactory;

public class CalendarUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//Empty first element to start actual days on index 1
	String[] weekDaysSv = { "", "Söndag", "Måndag", "Tisdag", "Onsdag", "Torsdag",
		"Fredag", "Lördag" };
	String[] shortDaysSv = {"", "Må", "Ti", "On", "To", "Fr", "Lö", "Sö"};
	String[] monthsSv = { "Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti",
		"September", "Oktober", "November", "December"};
	String[] weekDaysEn = { "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
			"Friday", "Saturday" };
	String[] shortDaysEn = {"", "Må", "Ti", "On", "To", "Fr", "Lö", "Sö"};
	String[] monthsEn = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December"};
	
	Calendar calendar = Calendar.getInstance();
	WebServiceHelper wsh = new WebServiceHelper();
	BookingRequest request;
	BookingResponseLocal r;


	private List<Calendar> availableDates = new ArrayList<Calendar>();
//	private List<Calendar> renderList = new ArrayList<Calendar>();
	
	private List<String> days;
	private List<Boolean> isLink;
	private int index = 0;
	private boolean gotALink = false;
	
	/**
	 * Returns the date for the current day
	 * 
	 * @return the day
	 */
	public String getDay() {
		if(index == days.size()) {
			return "";
		}
		else {
			String day = days.get(index);
			index++;
			return day;
		}
	} 
		
	/**
	 * If the current day should have a link,
	 * return 1, else return 0.
	 * 
	 * @return the state associated with this day
	 */
	public int getIsLink() {
		//a return value of 1 means that this day should have a link
		int state = 0;

		if(index == days.size()) {
			return 0;
		}
		boolean link = isLink.get(index);
		if(link == true) {
			state = 1;
			gotALink = true;
		}
		return state;
	}
	
	/**
	 * If the current day should not have a link,
	 * return 1, else return 0.
	 * 
	 * @return the state associated with this day
	 */
	public int getIsNotLink() {
		//a return value 1 means that this day should not have a link
		int state = 0;

		if(index == days.size()) {
			return 0;
		}

		if(gotALink) {
			gotALink = false;
			return 0;
		}

		boolean link = isLink.get(index);
		if(link == false) {
			state = 1;
		}
		return state;
	}

	/**
	 * Returns the name of the current month
	 * @return the name of the current month
	 */
	public String getCurrentMonth() {
		int month = calendar.get(Calendar.MONTH);
		return monthsSv[month];
	}
	
	/**
	 * Returns the name of the previous month
	 * @return the name of the month previous to the current month
	 */
	public String getPreviousMonth() {
		int month = calendar.get(Calendar.MONTH);
		if(month == Calendar.JANUARY) {
			month = Calendar.DECEMBER;
		}
		else {
			month--;
		}
		return monthsSv[month];
	}

	/**
	 * Returns the name of the next month
	 * @return the name of the month after to the current month
	 */
	public String getNextMonth() {
		int month = calendar.get(Calendar.MONTH);
		if(month == Calendar.DECEMBER) {
			month = Calendar.JANUARY;
		}
		else {
			month++;
		}
		return monthsSv[month];
	}
	
	/**
	 * Initialize a CalenderUtil object for the given month
	 * @param c a Calendar object
	 * @return a CalendarUtil object
	 */
	public CalendarUtil getCalendar() {
		index = 0;
		createCalendarForMonth();
		return this;
	}
	
	/**
	 * Initialize a CalenderUtil object for the given month
	 * @param c a Calendar object
	 * @return a CalendarUtil object
	 */
	public CalendarUtil getCalendar(Calendar c) {
		index = 0;
		this.calendar = c;
		createCalendarForMonth();
		return this;
	}
	
	public CalendarUtil getCalendar(State state) {
		System.out.println("Today is "+calendar.getTime().toString());
		index = 0;
		webService(state);
//		XMLGregorianCalendar d = r.getTimeBooking();
		Calendar c = Calendar.getInstance();
		c.setTime(r.getTimeBooking());

		System.out.println("         ****** "+state.getPnr());
		
		//calendar.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), c.get(Calendar.HOUR), c.get(Calendar.MINUTE));
		availableDates = getAvailableDates(state);
		
		createCalendarForMonth();
		return this;
	}
	
	private void webService(State state) {
		wsh = new WebServiceHelper();
		request = wsh.getQueryWSRequest(state);
		r = new BookingResponseLocal(wsh.getQueryWS(request));
	}
	
	private List<Calendar> getAvailableDates(State state){
		ObjectFactory objectFactory = new ObjectFactory();
		java.util.Calendar temp;

		String pattern = "yyyy-MM-dd";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
	    	    
	    temp = Calendar.getInstance();
	    temp.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
	    temp.set(Calendar.DATE, 1);
	    String from = format.format(temp.getTime());
	    
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	    temp.set(Calendar.DATE, lastDay);
	    String to = format.format(temp.getTime());

		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat(from);
		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat(to);
		
//		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat("2010-04-01");
//		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat("2010-04-31");
		
		request.setCentralTidbokID(1);
		request.setFromDat(fromDat);
		request.setToDat(toDat);
		
		ArrayOfCalendar calArr = wsh.getQueryWSRequestCalendar(request);
		List<se.vgregion.webbtidbok.ws.Calendar> calList = new ArrayList<se.vgregion.webbtidbok.ws.Calendar>();
		try {
			calList = calArr.getCalendar();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Set<Calendar> dateSet = new HashSet<Calendar>();
		for(se.vgregion.webbtidbok.ws.Calendar c : calList) {
			temp.set(Calendar.YEAR, c.getDatum().getYear());
			temp.set(Calendar.MONTH, c.getDatum().getMonth());
			temp.set(Calendar.DATE, c.getDatum().getDay());
			dateSet.add(temp);
		}

		List<Calendar> ret = new ArrayList<Calendar>();
		for(Calendar t : dateSet) {
			ret.add(t);
		}
		
		System.out.println("ret.size() - amount of bookable dates within fromDat & toDat: " + ret.size());
		return ret;
	}
		
	private void createCalendarForMonth() {
		//TODO: grey out the days before current date
		//TODO: highlight exam date
		//TODO: grey out unavailable dates

		days = new ArrayList<String>();
		isLink = new ArrayList<Boolean>();

		int today = calendar.get(Calendar.DAY_OF_MONTH);

		//uncomment this to use testdata instead of data from the web service
//		List<Calendar> testDates = testData();
//		for(int i = 0; i < testDates.size(); i++) {
//			if(testDates.get(0).get(Calendar.DAY_OF_MONTH) < today) {
//				testDates.remove(0);
//			}
//		}
		
		for(int i = 0; i < availableDates.size(); i++) {
			if(availableDates.get(0).get(Calendar.DAY_OF_MONTH) < today) {
				availableDates.remove(0);
			}
		}
		
//		renderList = new ArrayList<Calendar>(testDates);
//		Collections.copy(renderList, testDates);

		List<List<Integer>> rows = getRows(calendar);

		for(List<Integer> row : rows) {
			for(Integer day : row) {
				if(day == 0) {
					days.add("");
					isLink.add(false);
				}
				else if(day < today) {
					days.add("" + day);
					isLink.add(false);
				}
				else {
					if(availableDates.size() > 0) {
						Calendar b = availableDates.get(0);
						if(b.get(Calendar.DAY_OF_MONTH) == day) {
							days.add("" + day);
							isLink.add(true);
							availableDates.remove(0);
						}
						else {
							days.add("" + day);
							isLink.add(false);
						}
					}
					else {
						days.add("" + day);
						isLink.add(false);
					}
				}
			}
		}
	}
	
	private List<List<Integer>> getRows(Calendar cal) {
		List<List<Integer>> rows = new ArrayList<List<Integer>>();
		List<Integer> row = new ArrayList<Integer>();
		int emptySlots;
	
		//which day is the first day of the month?
		Calendar temp = cal;
		temp.set(Calendar.DATE, 1);
		int firstDay = temp.get(Calendar.DAY_OF_WEEK);
		if(firstDay == 1) {
			emptySlots = 7;
		}
		else {
			emptySlots = firstDay - 1;	
		}
		int printed = 0;
		System.out.println(cal.get(Calendar.MONTH)+" emptySlots: "+emptySlots);
		for(int i = 1; i < emptySlots; ++i) {
			row.add(0);
			printed++;
			if(printed % 7 == 0) {
				rows.add(row);
				row = new ArrayList<Integer>();
			}
		}
		int totalDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
		for(int i = 1; i < totalDays; i++) {
			row.add(i);
			printed++;
			if(printed % 7 == 0) {
				rows.add(row);
				row = new ArrayList<Integer>();
			}
		}
		rows.add(row);

		return rows;
	}
	
	private List<Calendar> testData() {
		List<Calendar> arcal = new ArrayList<Calendar>();
		Calendar t = Calendar.getInstance();

//		t.set(2010, 3, 1);
//		arcal.add(t);
//		t = Calendar.getInstance();
//		t.set(2010, 3, 2);
//		arcal.add(t);
//		t = Calendar.getInstance();
//		t.set(2010, 3, 4);
//		arcal.add(t);
//		t = Calendar.getInstance();
//		t.set(2010, 3, 8);
//		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 10, 1);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 10, 2);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 10, 3);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 10, 7);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 10, 9);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 10, 11);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 10, 13);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 10, 15);
		arcal.add(t);
		
		return arcal;
	}
}
