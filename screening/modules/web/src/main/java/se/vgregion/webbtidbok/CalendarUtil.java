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
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import se.vgregion.webbtidbok.ws.ArrayOfCalendar;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.ObjectFactory;

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
	private List<String> commandLinkDays;
	private List<Boolean> isLink;
	private int index = 0;
	private int commandLinkDayIndex = 0;
	private boolean gotALink = false;
	private boolean isEmptyCalendar = true;
	private String selectedDay = "";
	
	private List<String> color = new ArrayList<String>();
	
	public void setColor(String colorCode, int index){
		color.add(index, colorCode);
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
	
//	public void emptyColorList(){	
//		int tmpIndex = 0;
//	
//		if(color.size() > 0 && !color.get(tmpIndex).equals(null)){
//			for(int i = 0; i < color.size();i++){
//				color.remove(i);					
//				tmpIndex++;
//			}		
//		}	
//	}


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
	 * Returns the date for the current day
	 * 
	 * @return the day
	 */
	public String getCommandLinkDay() {
		if(commandLinkDayIndex == days.size()) {
			return "";
		}
		else {
			String day = days.get(commandLinkDayIndex);
			commandLinkDayIndex++;
			return day;
//			return "**** BAPP: commandLinkDayIndex: " + commandLinkDayIndex;
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
		//reset selected day when the < or > is clicked in calendar
		selectedDay = "";
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
	
	/**
	 * This method is used to generate a calendar month which is to be rendered in the update.xhtml page.
	 * The calendar is first rendered from the kallelse of the person you're logged in as.
	 * Then the getCalendar() method may be called if you wish to switch month with the < > arrows in the gui.
	 * @param state
	 */
	public void getCalendar(State state) {
		System.out.println("Today is " + masterCalendar.getTime().toString());
		
		//both indexes reset to 0 because we want to restart the index count at beginning of each month
		index = 0;
		commandLinkDayIndex = 0;
		
		gotALink = false;
		color = new ArrayList<String>(); 
		if(state.getBookingResponse() == null) {
			webService(state);
		}
		
//		System.out.println("         ****** "+state.getPnr());
		createCalendarForMonth(state);
		
		int tmpIndex = 0;
		for(String s : color){	
			tmpIndex++;
		}
		System.out.println("index: " + index);
		tmpIndex = 0;	
	}

	private void webService(State state) {
		
//		System.out.println("CalendarUtil.webService:CentraltidBokidId " + state.getCentralTidbokID() );
		
		wsh = new WebServiceHelper();
		request = wsh.getQueryWSRequest(state);
		state.setBookingResponse(wsh.getQueryWS(request));
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
	    	XMLGregorianCalendar xmlCal = state.getBookingResponse().getBokadTid();
	    	masterCalendar.set(Calendar.YEAR, xmlCal.getYear());
	    	masterCalendar.set(Calendar.MONTH, xmlCal.getMonth());
	    	masterCalendar.set(Calendar.DATE, xmlCal.getDay());
	    	state.setSelectedDate(masterCalendar);
	    	state.setDefaultDate(false);
	    }
	    else {
	    	masterCalendar = state.getSelectedDate();
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

	    System.out.println("getAvailableDates FROM: " + from + ", TO: " + to);
		JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat(from);
		JAXBElement<String> toDat = objectFactory.createBookingRequestToDat(to);
		
		request.setCentralTidbokID(state.getCentralTidbokID());
		request.setFromDat(fromDat);
		request.setToDat(toDat);
		
		ArrayOfCalendar calArr = wsh.getQueryWSRequestCalendar(request);
		List<se.vgregion.webbtidbok.ws.Calendar> calList = new ArrayList<se.vgregion.webbtidbok.ws.Calendar>();
		try {
			calList = calArr.getCalendar();
			
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
		
		for(se.vgregion.webbtidbok.ws.Calendar c : calList) {
			Calendar tCal = Calendar.getInstance();
			tCal.set(Calendar.YEAR, c.getDatum().getYear());
			tCal.set(Calendar.MONTH, c.getDatum().getMonth() - 1);
			tCal.set(Calendar.DATE, c.getDatum().getDay());
			returnCal.add(tCal);
		}

		return returnCal;
	}
	 	
	private void createCalendarForMonth(State state) {
		//TODO: highlight exam date
		availableDates = getAvailableDates(state);
		days = new ArrayList<String>();
		commandLinkDays = new ArrayList<String>();
		isLink = new ArrayList<Boolean>();

		int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

		// rows == weeks of the month
		List<List<Integer>> rows = getRows(masterCalendar);
		for(List<Integer> row : rows) {

			for(Integer dayToEvaluate : row) {
				//there is no day in the week which is 0, only 1-7, thus days.add("<empty string>")
				if(dayToEvaluate.intValue() == 0) {
					days.add("");
					isLink.add(false);

					this.setColor("#fff");
				} //if day from row is before today, do not render it in calendar, thus days.add("<empty string>")
				else if(dayToEvaluate.intValue() < today && masterCalendar.get(Calendar.MONTH) == currentMonth) {
					days.add("" + dayToEvaluate);
					isLink.add(false);

					this.setColor("#e6e6e6");
				}
				
				else if( (dayToEvaluate.intValue() >= today &&
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
					}
				}
			}
		}
		
	private List<List<Integer>> getRows(Calendar cal) {
		//The calendar for a month, consisting of up to 6 weeks
		List<List<Integer>> rows = new ArrayList<List<Integer>>();
		//A row represents a single week
		List<Integer> row = new ArrayList<Integer>();
		int emptySlots = 0;

		//Calendar tempCal = cal; do not use a calendar -> masterCalendar you change the objects value by using =, commented by Conny to Örjan
		Calendar tempCal = Calendar.getInstance();
	    tempCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
    	tempCal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		tempCal.set(Calendar.DATE, 1);

		//what is the weekday of the first day of the month?
		//weekdays start from Sunday == 1
		int firstDay = tempCal.get(Calendar.DAY_OF_WEEK);
//		System.out.println(" **** FIRSTDAY: " + firstDay);
		if(firstDay == 1) {
			emptySlots = 6;
//			System.out.println(" #### in IF(firstDay == 1), emptyslots = " + emptySlots);
		}
		else {
			emptySlots = firstDay - 2;	
//			System.out.println(" #### in ELSE, emptyslots = " + emptySlots);
		}
		int printed = 0;
//		System.out.println(cal.get(Calendar.MONTH)+" emptySlots: " + emptySlots);
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
	
	public String getSelectedDay(){
		return selectedDay;
	}
	
	public void setSelectedDay(String selectedDay) {
		this.selectedDay = selectedDay;
		System.out.println(" ssssssss I'm running setSelectedDay() " + selectedDay);
	}
	
	public void getTimeForChosenDate(State state){
		//här vill vi plocka ut dagen som en int
		//vi vill ha currentMonth
		//vi vill ha current year
		//vi vill ha CTID
		//pnr from state
		//pin from state
		int day;
		Calendar selectedDate = Calendar.getInstance();
		System.out.println("++++++ state.getSelectedDate(): " + state.getSelectedDate());

		selectedDate.set(state.getSelectedDate().get(Calendar.YEAR),
		
				state.getSelectedDate().get(Calendar.MONTH), state.getSelectedDate().get(Calendar.DATE));
		System.out.println("++++++ state.getSelectedDate() after set: " + selectedDate.get(Calendar.YEAR) + " " + selectedDate.get(Calendar.MONTH) + " " + selectedDate.get(Calendar.DATE));
		
		if(getSelectedDay().isEmpty()) {
			System.out.println("+++++ selectedDay is empty");
//			state.setSelectedDate(null);
		}
		else {
			day = Integer.valueOf(this.getSelectedDay());
			selectedDate.set(masterCalendar.get(Calendar.YEAR), (masterCalendar.get(Calendar.MONTH)), day);
			System.out.println("+++++++ getTimeForChosenDate(State state).state.getCentralTidbokID(): " + state.getCentralTidbokID() + ", day: " + day);
			System.out.println("selectedDate = " + selectedDate.getTime().toString());
			state.setSelectedDate(selectedDate);	
		}

			
	}
}