/**
 * Copyright 2010 Västra Götalandsregionen
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
 *
 */

package se.vgregion.webbtidbok.domain;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class BookingTest {

    private Booking booking;
    
    @Before
    public void setUp() throws Exception {
        booking = new TestBooking();
    }

    @Test
    public void testSetUpdateable() {
        booking.setUpdateable(false);
        assertFalse(booking.isUpdateable());
        booking.setUpdateable(true);
        assertTrue(booking.isUpdateable());
    }

    @Test
    public void testSetStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        
        Calendar cal = Calendar.getInstance();
        Date nowDate = cal.getTime();
        cal.roll(Calendar.MONTH, true);
        Date thenDate = cal.getTime();
        
        booking.setStartTime(nowDate);
        assertEquals(sdf.format(nowDate), sdf.format(booking.getStartTime()));
        booking.setStartTime(thenDate);
        assertEquals(sdf.format(thenDate), sdf.format(booking.getStartTime()));
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testSetImmutableStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        
        Calendar cal = Calendar.getInstance();
        Date origDate = cal.getTime();
        Date mutableDate = cal.getTime();
        
        booking.setStartTime(mutableDate);
        assertEquals(sdf.format(origDate), sdf.format(booking.getStartTime()));
        assertEquals(sdf.format(mutableDate), sdf.format(booking.getStartTime()));

        mutableDate.setYear(mutableDate.getYear() + 1);
        
        assertEquals(sdf.format(origDate), sdf.format(booking.getStartTime()));
        assertFalse(sdf.format(mutableDate).equals(sdf.format(booking.getStartTime())));
        assertNotSame(mutableDate, booking.getStartTime());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetImmutableStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        
        Calendar cal = Calendar.getInstance();
        Date origDate = cal.getTime();
        booking.setStartTime(origDate);

        Date testDate = booking.getStartTime();
        testDate.setYear(testDate.getYear() + 1);
        
        assertEquals(sdf.format(origDate), sdf.format(booking.getStartTime()));
        assertFalse(sdf.format(testDate).equals(sdf.format(booking.getStartTime())));
    }
        
    @Test
    public void testSetPatientId() {
        booking.setPatientId("20000101-0101");
        assertEquals("20000101-0101", booking.getPatientId());
        booking.setPatientId("19000101-0101");
        assertEquals("19000101-0101", booking.getPatientId());
    }

    @Test
    public void testSetPatientName() {
        booking.setPatientName("Anna Andersson");
        assertEquals("Anna Andersson", booking.getPatientName());
        booking.setPatientName("Svea Svensson");
        assertEquals("Svea Svensson", booking.getPatientName());
    }

    @Test
    public void testSetSurgery() {
        Surgery s1 = new Surgery();
        s1.setSurgeryId("s1");
        Surgery s2 = new Surgery();
        s2.setSurgeryId("s2");
        
        booking.setSurgery(s1);
        assertEquals("s1", booking.getSurgery().getSurgeryId());
        booking.setSurgery(s2);
        assertEquals("s2", booking.getSurgery().getSurgeryId());
    }

    private class TestBooking extends Booking {
        private static final long serialVersionUID = 1L;
    }
}
