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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.elvis.BookingElvis;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;

public class PatientAgeTest {
	
	private static Calendar today, todayMinusThirtyYears;
	private static final int THIRTY = 30;
	private static final SimpleDateFormat pnrFormat = new SimpleDateFormat("yyyyMMdd-0101");
	private BookingSectra bookingSectra;
	private BookingElvis bookingElvis;

	@Before
	public void setUp() throws Exception {
		today = Calendar.getInstance();
		todayMinusThirtyYears = ((Calendar) today.clone());
		todayMinusThirtyYears.roll(Calendar.YEAR, -THIRTY);
		
		String pnr = pnrFormat.format(todayMinusThirtyYears.getTime());
		
		bookingSectra = new BookingSectra();
		bookingSectra.setPatientId(pnr);
		
		bookingElvis = new BookingElvis();
		bookingElvis.setPatientId(pnr);
	}

	
	public void testAge(Booking booking) {
		assertEquals(THIRTY, booking.getPatientAge());
		
		//Make sure age is unchanged one day back
		todayMinusThirtyYears.roll(Calendar.DAY_OF_YEAR, -1);
		this.setPatientAge(booking, todayMinusThirtyYears);
		assertEquals(THIRTY, booking.getPatientAge());
		//Make sure age has decreased one day ahead
		todayMinusThirtyYears.roll(Calendar.DAY_OF_YEAR, 2);
		this.setPatientAge(booking, todayMinusThirtyYears);
		assertEquals(THIRTY-1, booking.getPatientAge());	
	}
	
	public void testNullAge(Booking booking) {
		assertEquals(0, booking.getPatientAge());
	}
	
	@Test
	public void testAgeSectra(){
		this.testAge(this.bookingSectra);
	}
	
	@Test
	public void testAgeElvis(){
		this.testAge(this.bookingElvis);
	}
	
	@Test
	public void testAgeNullSectra(){
		this.testNullAge(new BookingSectra());
	}
	
	@Test
	public void testAgeNullElvis(){
		this.testNullAge(new BookingElvis());
	}
	
	private void setPatientAge(Booking booking, Calendar cal){
		booking.setPatientId(pnrFormat.format(cal.getTime()));
	}

}
