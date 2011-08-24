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
package se.vgregion.webbtidbok.booking.elvis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.ObjectFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/se/vgregion/webbtidbok/booking/web-application-config.xml" })
public class BookingServiceTest {

	private BookingService bookingService;
	private ObjectFactory objectFactory;
	private State state;

	@Before
	public void setUp() throws Exception {
		bookingService = new BookingService();
		bookingService.setHelper(new WebServiceHelperMock());
		bookingService.setMapping(new BookingMapperElvis());
		// bookingService.setHelper(webServiceHelper);
		objectFactory = new ObjectFactory();
		objectFactory.createString("test");
		state = new State();
	}

	@Test
	public void testGetBooking() {
		Booking booking = bookingService.getBooking(state);
		assertNotNull(booking);
		state.setLoggedIn(true);
		booking = bookingService.getBooking(state);

		assertEquals(3, state.getCentralTidbokID());

	}

	@Test
	public void testGetBookingPlace() {
		List<Surgery> bookingPlace = bookingService.getBookingPlace(state);
		assertEquals(0, bookingPlace.size());
		state.setLoggedIn(true);
		bookingPlace = bookingService.getBookingPlace(state);
		assertEquals(2, bookingPlace.size());
	}

	@Test
	public void testGetBookingTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DAY_OF_MONTH, +3);
		List<BookingTime> bookingTime = bookingService.getBookingTime(state, "3", cal);
		assertNotNull(bookingTime);
		state.setLoggedIn(true);
		bookingTime = bookingService.getBookingTime(state, "3", cal);
	}

	/*
	 * This test is IGNORED since one cannot control whether there will be free days in the given interval or not. it is thus not
	 * guaranteed to be repeateable.
	 * 
	 * This test may be ignored in case the test user doesn't work and there isn't any other credentials to use as alternative.
	 * 19420213-8014 kzxpQlLb
	 */
	@Ignore
	@Test
	public void testGetFreeDays() {
		// state.setPasswd("4YL7CXnp");
		// state.setPnr("19121212-1212");
		state.setPasswd("kzxpQlLb");
		state.setPnr("19420213-8014");
		int tidbokID = 3;
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		endDate.add(endDate.MONTH, +1);
		List<Calendar> freeDays = bookingService.getFreeDays(state, tidbokID, startDate, endDate);
		assertNotNull(freeDays);
	}

	class WebServiceHelperMock extends WebServiceHelper {

		@Override
		public ArrayOfBookingTime getQueryWSRequestTime(BookingRequest request) {
			return new ArrayOfBookingTime();
		}

		@Override
		public BookingRequest getQueryWSRequest(State loginCredentials) {
			BookingRequest bookingRequest = new BookingRequest();
			return bookingRequest;
		}

		@Override
		public ArrayOfBookingPlace getQueryWSRequestPlaces(BookingRequest request) {
			se.vgregion.webbtidbok.ws.BookingPlace bookingPlace = new se.vgregion.webbtidbok.ws.BookingPlace();
			ArrayOfBookingPlace arrayOfBookingPlace = new ArrayOfBookingPlace();
			arrayOfBookingPlace.getBookingPlace().add(bookingPlace);
			arrayOfBookingPlace.getBookingPlace().add(bookingPlace);
			return arrayOfBookingPlace;
		}

		@Override
		public BookingResponse getQueryWS(BookingRequest request) {
			BookingResponse bookingResponse = new BookingResponse();
			bookingResponse.setPnr(objectFactory.createString("test"));
			bookingResponse.setNamn(objectFactory.createString("test"));
			bookingResponse.setAddress(objectFactory.createString("test"));
			bookingResponse.setMottagning(objectFactory.createString("test"));
			bookingResponse.setAntalOmbok(1);
			bookingResponse.setMaxAntalOmbok(1);
			bookingResponse.setCentralTidbokID(3);

			try {
				bookingResponse.setBokadTid(DatatypeFactory.newInstance().newXMLGregorianCalendar());
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			return bookingResponse;
		}

	}
}
