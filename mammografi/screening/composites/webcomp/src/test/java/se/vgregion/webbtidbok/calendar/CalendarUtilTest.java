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

import static org.junit.Assert.assertEquals;
import static se.vgregion.webbtidbok.lang.DateHandler.calendarFor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.lang.DateHandler;

public class CalendarUtilTest {

    private CalendarUtil util;
    
    @Before
    public void setUp() throws Exception {
        util = new CalendarUtil();
    }

    @Test
    public void testSetCalendarMonth() {
        State state = new State();
        Calendar cal = calendarFor(2010, 8, 1);
        state.setSelectedDate(cal);
        
        util.setCalendarMonth(state, 1);
        assertCalendar(state.getSelectedDate(), 2010, 9, 1);

        util.setCalendarMonth(state, 1);
        assertCalendar(state.getSelectedDate(), 2010, 10, 1);
    
        util.setCalendarMonth(state, -1);
        assertCalendar(state.getSelectedDate(), 2010, 9, 1);
    }
    
    @Test
    public void testGetCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        util.masterCalendar = calendarFor(2010, 8, 1);
        System.out.println(sdf.format(util.masterCalendar.getTime()));

        assertEquals("Augusti", util.getCurrentMonth());
    }

    @Test
    public void testGetCurrentMonthAndYear() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        util.masterCalendar = calendarFor(2010, 8, 1);
        System.out.println(sdf.format(util.masterCalendar.getTime()));

        assertEquals("Augusti 2010", util.getCurrentMonthAndYear());
    }

    @Test
    public void testGetCurrentDayMonthAndYear() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        util.masterCalendar = calendarFor(2010, 8, 3);
        System.out.println(sdf.format(util.masterCalendar.getTime()));

        assertEquals("Tisdag 3 augusti 2010", util.getCurrentDayMonthAndYear());
    }

    @Test
    public void testGetCalendarMonth() {
        util.masterCalendar = calendarFor(2010, 8, 1);
        // Calendar months are zero based, for some unknown reason.
        assertEquals(7, util.getCalendarMonth());
    }
    
    private void assertCalendar(Calendar cal, int year, int month, int day) {
        assertEquals(year, cal.get(Calendar.YEAR));
        assertEquals(month, cal.get(Calendar.MONTH)+1);
        assertEquals(day, cal.get(Calendar.DAY_OF_MONTH));
    }
    
    @Test
    public void testGetTimeForChosenDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        State state = new State();
        state.setSelectedDate(DateHandler.calendarFor(2010, 8, 1));
        
        util.masterCalendar = DateHandler.calendarFor(2010, 9, 15);
        util.setSelectedDay("25");
        
        util.getTimeForChosenDate(state);
        assertEquals("2010-09-25", sdf.format(state.getSelectedDate().getTime()));
        
        util.setSelectedDay("");
        assertEquals("2010-09-25", sdf.format(state.getSelectedDate().getTime()));
    }
}
