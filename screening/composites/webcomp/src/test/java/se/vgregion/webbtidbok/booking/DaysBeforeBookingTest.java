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

package se.vgregion.webbtidbok.booking;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.elvis.BookingElvis;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;

public class DaysBeforeBookingTest {
	
	private static Calendar today, todayPlusThirtyDays, todayMinusThirtyDays;
	private static final int THIRTY = 30;
	private BookingSectra bookingSectra;
	private BookingElvis bookingElvis;

	@Before
	public void setUp() throws Exception {
		today = Calendar.getInstance();
		todayPlusThirtyDays = ((Calendar) today.clone());
		todayPlusThirtyDays.add(Calendar.DAY_OF_MONTH, THIRTY);
		todayMinusThirtyDays = ((Calendar) today.clone());
		todayMinusThirtyDays.add(Calendar.DAY_OF_MONTH, -THIRTY);
		
		bookingSectra = new BookingSectra();
		bookingElvis = new BookingElvis();
	}

	
	public void testDaysBeforeBooking(Booking booking) {
		booking.setStartTime(todayPlusThirtyDays.getTime());
		assertEquals(THIRTY, booking.getDaysBeforeBooking());
		
		booking.setStartTime(today.getTime());
		assertEquals(0, booking.getDaysBeforeBooking());
		
		booking.setStartTime(todayMinusThirtyDays.getTime());
		assertEquals(-THIRTY, booking.getDaysBeforeBooking());
	}
	
	public void testNull(Booking booking) {
		assertEquals(Integer.MIN_VALUE, booking.getDaysBeforeBooking());
	}
	
	@Test
	public void testDaysBeforeBookingSectra(){
		this.testDaysBeforeBooking(this.bookingSectra);
	}
	
	@Test
	public void testDaysBeforeBookingElvis(){
		this.testDaysBeforeBooking(this.bookingElvis);
	}
	
	@Test
	public void testNullSectra(){
		this.testNull(new BookingSectra());
	}
	
	@Test
	public void testNullElvis(){
		this.testNull(new BookingElvis());
	}
}
