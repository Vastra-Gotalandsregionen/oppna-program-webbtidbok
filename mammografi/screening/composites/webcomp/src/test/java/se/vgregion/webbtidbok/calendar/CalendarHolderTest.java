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
    public void testGetCurrentMonthString() {
        holder.setCurrentShowingMonth(DateHandler.calendarFor(2010, 8, 1), new ArrayList<Calendar>());
        assertEquals("Augusti 2010", holder.getCurrentMonthString());
    }

    @Test
    public void testGetSelectedDateString() {
        holder.setSelectedDate(DateHandler.calendarFor(2010, 8, 3));
        assertEquals("Tisdag 3 augusti 2010", holder.getSelectedDateString());
    }

}
