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

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.lang.DateHandler;
import se.vgregion.webbtidbok.lang.StringHandler;

public class CalendarUtil implements CalendarUtilInterface {
	static int PREVIOUS = -1;
	static int NEXT = 1;
	private Logger logger;
	Calendar masterCalendar = Calendar.getInstance();

	BookingFacade bookingFacade;

	public void setBookingFacade(BookingFacade bookingFacade) {
		this.bookingFacade = bookingFacade;
	}

	private String selectedDay = "";

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

	// REFACTOR: Should not be needed.
	public void setEmptyCalendar(boolean isEmpty) {
	}

	public boolean isEmptyCalendar() {
		return false;
	}

	// REFACTOR: Replaced by holder.showCalendar() + call to getAvailableDates()
	public void getCalendar(State state, String surgeryId) {
	}

	public void getCalendar(State state, String surgeryId, Calendar monthToDisplay, Calendar selectedDate) {
	}

	/**
	 * Create a new calendar holder from a given booking. Used by the UI when opening the update flow.
	 * 
	 * @param state
	 *            The user's state, used for getting login info for WS call.
	 * @param booking
	 *            The user's initial booking. Used for location and time information.
	 * @return A CalendarHolder usable for backing the calendar on the update page.
	 */
	public CalendarHolder createCalendarHolder(State state, Booking booking) {
		Calendar bookingStart = DateHandler.calendarFromDate(booking.getStartTime());
		List<Calendar> availableDates = getAvailableDates(state, booking.getSurgery().getSurgeryId(), bookingStart);
		CalendarHolder holder = new CalendarHolder();
		holder.setCurrentShowingMonth(bookingStart, availableDates);
		holder.setSelectedDate(bookingStart);
		return holder;
	}

	/**
	 * Change selected location for a user.
	 * 
	 * @param state
	 *            The user's state, used for getting login info for WS call.
	 * @param surgeryId
	 *            The surgery we should switch to.
	 * @param holder
	 *            The user's CalendarHolder, which will be updated.
	 */
	public void switchLocation(State state, CalendarHolder holder, String currentLocation) {
		updateCalendarHolder(state, holder, currentLocation, holder.getCurrentMonth());
	}

	public void showNextMonth(State state, CalendarHolder holder, String currentLocation) {
		Calendar cal = holder.getCurrentMonth();
		cal.roll(Calendar.MONTH, true);
		updateCalendarHolder(state, holder, currentLocation, cal);
	}

	public void showPreviousMonth(State state, CalendarHolder holder, String currentLocation) {
		Calendar cal = holder.getCurrentMonth();
		cal.roll(Calendar.MONTH, false);
		updateCalendarHolder(state, holder, currentLocation, cal);
	}

	public void updateCalendarHolder(State state, CalendarHolder holder, String currentLocation, Calendar targetMonth) {
		List<Calendar> availableDates = getAvailableDates(state, currentLocation, targetMonth);
		holder.setCurrentShowingMonth(targetMonth, availableDates);
	}

	// This is our keeper method! :)
	public List<Calendar> getAvailableDates(State state, String surgeryId, Calendar monthToDisplay) {
		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.YEAR, monthToDisplay.get(Calendar.YEAR));
		startDate.set(Calendar.MONTH, monthToDisplay.get(Calendar.MONTH));
		startDate.set(Calendar.DATE, 1);

		Calendar endDate = Calendar.getInstance();
		endDate.set(Calendar.YEAR, monthToDisplay.get(Calendar.YEAR));
		endDate.set(Calendar.MONTH, monthToDisplay.get(Calendar.MONTH));
		endDate.set(Calendar.DATE, monthToDisplay.getActualMaximum(Calendar.DAY_OF_MONTH));

		List<Calendar> availableDates = bookingFacade.getFreeDays(state, surgeryId, startDate, endDate);
		// setEmptyCalendar(availableDates.isEmpty());

		// TODO: For backwards compatibility only. Remove when possible.
		masterCalendar = DateHandler.cloneCalendar(monthToDisplay);
		state.setDefaultDate(false);
		// End backwards compatibility.

		return availableDates;
	}

	// REFACTOR: Replaced by holder.showCalendar()
	private void createCalendarForMonth(List<Calendar> availableDates) {
	}

	private List<List<Integer>> getRows(Calendar cal) {
		return null;
	}

	public String getSelectedDay() {
		return selectedDay;
	}

	public void setSelectedDay(String selectedDay) {
		this.selectedDay = selectedDay;
		logger.debug("CalendarUtil.setSelectedDay() " + selectedDay);
	}

	public void getTimeForChosenDate(State state) {
		// här vill vi plocka ut dagen som en int
		// vi vill ha currentMonth
		// vi vill ha current year
		// vi vill ha CTID
		// pnr from state
		// pin from state
		int day;
		Calendar selectedDate = Calendar.getInstance();
		logger.debug("CalebdarUtil.getTimeForChosenDate(), " + state.getSelectedDate().getTime().toString());

		if (!getSelectedDay().isEmpty()) {
			day = Integer.valueOf(this.getSelectedDay());
			selectedDate.set(masterCalendar.get(Calendar.YEAR), (masterCalendar.get(Calendar.MONTH)), day);
			logger.debug("CalendarUtil.getTimeForChosenDate(State state).state.getCentralTidbokID(): "
					+ state.getCentralTidbokID() + ", day: " + day);
			state.setSelectedDate(selectedDate);
		}

	}
}