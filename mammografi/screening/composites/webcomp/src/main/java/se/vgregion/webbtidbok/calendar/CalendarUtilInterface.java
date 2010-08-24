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
import se.vgregion.webbtidbok.domain.Booking;

public interface CalendarUtilInterface {

  // Our new set of methods start here
  public abstract CalendarHolder createCalendarHolder(State state, Booking booking);
  public abstract void switchLocation(State state, CalendarHolder holder, String currentLocation);
  public abstract void showNextMonth(State state, CalendarHolder holder, String currentLocation);
  public abstract void showPreviousMonth(State state, CalendarHolder holder, String currentLocation);
    
  public abstract List<Calendar> getAvailableDates(State state, String surgeryId, Calendar monthToDisplay);

  // End new set of methods
  
  /**
   * Returns the name of the current month
   * 
   * @return the name of the current month
   */
  public abstract String getCurrentMonth();

  public abstract String getCurrentMonthAndYear();

  public abstract String getCurrentDayMonthAndYear();

  public abstract int getCalendarMonth();

  public abstract void setCalendarMonth(State state, int direction);

  public abstract void getCalendar(State state, String surgeryId);
  
  /**
   * This method is used to generate a calendar month which is to be rendered in the update.xhtml page. The calendar is first rendered from the kallelse of the person you're logged in as. Then the
   * getCalendar() method may be called if you wish to switch month with the < > arrows in the gui.
   * 
   * @param state
   */
  public abstract void getCalendar(State state, String surgeryId, Calendar monthToDisplay, Calendar selectedDate);

  public abstract void setEmptyCalendar(boolean isEmpty);

  public abstract boolean isEmptyCalendar();

  public abstract String getSelectedDay();

  public abstract void setSelectedDay(String selectedDay);

  public abstract void getTimeForChosenDate(State state);

}