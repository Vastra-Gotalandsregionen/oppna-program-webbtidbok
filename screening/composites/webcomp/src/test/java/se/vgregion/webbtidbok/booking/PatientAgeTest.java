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
	
	private static Calendar today;
	private static Calendar todayMinusThirtyYears, tmpDate; 
	private static String todayMinusThirtyYearsL, todayMinusThirtyYearsLD, 
								todayMinusThirtyYearsS, todayMinusThirtyYearsSD;
	private static String[] personalNumbers = {todayMinusThirtyYearsL, todayMinusThirtyYearsLD,
										todayMinusThirtyYearsS, todayMinusThirtyYearsSD};
	private static final int THIRTY = 30;
	private static final SimpleDateFormat pnrFormatLong = new SimpleDateFormat("yyyyMMdd0101");
	private static final SimpleDateFormat pnrFormatLongDash = new SimpleDateFormat("yyyyMMdd-0101");
	private static final SimpleDateFormat pnrFormatShort = new SimpleDateFormat("yyMMdd0101");
	private static final SimpleDateFormat pnrFormatShortDash = new SimpleDateFormat("yyMMdd-0101");
	private static final SimpleDateFormat[] formats = {pnrFormatLong, pnrFormatLongDash,
														pnrFormatShort, pnrFormatShortDash};
	private BookingSectra bookingSectra;
	private BookingElvis bookingElvis;

	@Before
	public void setUp() throws Exception {
		today = Calendar.getInstance();
		tmpDate = ((Calendar) today.clone());
		todayMinusThirtyYears = ((Calendar) today.clone());
		todayMinusThirtyYears.roll(Calendar.YEAR, -THIRTY);			

		bookingSectra = new BookingSectra();
		bookingSectra.setPatientId(personalNumbers[0]);
		
		bookingElvis = new BookingElvis();
		bookingElvis.setPatientId(personalNumbers[0]);
		
		this.setupFormats();
	}

	private void setupFormats(){
		for(int i=0;i<personalNumbers.length && personalNumbers.length == formats.length;i++){
			personalNumbers[i] = formats[i].format(todayMinusThirtyYears.getTime());
		}
	}
	
	//Makes test with all possible date formats (see formats)
	private void testAllFormats(int expected, Booking booking){
		for(String personalNumber : personalNumbers){
			booking.setPatientId(personalNumber);
			assertEquals(expected, booking.getPatientAge());
		}
	}
	
	public void testAge(Booking booking) {
		this.testAllFormats(THIRTY, booking);
		
		//Make sure age is unchanged one day back
		todayMinusThirtyYears.roll(Calendar.DAY_OF_YEAR, -1);
		this.setupFormats();
		this.testAllFormats(THIRTY, booking);
		//Make sure age has decreased one day ahead
		todayMinusThirtyYears.roll(Calendar.DAY_OF_YEAR, 2);
		this.setupFormats();
		this.testAllFormats(THIRTY-1, booking);	
	}
	
	private void testMalformedPNr(Booking booking){
		booking.setPatientId("19801010-1234FF");
		assertEquals(0, booking.getPatientAge());
	}
	
	private void testNullAge(Booking booking) {
		assertEquals(0, booking.getPatientAge());
	}
	
	private void testTwoDigitYear(Booking booking){
		//The date should go back 99 years from now
		tmpDate.add(Calendar.YEAR, -99);
		booking.setPatientId(pnrFormatShort.format(tmpDate.getTime()));
		assertEquals(booking.getPatientAge(), 99);
		
		//Resets at 100
		tmpDate.add(Calendar.YEAR, -1);
		booking.setPatientId(pnrFormatShort.format(tmpDate.getTime()));
		assertEquals(booking.getPatientAge(), 0);
		
		//Reset
		tmpDate.add(Calendar.YEAR, 100);
	}
	
	@Test
	public void testTwoDigitYearSectra(){
		this.testTwoDigitYear(bookingSectra);
	}
	@Test
	public void testTwoDigitYearElvis(){
		this.testTwoDigitYear(bookingElvis);
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
	
	@Test
	public void testMalformedPNrSectra(){
		this.testMalformedPNr(bookingSectra);
	}
	@Test
	public void testMalformedPNrElvis(){
		this.testMalformedPNr(bookingElvis);
	}

}
