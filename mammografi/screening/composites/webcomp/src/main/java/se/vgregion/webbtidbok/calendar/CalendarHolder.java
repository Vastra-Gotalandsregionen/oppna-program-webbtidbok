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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.vgregion.webbtidbok.lang.DateHandler;
import se.vgregion.webbtidbok.lang.StringHandler;

/**
 * Acts as a backing bean for the calendar objects.
 */
public class CalendarHolder implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final String INVISIBLE_DAY_COLOR = "#fff";
    private static final String PAST_DAY_COLOR = "#e6e6e6";
    private static final String EMPTY_DAY_COLOR = "#e6e6e6";
    private static final String AVAILABLE_DAY_COLOR = "#fff";
    private static final String SELECTED_DAY_COLOR = "#fff";

    private static final String MONTH_FORMAT = "MMMMM yyyy";
    private static final String DAY_FORMAT = "EEEE d MMMMM yyyy";
    
    
    private Calendar selectedDate;
    private Set<Integer> availableDatesForMonth;
    private Calendar currentShowingMonth;
    private List<List<DayItem>> dayItems;
    
    public CalendarHolder() {
        selectedDate = null;
    }
    
    public void clearSelectedDate() {
        selectedDate = null;
    }

    public void setSelectedDate(Calendar date) {
        selectedDate = DateHandler.cloneCalendar(date);
    }

    public String getSelectedDateString() {
        if (getHasSelectedDate()) {
            return StringHandler.toFirstLetterToUpperCase(
                    StringHandler.formatCalendar(DAY_FORMAT, selectedDate));
        } else {
            return "";
        }
    }
    
    public Calendar getSelectedDate() {
        return DateHandler.cloneCalendar(selectedDate);
    }
    
    public boolean getHasSelectedDate() {
        return selectedDate != null;
    }
    
    public boolean getSelectedDateIsInCurrentMonth() {
        return getHasSelectedDate() && isSameMonth(selectedDate, currentShowingMonth);
    }

    public void setSelectedDate(String day) {
        Calendar cal = getCurrentMonth();
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        selectedDate = cal;
    }

    public String getCurrentMonthString() {
        return StringHandler.toFirstLetterToUpperCase(
                StringHandler.formatCalendar(MONTH_FORMAT, currentShowingMonth));
    }
    
    public Calendar getCurrentMonth() {
        return DateHandler.cloneCalendar(currentShowingMonth);
    }

    public void setCurrentShowingMonth(Calendar cal, List<Calendar> availableDates) {
        availableDatesForMonth = convertAvailableDates(availableDates);
        currentShowingMonth = DateHandler.cloneCalendar(cal);
        currentShowingMonth.set(Calendar.DATE, 1);
        dayItems = updateCalendar(Calendar.getInstance());
    }
    
    private Set<Integer> convertAvailableDates(List<Calendar> dates) {
        Set<Integer> days = new HashSet<Integer>();
        for (Calendar cal : dates) {
            days.add(new Integer(cal.get(Calendar.DAY_OF_MONTH)));
        }
        return days;
    }

    /**
     * This is called by the GUI to show a calendar for a given month.
     * @returns A list of list of day items, i.e. one DayItem for each day
     * in the month, ordered by week.
     */
    public List<List<DayItem>> getCalendar() {
        return dayItems;
    }
        
    /**
     * This is called by the GUI to show a calendar for a given month.
     * @returns A list of list of day items, i.e. one DayItem for each day
     * in the month, ordered by week.
     */
    private List<List<DayItem>> updateCalendar(Calendar today) {
        List<List<DayItem>> calendarGrid = new ArrayList<List<DayItem>>();

        int monthLength = currentShowingMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDay = currentShowingMonth.get(Calendar.DAY_OF_WEEK);
        int emptySlots = firstDay == Calendar.SUNDAY ? 6 : firstDay - 2; // Calendar.MONDAY is day 2 in week. 

        List<DayItem> currentRow = new ArrayList<DayItem>();
        calendarGrid.add(currentRow);

        // Fill in blanks before start of month.
        for (int emptyDayIndex = 1; emptyDayIndex <= emptySlots; emptyDayIndex++) {
            currentRow.add(createEmptyDayItem());
        }

        for (int dayIndex = 1; dayIndex <= monthLength; dayIndex++) {
            currentRow.add(createDayItem(dayIndex, availableDatesForMonth.contains(new Integer(dayIndex)),
                    isToday(today, currentShowingMonth, dayIndex)));
            if (currentRow.size() >= 7) {
                currentRow = new ArrayList<DayItem>();
                calendarGrid.add(currentRow);
            }
        }

        // Fill up rest of last week with blanks.
        while (currentRow.size() < 7) {
            currentRow.add(createEmptyDayItem());
        }

        return calendarGrid;
    }

    private DayItem createEmptyDayItem() {
        return new DayItem("", DayState.EMPTY, false, false);
    }

    private DayItem createDayItem(int day, boolean hasAvailableTimes, boolean isHistoric) {
        DayState color = getCellState(hasAvailableTimes, isHistoric);
        return new DayItem(Integer.toString(day), color, hasAvailableTimes, true);  
    }
    
    private boolean isToday(Calendar today, Calendar month, int day) {
        return isSameMonth(today, month) && today.get(Calendar.DAY_OF_MONTH) == day;
    }
    
    private boolean isSameMonth(Calendar date1, Calendar date2) {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
            date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH);
    }
    
    private DayState getCellState(boolean hasAvailableTimes, boolean isHistoric) {
        if (isHistoric) {
            return DayState.PAST;
        } else if (hasAvailableTimes) {
            return DayState.BOOKABLE;
        } else {
            return DayState.NO_TIMES;
        }
    }

    /**
     * This class is returned to the GUI in the showCalendar call.
     */
    public class DayItem implements Serializable {
        
        private static final long serialVersionUID = 1L;

        String day;
        DayState category;
        boolean isLink;
        boolean visible;
        
        public DayItem(String day, DayState category, boolean isLink, boolean visible) {
            this.day = day;
            this.category = category;
            this.isLink = isLink;
            this.visible = visible;
        }
        
        public String getStyle() {
            return "tb_cal_" + category.toString().toLowerCase();
        }

        public String getDay() {
            return day;
        }

        public boolean getIsLink() {
            return isLink;
        }
        
        public boolean getVisible() {
            return visible;
        }
    }

    public enum DayState { EMPTY, BOOKABLE, NO_TIMES, PAST, TODAY };
}
