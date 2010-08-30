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

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.lang.DateHandler;
import se.vgregion.webbtidbok.lang.StringHandler;

public class CalendarHolderTest {

    private CalendarHolder holder;
    
    @Before
    public void setUp() throws Exception {
        holder = new CalendarHolder();
    }

    @Test
    public void testShowCalendar() {
        holder.setCurrentShowingMonth(DateHandler.calendarFor(2010, 8, 1), new ArrayList<Calendar>());
        
        assertionsForTestShowCalendar(holder.getCalendar());

        // Assert once more, to test when we use cached calendar info.
        assertionsForTestShowCalendar(holder.getCalendar());
    }

    private void assertionsForTestShowCalendar(List<List<CalendarHolder.DayItem>> calendarContent) {
        assertEquals(6, calendarContent.size()); // August 2010 spans over 6 weeks.

        // All weeks should have 7 days.
        for (List<CalendarHolder.DayItem> week : calendarContent) {
            assertEquals(7, week.size());
        }
        
        // First Sunday should be August 1.
        assertEquals("1", calendarContent.get(0).get(6).getDay());
        
        // August 31 should be the last Tuesday.
        assertEquals("31", calendarContent.get(5).get(1).getDay());
        
        // The following day should be blank.
        assertEquals("", calendarContent.get(5).get(2).getDay());
    }

    @Test
    public void testShowCalendarStartingNonSunday() {
        holder.setCurrentShowingMonth(DateHandler.calendarFor(2010, 9, 1), new ArrayList<Calendar>());
        List<List<CalendarHolder.DayItem>> calendarContent = holder.getCalendar();
        assertEquals(5, calendarContent.size()); // September 2010 spans over 5 weeks.
        assertEquals("", calendarContent.get(0).get(1).getDay());
        assertEquals("1", calendarContent.get(0).get(2).getDay()); // The 1st is on a Tuesday.
    }
    
    @Test
    public void testGetCurrentMonth() {
        holder.setCurrentShowingMonth(DateHandler.calendarFor(2010, 8, 1), new ArrayList<Calendar>());
        assertEquals("Augusti 2010", holder.getCurrentMonthString());
        assertCalendarDate(2010, 8, 1, holder.getCurrentMonth());
    }

    @Test
    public void testGetSelectedDateString() {
        holder.setSelectedDate(DateHandler.calendarFor(2010, 8, 3));
        assertEquals("Tisdag 3 augusti 2010", holder.getSelectedDateString());
    }

    @Test
    public void testGettingAndSettingSelectedDate() {
        holder.setSelectedDate(DateHandler.calendarFor(2010, 9, 2));
        assertCalendarDate(2010, 9, 2, holder.getSelectedDate());
        holder.clearSelectedDate();
        assertNull(holder.getSelectedDate());
        assertEquals("", holder.getSelectedDateString());
    }
    
    @Test
    public void testSelectedDateInCurrentMonth() {
        holder.setCurrentShowingMonth(DateHandler.calendarFor(2010, 8, 1), new ArrayList<Calendar>());
        holder.clearSelectedDate();
        assertFalse(holder.getSelectedDateIsInCurrentMonth());
        holder.setSelectedDate(DateHandler.calendarFor(2010, 8, 2));
        assertTrue(holder.getSelectedDateIsInCurrentMonth());
        holder.setSelectedDate(DateHandler.calendarFor(2010, 9, 2));
        assertFalse(holder.getSelectedDateIsInCurrentMonth());
    }
    
    @Test
    public void testSetSelectedDateByDay() {
        holder.setCurrentShowingMonth(DateHandler.calendarFor(2010, 8, 1), new ArrayList<Calendar>());
        holder.setSelectedDate("3");
        assertCalendarDate(2010, 8, 3, holder.getSelectedDate());
        holder.setSelectedDate("17");
        assertCalendarDate(2010, 8, 17, holder.getSelectedDate());
        holder.setCurrentShowingMonth(DateHandler.calendarFor(2010, 9, 1), new ArrayList<Calendar>());
        holder.setSelectedDate("5");
        assertCalendarDate(2010, 9, 5, holder.getSelectedDate());
    }
    
    private void assertCalendarDate(int expectedYear, int expectedMonth, int expectedDay, Calendar actual) {
        assertEquals(StringHandler.formatCalendar("yyyy-MM-dd",
                DateHandler.calendarFor(expectedYear, expectedMonth, expectedDay)),
                StringHandler.formatCalendar("yyyy-MM-dd", actual));
    }
}
