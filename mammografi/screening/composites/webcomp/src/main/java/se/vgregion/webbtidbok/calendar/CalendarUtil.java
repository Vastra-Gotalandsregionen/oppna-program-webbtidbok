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

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.lang.DateHandler;

public class CalendarUtil {

  BookingFacade bookingFacade;

  public void setBookingFacade(BookingFacade bookingFacade) {
    this.bookingFacade = bookingFacade;
  }


  /**
   * Create a new calendar holder from a given booking. Used by the UI when opening the update flow.
   * @param state The user's state, used for getting login info for WS call.
   * @param booking The user's initial booking. Used for location and time information.
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
   * @param state The user's state, used for getting login info for WS call.
   * @param surgeryId The surgery we should switch to. 
   * @param holder The user's CalendarHolder, which will be updated.
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
    //setEmptyCalendar(availableDates.isEmpty());

    // TODO: For backwards compatibility only. Remove when possible.
    state.setDefaultDate(false);
    // End backwards compatibility.
    
    return availableDates;
  }
  
}