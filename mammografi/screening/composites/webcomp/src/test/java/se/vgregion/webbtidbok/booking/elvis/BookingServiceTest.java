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

import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingPlace;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.ObjectFactory;

public class BookingServiceTest {

	private BookingService bookingService;
	private ObjectFactory objectFactory;
	private State state;

	@Before
	public void setUp() throws Exception {
		bookingService = new BookingService();
		bookingService.setHelper(new WebServiceHelperMock());
		bookingService.setMapping(new BookingMapperElvis());
		objectFactory = new ObjectFactory();
		objectFactory.createString("test");
		state = new State();
	}

	@Test
	public void testGetSelectedDefaultItem() {
		assertEquals(3, bookingService.getSelectedDefaultItem(state));
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
	public void testGetBookingPlace(){
		List<BookingPlace> bookingPlace = bookingService.getBookingPlace(state);
		assertEquals(0, bookingPlace.size());
		state.setLoggedIn(true);
		bookingPlace = bookingService.getBookingPlace(state);
		assertEquals(2, bookingPlace.size());
	}
	
	@Test
	public void testGetBookingTime(){
		state.setSelectedDate(Calendar.getInstance());
		List<BookingTimeLocal> bookingTime = bookingService.getBookingTime(state);
		assertNotNull(bookingTime);
		state.setLoggedIn(true);
		bookingTime = bookingService.getBookingTime(state);
	}

	class WebServiceHelperMock extends WebServiceHelper {

		@Override
		public BookingRequest getQueryWSRequest(State loginCredentials) {
			BookingRequest bookingRequest = new BookingRequest();
			return bookingRequest;
		}
		@Override
		public ArrayOfBookingPlace getQueryWSRequestPlaces(
				BookingRequest request) {
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
				bookingResponse.setBokadTid(DatatypeFactory.newInstance()
						.newXMLGregorianCalendar());
			} catch (DatatypeConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bookingResponse;
		}

	}
}
