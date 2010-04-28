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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.ws.ArrayOfCalendar;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.ObjectFactory;
import se.vgregion.webbtidbok.ws.*;

public class CalendarUtil {
	static int PREVIOUS = -1;
	static int NEXT = 1;
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
	
	Calendar masterCalendar = Calendar.getInstance();
	WebServiceHelper wsh = new WebServiceHelper();
	BookingRequest request;

	private List<Calendar> availableDates = new ArrayList<Calendar>();
	private List<String> days;
	private List<Boolean> isLink;
	private int index = 0;
	private boolean gotALink = false;
	private boolean isEmptyCalendar = true;
	private String selectedDay = "";
	
	private List<String> color = new ArrayList<String>();
	
	public void setColor(String colorCode, int index){
		//color.add(colorCode);
		color.add(index, colorCode);
		//color.set(index, colorCode);
	}
	
	//only here to avoid that the color array/list might return a null value
	public String getColor(){
		if(color.size()== 0){
			return "#fff";
		}
		else if(color.size() > index && color.get(index)== null){
			return "#fff";
		}
		else if(color.size() < index){
			return "#fff";
		}
		else{
			if(color.size() > index){
				System.out.println("Returned color for index " + index + ": " + color.get(index));
				return color.get(index);	
			}
			else{
				return "#fff";
			}
		}		
	}
	
	public void setColor(String colorCode){
		color.add(colorCode);
	}
	
	public void emptyColorList(){
		
		int tmpIndex = 0;
		//color.clear();0 
		
		if(color.size() > 0 && !color.get(tmpIndex).equals(null)){
			for(int i = 0; i < color.size();i++){
				color.remove(i);					
				tmpIndex++;
			}		
		}	
	}
	
	/***
	 * 
	 */
	public void setSelectedDay(){
		int year = masterCalendar.get(Calendar.YEAR);
		int month = masterCalendar.get(Calendar.MONTH);
		int day = masterCalendar.get(Calendar.DAY_OF_MONTH);
			
		System.out.println("/******");
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******");
		System.out.println("/******");
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******");
		System.out.println("/******");
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******");
		System.out.println("/******");
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******");
		System.out.println("/******");
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******");
		System.out.println("/******");
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******");
		System.out.println("/******");
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******");
		System.out.println("/******");
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******");
		System.out.println("/******");
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******");
		System.out.println("/******");
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******");
		System.out.println("/******");
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******");
		System.out.println("Selected Date, year: " + year + " month: " + month + ", day: " + day);
	} 
	
	/***
	 * 
	 */
	public String getSelectedDay(){
		int year = masterCalendar.get(Calendar.YEAR);
		int month = masterCalendar.get(Calendar.MONTH);
		int day = Integer.valueOf(this.getDay());
		
		System.out.println("/******");
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******"); 
		System.out.println("*******");
		System.out.println("Selected Date, year: " + year + " month: " + month + ", day: " + day);
		
		return String.valueOf(year +  month + day);
	} 
	
	/**
	 * Returns the date for the current day
	 * : d
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
//			this.setColor("#ffffff", index);
//			System.out.println("++++ white");
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
//			this.setColor("#BBBBBB", index);
//			System.out.println("++++ grey");
		}
		return state;
	}

	/**
	 * Returns the name of the current month
	 * @return the name of the current month
	 */
	public String getCurrentMonth() {
		int month = masterCalendar.get(Calendar.MONTH);
		return monthsSv[month];
	}
	
	public String getCurrentMonthAndYear() {
		return getCurrentMonth() + " " + masterCalendar.get(Calendar.YEAR);
	}
	
	/**
	 * Returns the name of the previous month
	 * @return the name of the month previous to the current month
	 */
	public String getPreviousMonth() {
		int month = masterCalendar.get(Calendar.MONTH);
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
		int month = masterCalendar.get(Calendar.MONTH);
		if(month == Calendar.DECEMBER) {
			month = Calendar.JANUARY;
		}
		else {
			month++;
		}
		return monthsSv[month];
	}
	
	public int getCalendarMonth(){
		return this.masterCalendar.get(Calendar.MONTH);
	}
	
	public void setCalendarMonth(State state, int direction){
		Calendar c = Calendar.getInstance();
		int currentYear = state.getSelectedDate().get(Calendar.YEAR);
		int currentMonth = state.getSelectedDate().get(Calendar.MONTH);

		if(direction == PREVIOUS) {
			System.out.print("     ***** PREVIOUS clicked: ");
			if(currentMonth == Calendar.JANUARY) {
				currentMonth = Calendar.DECEMBER;
				currentYear--;
			}
			else {
				currentMonth--;
			}
		}
		
		if(direction == NEXT) {
			System.out.print("     ***** NEXT clicked: ");
			if(currentMonth == Calendar.DECEMBER) {
				currentMonth = Calendar.JANUARY;
				currentYear++;
			}
			else {
				currentMonth++;
			}
		}
		
		c.set(Calendar.YEAR, currentYear);
		c.set(Calendar.MONTH, currentMonth);
		c.set(Calendar.DATE, 1);
		
		System.out.println("" + currentYear + "-" + currentMonth + "-" +c.get(Calendar.DATE));
		state.setSelectedDate(c);
	}
	
	public void getCalendar(State state) {
		System.out.println("Today is " + masterCalendar.getTime().toString());
		System.out.println("CalendarUtil.getCalendar.getSelectedDate: " + state.getSelectedDate().getTime().toString() );
		
		
		index = 0;
		gotALink = false;
		color = new ArrayList<String>(); 
		if(state.getBookingResponse() == null) {
			webService(state);
		}
		
		System.out.println("         ****** "+state.getPnr());
		createCalendarForMonth(state);
		
		int tmpIndex = 0;
		for(String s : color){	
			System.out.println("color.size(): " + color.size() + ", tempIndex: " + tmpIndex + ", s: " + s);

			tmpIndex++;
		}
		System.out.println("index: " + index);
		tmpIndex = 0;
		
		//this.emptyColorList();
		
		
		
		System.out.println("CalendarUtil.getCalendar.last.state.getSelectedDate: " + state.getSelectedDate().getTime().toString());
		
		
		
	}
	
	private void webService(State state) {
		
		/****
		 * 
		 * Check that selected place is used for gettting calendar with available Dates
		 * 
		 */
		System.out.println("CalendarUtil.webService:CentraltidBokidId " + state.getCentralTidbokID() );
		
		
		
		
		wsh = new WebServiceHelper();
		request = wsh.getQueryWSRequest(state);
		System.out.println("CalendarUtil.webservice.request: ");
		
		BookingResponse response = wsh.getQueryWS(request);
		
		System.out.println("CalendarUtil.webservice.response: " + response.getBokadTid().toString());
		state.setBookingResponse(response);
		
		
	}
	
	public void setEmptyCalendar(boolean isEmpty){
		isEmptyCalendar = isEmpty;
	}
	
	public boolean isEmptyCalendar(){
		return this.isEmptyCalendar;
	}
	
	private List<Calendar> getAvailableDates(State state){
		ObjectFactory objectFactory = new ObjectFactory();
		String pattern = "yyyy-MM-dd";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
    	
	    if(state.isDefaultDate()) {
	    	//temp.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
	    	//temp.set(Calendar.MONTH, this.getCalendarMonth());
	    	XMLGregorianCalendar xmlCal = state.getBookingResponse().getBokadTid();
	    	
	    	System.out.println("CalendarUtil.getAvailableDates.xmlCal: " + xmlCal.toString());
	    	
	    	masterCalendar.set(Calendar.YEAR, xmlCal.getYear());
	    	masterCalendar.set(Calendar.MONTH, xmlCal.getMonth() - 1);
	    	masterCalendar.set(Calendar.DATE, xmlCal.getDay());
	    	state.setSelectedDate(masterCalendar);
	    	state.setDefaultDate(false);
	    	
	    	System.out.println("state.isDefaultDate()--------");
	    	System.out.println("CalendarUtil.getAvailableDates.masterCalendar: " + state.getSelectedDate().getTime().toString());
	    	
	    	
	    }
	    else {
	    	System.out.println("*******ELSE state.isDefaultDate()--------");
	    	
	    	
	    	masterCalendar = state.getSelectedDate();
	    	
	    	System.out.println("CalendarUtil.getAvailableDates.masterCalendar: " + state.getSelectedDate().getTime().toString());
	    	
	    	
	    }
	    
	    Calendar tmpCalendar = Calendar.getInstance();
	    tmpCalendar.set(Calendar.YEAR, masterCalendar.get(Calendar.YEAR));
    	tmpCalendar.set(Calendar.MONTH, masterCalendar.get(Calendar.MONTH));
    	tmpCalendar.set(Calendar.DATE, masterCalendar.get(Calendar.DAY_OF_MONTH));    
	    tmpCalendar.set(Calendar.DATE, 1);
    	String from = format.format(tmpCalendar.getTime());
    
		int lastDay = tmpCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		tmpCalendar.set(Calendar.DATE, lastDay);
	    String to = format.format(tmpCalendar.getTime());

	    System.out.println("FROM: " + from + ", TO: " + to);
		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat(from);
		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat(to);
		
		request.setCentralTidbokID(state.getCentralTidbokID());

		System.out.println("CalendarUtil.getCalendar.centraltidbokid: " + state.getCentralTidbokID());
		System.out.println("CalendarUtil.getCalendar.centraltidbokid.state.getSelectedDay: " + state.getSelectedDate().getTime().toString());

		request.setFromDat(fromDat);
		request.setToDat(toDat);
		
		ArrayOfCalendar calArr = wsh.getQueryWSRequestCalendar(request);
		List<se.vgregion.webbtidbok.ws.Calendar> calList = new ArrayList<se.vgregion.webbtidbok.ws.Calendar>();
		try {
			calList = calArr.getCalendar();

			System.out.println("CalList.size: " + calList.size());
			System.out.println(calArr.getCalendar().size());
			
			for(se.vgregion.webbtidbok.ws.Calendar c : calList){
//				System.out.println("DDDD Datum: " + c.getDatum());
//				System.out.println(".toString: " + c.getDatum().toString());			
			}
			
			if(calList.isEmpty()){
				this.setEmptyCalendar(true);
			}
			else{
				this.setEmptyCalendar(false);
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setEmptyCalendar(true);
		}
		List<Calendar> returnCal = new ArrayList<Calendar>();
		//Set<Calendar> dateSet = new HashSet<Calendar>();
		for(se.vgregion.webbtidbok.ws.Calendar c : calList) {
			Calendar tCal = Calendar.getInstance();
			tCal.set(Calendar.YEAR, c.getDatum().getYear());
			tCal.set(Calendar.MONTH, c.getDatum().getMonth() - 1);
			tCal.set(Calendar.DATE, c.getDatum().getDay());
			//dateSet.add(tCal);
			returnCal.add(tCal);
			System.out.println("CalendarUtil.getAvailableDates.ListCalendar: " + tCal.get(Calendar.DAY_OF_MONTH));
		}

		for(Calendar c: returnCal){
			System.out.println("returnCal.toString: "  +  c.get(Calendar.DAY_OF_MONTH));
		}
		System.out.println("ret.size() - amount of bookable dates within fromDat & toDat: " + returnCal.size());
		
		System.out.println("CalendarUtil.getCalendar.centraltidbokid.state.getSelectedDay: " + state.getSelectedDate().getTime().toString());

		
		return returnCal;
	}
	 	
	private void createCalendarForMonth(State state) {
		//TODO: grey out the days before current date
		//TODO: highlight exam date
		//TODO: grey out unavailable dates

		availableDates = getAvailableDates(state);
		
		System.out.println("CalendarUtil.getCalendar.centraltidbokid.state.getSelectedDay: " + state.getSelectedDate().getTime().toString());

		
		System.out.println(" AAAA availableDates.size(): " + availableDates.size());
		int i = 0;

		days = new ArrayList<String>();
		isLink = new ArrayList<Boolean>();
		//int today = calendar.get(Calendar.DAY_OF_MONTH);
		
		for(Calendar c : availableDates){
			System.out.println("CalendarUtil.createCalendarForMonth: " + c.get(Calendar.DAY_OF_MONTH));
		}
		//int today = calendar.get(Calendar.DAY_OF_MONTH);
		int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		int currentYear  = Calendar.getInstance().get(Calendar.YEAR);

		//is rows == weeks of the month?
		List<List<Integer>> rows = getRows(masterCalendar);
		
		System.out.println("After List<List<Integer>> rows  CalendarUtil.getCalendar.centraltidbokid.state.getSelectedDay: " + state.getSelectedDate().getTime().toString());

		for(List<Integer> row : rows) {
			System.out.println("rows size is: " + rows.size());
			for(Integer dayToEvaluate : row) {
				//there is no day in the week which is 0, only 1-7, thus days.add("<empty string>")
				if(dayToEvaluate.intValue() == 0) {
					days.add("");
					isLink.add(false);
					System.out.println("1 isLink == FALSE");
					this.setColor("#fff");
				} //if day from row is before today, do not render it in calendar, thus days.add("<empty string>")
				else if(dayToEvaluate.intValue() < today && masterCalendar.get(Calendar.MONTH) == currentMonth) {
					days.add("" + dayToEvaluate);
					isLink.add(false);
					System.out.println("2 isLink == FALSE");
					this.setColor("#e6e6e6");
				}
				
				else if( (dayToEvaluate.intValue() == today || dayToEvaluate.intValue() > today &&
						 masterCalendar.get(Calendar.MONTH) == currentMonth  )  || 
						 (  masterCalendar.get(Calendar.MONTH) > currentMonth  ) ) {	
					if( availableDates.size() > 0   ){
						boolean dayToEvaluateIsFound = false;
						for(int index = 0; index < availableDates.size(); index++  ){
							Calendar tmpCalendar = availableDates.get(index);
							if(dayToEvaluate.intValue() == tmpCalendar.get(Calendar.DAY_OF_MONTH) ){
								dayToEvaluateIsFound = true;
							}
						}						
						
						if(dayToEvaluateIsFound){
							days.add("" + dayToEvaluate);
							isLink.add(true);
							this.setColor("#fff");
						}
						else{
							days.add("" + dayToEvaluate);
							isLink.add(false);	
							this.setColor("#e6e6e6");
						}						
					}
					else{
						days.add("" + dayToEvaluate);
						isLink.add(false); 
						setColor("#e6e6e6");
					}
					//availableDates.remove(k);								
					}
				}
			}
		}
		
	private List<List<Integer>> getRows(Calendar cal) {
		//The calender for a month, consisting of up to 6 weeks
		List<List<Integer>> rows = new ArrayList<List<Integer>>();
		//A row represents a single week
		List<Integer> row = new ArrayList<Integer>();
		int emptySlots = 0;
		
		System.out.println("After List<List<Integer>> rows  CalendarUtil.getCalendar.centraltidbokid.cal.getSelectedDay: " + cal.getTime().toString());

		//Calendar tempCal = cal; do not use a calendar -> masterCalendar you change the objects value by using =, commented by Conny to Örjan
		Calendar tempCal = Calendar.getInstance();
	    tempCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
    	tempCal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
    	//tempCal.set(Calendar.DATE, cal.get(Calendar.DAY_OF_MONTH));
		tempCal.set(Calendar.DATE, 1);
		
		System.out.println("After List<List<Integer>> rows  CalendarUtil.getCalendar.centraltidbokid.cal.getSelectedDay: " + cal.getTime().toString());


		//what is the weekday of the first day of the month?
		//weekdays start from Sunday == 1
		int firstDay = tempCal.get(Calendar.DAY_OF_WEEK);
		System.out.println(" **** FIRSTDAY: " + firstDay);
		if(firstDay == 1) {
			emptySlots = 6;
			System.out.println(" #### in IF(firstDay == 1), emptyslots = " + emptySlots);
		}
		else {
			emptySlots = firstDay - 2;	
			System.out.println(" #### in ELSE, emptyslots = " + emptySlots);
		}
		int printed = 0;
		System.out.println(cal.get(Calendar.MONTH)+" emptySlots: " + emptySlots);
		for(int i = 0; i < emptySlots; ++i) {
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