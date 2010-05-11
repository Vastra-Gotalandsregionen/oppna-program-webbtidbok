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

public interface CalendarUtilInterface {

  public abstract void setColor(String colorCode, int index);

  // only here to avoid that the color array/list might return a null value
  public abstract String getColor();

  public abstract void setColor(String colorCode);

  /**
   * Returns the date for the current day
   * 
   * @return the day
   */
  public abstract String getDay();

  /**
   * Returns the date for the current day
   * 
   * @return the day
   */
  public abstract String getCommandLinkDay();

  /**
   * If the current day should have a link, return 1, else return 0.
   * 
   * @return the state associated with this day
   */
  public abstract int getIsLink();

  /**
   * If the current day should not have a link, return 1, else return 0.
   * 
   * @return the state associated with this day
   */
  public abstract int getIsNotLink();

  /**
   * Returns the name of the current month
   * 
   * @return the name of the current month
   */
  public abstract String getCurrentMonth();

  public abstract String getCurrentMonthAndYear();

  public abstract String getCurrentDayMonthAndYear();

  /**
   * Returns the name of the previous month
   * 
   * @return the name of the month previous to the current month
   */
  public abstract String getPreviousMonth();

  /**
   * Returns the name of the next month
   * 
   * @return the name of the month after to the current month
   */
  public abstract String getNextMonth();

  public abstract int getCalendarMonth();

  public abstract void setCalendarMonth(State state, int direction);

  /**
   * This method is used to generate a calendar month which is to be rendered in the update.xhtml page. The calendar is first rendered from the kallelse of the person you're logged in as. Then the
   * getCalendar() method may be called if you wish to switch month with the < > arrows in the gui.
   * 
   * @param state
   */
  public abstract void getCalendar(State state);

  public abstract void setEmptyCalendar(boolean isEmpty);

  public abstract boolean isEmptyCalendar();

  public abstract String getSelectedDay();

  public abstract void setSelectedDay(String selectedDay);

  public abstract void getTimeForChosenDate(State state);

}