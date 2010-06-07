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
package se.vgregion.webbtidbok.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarPrinter implements Serializable {
	private static final long serialVersionUID = 1L;
	//Empty first elem to start actual days on index 1
	String[] weekDays = { "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
		"Friday", "Saturday" };
	String[] shortDays = {"", "Må", "Ti", "On", "To", "Fr", "Lö", "Sö"};
	String[] months = { "January", "February", "March", "April", "May", "June", "July", "August",
		"September", "October", "November", "December"};
	private String calendar;
	
	private List<CalendarRow> calendarRows;
	
	public List<CalendarRow> getRows() {
		Calendar cal = Calendar.getInstance();
		getRows(cal);
		
		return calendarRows;
	}
	
	public String getCalendar() {
		return calendar;
	}
	
	private List<List<Integer>> getRows(Calendar cal) {
		List<List<Integer>> rows = new ArrayList<List<Integer>>();
		List<Integer> row = new ArrayList<Integer>();
		
		CalendarRow calRow = new CalendarRow();
		
		calendarRows = new ArrayList<CalendarRow>();
		
		int emptySlots;
		//which day is the first day of the month?
		int firstDay = cal.get(Calendar.DAY_OF_WEEK);
		if(firstDay == 1) {
			emptySlots = 6;
		}
		else {
			//day number = 4 = onsdag = två tomma luckor ska in
			emptySlots = firstDay - 2;	
		}
		int printed = 0;

		//		System.out.println("emptySlots: " + emptySlots);
		for(int i = 1; i < emptySlots; ++i) {
			row.add(0);
			printed++;
			if(printed % 7 == 0) {
				rows.add(row);
				row = new ArrayList<Integer>();
			}
		}
		int totalDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
		for(int i = 0; i < totalDays; i++) {
			row.add(i);
			printed++;
			if(printed % 7 == 0) {
				rows.add(row);
				
				calRow.setRow(row);
				calendarRows.add(calRow);
				
				row = new ArrayList<Integer>();
				calRow = new CalendarRow();
			}
		}
		rows.add(row);
		
		calRow.setRow(row);
		calendarRows.add(calRow);

		return rows;
	}

	public CalendarPrinter getCal() {
		return new CalendarPrinter();
	}
	
	public List<CalendarRow> getCalendarRowsText() {
		Calendar cal = Calendar.getInstance();
		System.out.println("Today is "+cal.getTime().toString());

		List<CalendarRow> calendarRows = new ArrayList<CalendarRow>();
		//TODO: this should only be for one month, not the entire year
		//TODO: create html table markup for the calendar
		//TODO: grey out the days before current date
		//TODO: highlight exam date
		//TODO: grey out unavailable dates

		List<Calendar> arcal = new ArrayList<Calendar>();
		Calendar t = Calendar.getInstance();

		t.set(2010, 3, 1);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 3, 2);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 3, 4);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 3, 16);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 3, 19);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 3, 20);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 3, 21);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 3, 23);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 3, 26);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 3, 27);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 3, 28);
		arcal.add(t);
		t = Calendar.getInstance();
		t.set(2010, 3, 29);
		arcal.add(t);
		t = Calendar.getInstance();

		int today = 18; //cal.get(Calendar.DAY_OF_MONTH);

		for(int i = 0; i < arcal.size(); i++) {
			if(arcal.get(0).get(Calendar.DAY_OF_MONTH) < today) {
				arcal.remove(0);
			}
		}

		String htmlOutput = "";
			//"<html>\n";
		cal.set(2010, 3, 1);
		htmlOutput += "<a href=\"prev\">previous</a><h5 style=\"display: inline;\">" + months[3] + "</h5><a href=\"next\">next</a>";
		htmlOutput += "<table border=\"1\">\n";
		htmlOutput += "<tr>";
		for(int dd = 1; dd < shortDays.length; dd++) {
			htmlOutput += "<th>" + shortDays[dd] +"</th>";
		}
		htmlOutput += "</tr>";
		List<List<Integer>> rows = getRows(cal);

 
		for(List<Integer> row : rows) {
			List<String> textRow = new ArrayList<String>();
			htmlOutput += "<tr>";
			for(Integer day : row) {

				if(day == 0) {
					htmlOutput += "<td></td>";
					//						htmlOutput = appendTD(0, htmlOutput, 0);
					
					textRow.add("");
				}
				else if(day < today) {
					htmlOutput += "<td style=\"color: grey;\">" + day + "</td>";
					//						htmlOutput = appendTD(1, htmlOutput, day);
					textRow.add("" + day);
				}
				else {
					if(arcal.size() > 0) {
						Calendar b = arcal.get(0);
						if(b.get(Calendar.DAY_OF_MONTH) == day) {
							htmlOutput += "<td><a href=\"\">" + day + "</a></td>";
							//								htmlOutput = appendTD(2, htmlOutput, day);
							textRow.add("_" + day + "_");
							arcal.remove(0);
						}
						else {
							htmlOutput += "<td style=\"color: grey;\">" + day + "</td>";
							//								htmlOutput = appendTD(1, htmlOutput, day);
							textRow.add("" + day);
						}
					}
					else {
						htmlOutput += "<td style=\"color: grey;\">" + day + "</td>";
						//htmlOutput = appendTD(1, htmlOutput, day);
						textRow.add("" + day);
					}

				}
			}
			htmlOutput += "</tr>\n";
			CalendarRow tRow = new CalendarRow();
			tRow.setRowStrings(textRow);
			calendarRows.add(tRow);
		}
		htmlOutput += "</table>\n";
		System.out.println(htmlOutput);
		calendar = htmlOutput;
		
		return calendarRows;
	}

	private static String appendTD(int type, String html, int value) {
		if(type == 0) {
			html += "<td></td>";
		}
		if(type == 1) {
			html += "<td style=\"color: grey;\">" + value + "</td>";
		}
		if(type == 2) {
			html += "<td><a href=\"\">" + value + "</a></td>";
		}
		return html;
	}}
