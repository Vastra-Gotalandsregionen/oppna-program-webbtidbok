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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.lang.DateHandler;
import se.vgregion.webbtidbok.lang.StringHandler;

public class CalendarUtil implements CalendarUtilInterface {
	static int PREVIOUS = -1;
	static int NEXT = 1;
	private Logger logger = Logger.getLogger(se.vgregion.webbtidbok.calendar.CalendarUtil.class);

	Calendar masterCalendar = Calendar.getInstance();

	BookingFacade bookingFacade;

	public void setBookingFacade(BookingFacade bookingFacade) {
		this.bookingFacade = bookingFacade;
	}

	private List<String> days;
	private List<Boolean> isLink;
	private int index = 0;
	private int commandLinkDayIndex = 0;
	private boolean gotALink = false;
	private boolean isEmptyCalendar = true;
	private String selectedDay = "";

	private List<String> color = new ArrayList<String>();

	public void setColor(String colorCode, int index) {
		color.add(index, colorCode);
	}

	// only here to avoid that the color array/list might return a null value
	public String getColor() {
		if (color.size() == 0) {
			return "#fff";
		} else if (color.size() > index && color.get(index) == null) {
			return "#fff";
		} else if (color.size() < index) {
			return "#fff";
		} else {
			if (color.size() > index) {
				return color.get(index);
			} else {
				return "#fff";
			}
		}
	}

	public void setColor(String colorCode) {
		color.add(colorCode);
	}

	/**
	 * Returns the date for the current day
	 * 
	 * @return the day
	 */
	public String getDay() {
		if (index == days.size()) {
			return "";
		} else {
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
		if (commandLinkDayIndex == days.size()) {
			return "";
		} else {
			String day = days.get(commandLinkDayIndex);
			commandLinkDayIndex++;
			return day;
			// return "**** BAPP: commandLinkDayIndex: " + commandLinkDayIndex;
		}
	}

	/**
	 * If the current day should have a link, return 1, else return 0.
	 * 
	 * @return the state associated with this day
	 */
	public int getIsLink() {
		// a return value of 1 means that this day should have a link
		int state = 0;

		if (index == days.size()) {
			return 0;
		}
		boolean link = isLink.get(index);
		if (link == true) {
			state = 1;
			gotALink = true;
		}
		return state;
	}

	/**
	 * If the current day should not have a link, return 1, else return 0.
	 * 
	 * @return the state associated with this day
	 */
	public int getIsNotLink() {
		// a return value 1 means that this day should not have a link
		int state = 0;

		if (index == days.size()) {
			return 0;
		}

		if (gotALink) {
			gotALink = false;
			return 0;
		}

		boolean link = isLink.get(index);
		if (link == false) {
			state = 1;
		}
		return state;
	}

	/**
	 * Returns the name of the current month
	 * 
	 * @return the name of the current month
	 */
	public String getCurrentMonth() {
		return formatMasterCalendar("MMMMM");
	}

	public String getCurrentMonthAndYear() {
		return formatMasterCalendar("MMMMM yyyy");
	}

	public String getCurrentDayMonthAndYear() {
		return formatMasterCalendar("EEEE d MMMMM yyyy");
	}

	private String formatMasterCalendar(String format) {
		return StringHandler.toFirstLetterToUpperCase(StringHandler.formatCalendar(format, masterCalendar));
	}

	public int getCalendarMonth() {
		return this.masterCalendar.get(Calendar.MONTH);
	}

	public void setCalendarMonth(State state, int direction) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, state.getSelectedDate().get(Calendar.YEAR));
		c.set(Calendar.MONTH, state.getSelectedDate().get(Calendar.MONTH));
		c.set(Calendar.DATE, 1);
		c.roll(Calendar.MONTH, direction);

		// reset selected day when the < or > is clicked in calendar
		selectedDay = "";
		state.setSelectedDate(c);
	}

	public void getCalendar(State state, String surgeryId) {
		Calendar cal = state.getSelectedDate() != null ? state.getSelectedDate() : Calendar.getInstance();
		getCalendar(state, surgeryId, cal, null);
	}

	/**
	 * This method is used to generate a calendar month which is to be rendered in the update.xhtml page. The calendar is first
	 * rendered from the kallelse of the person you're logged in as. Then the getCalendar() method may be called if you wish to
	 * switch month with the < > arrows in the gui.
	 * 
	 * @param state
	 */
	public void getCalendar(State state, String surgeryId, Calendar monthToDisplay, Calendar selectedDate) {
		logger.debug("**** CalendarUtil.getCalendar(), Today is " + masterCalendar.getTime().toString());
		// both indexes reset to 0 because we want to restart the index count at beginning of each month
		index = 0;
		commandLinkDayIndex = 0;

		gotALink = false;
		color = new ArrayList<String>();

		// System.out.println("         ****** "+state.getPnr());
		List<Calendar> availableDates = getAvailableDates(state, surgeryId, monthToDisplay);
		createCalendarForMonth(availableDates);

		System.out.println("index: " + index);
	}

	public void setEmptyCalendar(boolean isEmpty) {
		isEmptyCalendar = isEmpty;
	}

	public boolean isEmptyCalendar() {
		return this.isEmptyCalendar;
	}

	private List<Calendar> getAvailableDates(State state, String surgeryId, Calendar monthToDisplay) {
		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.YEAR, monthToDisplay.get(Calendar.YEAR));
		startDate.set(Calendar.MONTH, monthToDisplay.get(Calendar.MONTH));
		startDate.set(Calendar.DATE, 1);

		Calendar endDate = Calendar.getInstance();
		endDate.set(Calendar.YEAR, monthToDisplay.get(Calendar.YEAR));
		endDate.set(Calendar.MONTH, monthToDisplay.get(Calendar.MONTH));
		endDate.set(Calendar.DATE, monthToDisplay.getActualMaximum(Calendar.DAY_OF_MONTH));

		List<Calendar> availableDates = bookingFacade.getFreeDays(state, surgeryId, startDate, endDate);
		setEmptyCalendar(availableDates.isEmpty());

		// TODO: For backwards compatibility only. Remove when possible.
		masterCalendar = DateHandler.cloneCalendar(monthToDisplay);
		state.setDefaultDate(false);
		// End backwards compatibility.

		return availableDates;
	}

	private void createCalendarForMonth(List<Calendar> availableDates) {
		// TODO: highlight exam date
		days = new ArrayList<String>();
		isLink = new ArrayList<Boolean>();

		int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

		// rows == weeks of the month
		List<List<Integer>> rows = getRows(masterCalendar);
		for (List<Integer> row : rows) {

			for (Integer dayToEvaluate : row) {
				// there is no day in the week which is 0, only 1-7, thus days.add("<empty string>")
				if (dayToEvaluate.intValue() == 0) {
					days.add("");
					isLink.add(false);

					this.setColor("#fff");
				} else if (dayToEvaluate.intValue() < today && masterCalendar.get(Calendar.MONTH) == currentMonth) {
					// if day from row is before today, do not render it in calendar, thus days.add("<empty string>")
					days.add("" + dayToEvaluate);
					isLink.add(false);

					this.setColor("#e6e6e6");
				} else if ((dayToEvaluate.intValue() >= today && masterCalendar.get(Calendar.MONTH) == currentMonth)
						|| (masterCalendar.get(Calendar.MONTH) > currentMonth)) {
					if (availableDates.size() > 0) {
						boolean dayToEvaluateIsFound = false;
						for (int index = 0; index < availableDates.size(); index++) {
							Calendar tmpCalendar = availableDates.get(index);
							if (dayToEvaluate.intValue() == tmpCalendar.get(Calendar.DAY_OF_MONTH)) {
								dayToEvaluateIsFound = true;
							}
						}

						if (dayToEvaluateIsFound) {
							days.add("" + dayToEvaluate);
							isLink.add(true);
							this.setColor("#fff");
						} else {
							days.add("" + dayToEvaluate);
							isLink.add(false);
							this.setColor("#e6e6e6");
						}
					} else {
						days.add("" + dayToEvaluate);
						isLink.add(false);
						setColor("#e6e6e6");
					}
				} else if (masterCalendar.get(Calendar.MONTH) < currentMonth) {
					days.add("" + dayToEvaluate);
					isLink.add(false);

					this.setColor("#e6e6e6");
				}
			}
		}
	}

	private List<List<Integer>> getRows(Calendar cal) {
		// The calendar for a month, consisting of up to 6 weeks
		List<List<Integer>> rows = new ArrayList<List<Integer>>();
		// A row represents a single week
		List<Integer> row = new ArrayList<Integer>();
		int emptySlots = 0;

		// Calendar tempCal = cal; do not use a calendar -> masterCalendar you change the objects value by using =, commented by
		// Conny to Örjan
		Calendar tempCal = Calendar.getInstance();
		tempCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		tempCal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		tempCal.set(Calendar.DATE, 1);

		// what is the weekday of the first day of the month?
		// weekdays start from Sunday == 1
		int firstDay = tempCal.get(Calendar.DAY_OF_WEEK);
		// System.out.println(" **** FIRSTDAY: " + firstDay);
		if (firstDay == 1) {
			emptySlots = 6;
			// System.out.println(" #### in IF(firstDay == 1), emptyslots = " + emptySlots);
		} else {
			emptySlots = firstDay - 2;
			// System.out.println(" #### in ELSE, emptyslots = " + emptySlots);
		}
		int printed = 0;
		// System.out.println(cal.get(Calendar.MONTH)+" emptySlots: " + emptySlots);
		for (int i = 0; i < emptySlots; ++i) {
			row.add(0);
			printed++;
			if (printed % 7 == 0) {
				rows.add(row);
				row = new ArrayList<Integer>();
			}
		}
		int totalDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
		for (int i = 1; i < totalDays; i++) {
			row.add(i);
			printed++;
			if (printed % 7 == 0) {
				rows.add(row);
				row = new ArrayList<Integer>();
			}
		}
		rows.add(row);

		return rows;
	}

	public String getSelectedDay() {
		return selectedDay;
	}

	public void setSelectedDay(String selectedDay) {
		this.selectedDay = selectedDay;
		System.out.println(" ssssssss I'm running setSelectedDay() " + selectedDay);
	}

	public void getTimeForChosenDate(State state) {
		int day;
		Calendar selectedDate = Calendar.getInstance();
		System.out.println("++++++ state.getSelectedDate(): " + state.getSelectedDate());

		if (getSelectedDay().isEmpty()) {
			System.out.println("+++++ selectedDay is empty");
			// state.setSelectedDate(null);
		} else {
			day = Integer.valueOf(this.getSelectedDay());
			selectedDate.set(masterCalendar.get(Calendar.YEAR), (masterCalendar.get(Calendar.MONTH)), day);
			System.out.println("+++++++ getTimeForChosenDate(State state).state.getCentralTidbokID(): "
					+ state.getCentralTidbokID() + ", day: " + day);
			System.out.println("selectedDate = " + selectedDate.getTime().toString());
			state.setSelectedDate(selectedDate);
		}

	}
}