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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.elvis.BookingElvis;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;
import se.vgregion.webbtidbok.servicedef.ServiceDefinition;


public class BookingFacadeSwitchTest {

	private BookingFacadeSwitch bookingFacadeSwitch;
	private State state;
	private BookingFactoryMock bookingFactoryMock;
	private BookingFacadeSectraMock bookingFacadeSectraMock;
	private BookingFacadeElvisMock bookingFacadeElvisMock;
	private SortTestMock bookingFacadeSortMock;
	private Map<String, ServiceDefinition> sdMap;

	@Before
	public void setUp() throws Exception {
		bookingFacadeSwitch = new BookingFacadeSwitch();
		state = new State();
		state.setService("MAMMO_SU");
		bookingFacadeSectraMock = new BookingFacadeSectraMock();
		bookingFacadeElvisMock = new BookingFacadeElvisMock();
		bookingFacadeSortMock = new SortTestMock();
		bookingFactoryMock = new BookingFactoryMock(bookingFacadeSectraMock, bookingFacadeElvisMock, bookingFacadeSortMock);
		bookingFacadeSwitch.setBookingFactory(bookingFactoryMock);
		sdMap = new HashMap<String, ServiceDefinition>();
	}

	@Test
	public void testGetBookingInfo() throws Exception {
		// Test sectra facade.
		bookingFacadeSwitch.getBookingInfo(state);
		assertThatSectraFacadeWasCalled();
		setUp();
		// Test elvis facade
		state.setService("BUKAORTA");
		bookingFacadeSwitch.getBookingInfo(state);
		assertThatElvisFacadeWasCAlled();
	}

	@Test
	public void testGetAvailableSurgeries() throws Exception {
		// Test sectra facade.
		bookingFacadeSwitch.getAvailableSurgeries(state);
		assertThatSectraFacadeWasCalled();
		setUp();
		// Test elvis facade
		state.setService("BUKAORTA");
		bookingFacadeSwitch.getAvailableSurgeries(state);
		assertThatElvisFacadeWasCAlled();
	}

	@Test
	public void testGetFreeDays() throws Exception {
		// Test sectra facade.
		bookingFacadeSwitch.getFreeDays(state, null, null, null);
		assertThatSectraFacadeWasCalled();
		setUp();
		// Test elvis facade
		state.setService("BUKAORTA");
		bookingFacadeSwitch.getFreeDays(state, null, null, null);
		assertThatElvisFacadeWasCAlled();
	}

	@Test
	public void testGetBookingTime() throws Exception {
		// Test sectra facade.
		bookingFacadeSwitch.getBookingTime(state, null, null);
		assertThatSectraFacadeWasCalled();
		setUp();
		// Test elvis facade
		state.setService("BUKAORTA");
		bookingFacadeSwitch.getBookingTime(state, null, null);
		assertThatElvisFacadeWasCAlled();
	}

	private void assertThatSectraFacadeWasCalled() {
		assertFalse(bookingFacadeElvisMock.wasCalled);
		assertTrue(bookingFacadeSectraMock.wasCalled);
	}

	private void assertThatElvisFacadeWasCAlled() {
		assertTrue(bookingFacadeElvisMock.wasCalled);
		assertFalse(bookingFacadeSectraMock.wasCalled);
	}

	@Test
	public void testLogin() {
		assertFalse(bookingFacadeSwitch.login(state));
	}

	@Test
	public void testSortingBookingTimes() {
		state.setService("SORTTEST");
		List<BookingTime> list = bookingFacadeSwitch.getBookingTime(state, null, null);
		assertEquals("2", list.get(0).getBookingTimeId());
		assertEquals("4", list.get(1).getBookingTimeId());
		assertEquals("3", list.get(2).getBookingTimeId());
		assertEquals("1", list.get(3).getBookingTimeId());
	}

	class BookingFacadeSectraMock implements BookingFacade {

		boolean wasCalled;

		@Override
		public Booking getBookingInfo(State state) {

			wasCalled = true;

			return new BookingSectra();
		}

		@Override
		public List<Surgery> getAvailableSurgeries(State state) {
			wasCalled = true;
			return null;
		}

		@Override
		public boolean login(State state) {
			wasCalled = true;
			return false;
		}

		@Override
		public List<Calendar> getFreeDays(State state, String surgeryId, Calendar startDate, Calendar endDate) {
			wasCalled = true;
			return null;
		}

		@Override
		public List<BookingTime> getBookingTime(State state, String sectionId, Calendar selectedDate) {
			wasCalled = true;
			return new ArrayList<BookingTime>();
		}

		@Override
		public void reschedule(BookingTime bookingTime, State state) {

		}

		@Override
		public boolean cancelBooking(State state) {
			return false;
		}

	}

	class BookingFacadeElvisMock implements BookingFacade {

		boolean wasCalled;

		@Override
		public Booking getBookingInfo(State state) {
			wasCalled = true;
			return new BookingElvis();
		}

		@Override
		public List<Surgery> getAvailableSurgeries(State state) {
			wasCalled = true;
			return null;
		}

		@Override
		public boolean login(State state) {
			wasCalled = true;
			return false;
		}

		@Override
		public List<Calendar> getFreeDays(State state, String surgeryId, Calendar startDate, Calendar endDate) {
			wasCalled = true;
			return null;
		}

		@Override
		public List<BookingTime> getBookingTime(State state, String sectionId, Calendar selectedDate) {
			wasCalled = true;
			return new ArrayList<BookingTime>();
		}

		@Override
		public void reschedule(BookingTime bookingTime, State state) {

		}

		@Override
		public boolean cancelBooking(State state) {
			wasCalled = true;
			return false;
		}

	}

	class SortTestMock extends BookingFacadeDummy {
		@Override
		public List<BookingTime> getBookingTime(State state, String sectionId, Calendar selectedDate) {
			ArrayList<BookingTime> list = new ArrayList<BookingTime>();

			list.add(bookingTimeFor("1", "2010-08-26T14:00"));
			list.add(bookingTimeFor("2", "2010-08-26T08:15"));
			list.add(bookingTimeFor("3", "2010-08-26T10:45"));
			list.add(bookingTimeFor("4", "2010-08-26T10:30"));
			return list;
		}

		private BookingTime bookingTimeFor(String id, String time) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
			BookingTime book = new BookingTime();
			book.setBookingTimeId(id);
			try {
				book.setDateTime(sdf.parse(time));
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			return book;
		}
	}

	class BookingFactoryMock implements BookingFactory {

		private BookingFacade sectraService;
		private BookingFacade elvisService;
		private BookingFacade sortService;

		public BookingFactoryMock(BookingFacade sectraMock, BookingFacade elvisMock, BookingFacade sortMock) {
			sectraService = sectraMock;
			elvisService = elvisMock;
			sortService = sortMock;
		}

		@Override
		public BookingFacade getService(State state) {
			return getService(state.getService());
		}

		@Override
		public BookingFacade getService(String serviceId) {
			if ("MAMMO_SU".equals(serviceId)) {
				return sectraService;
			} else if ("BUKAORTA".equals(serviceId)) {
				return elvisService;
			} else if ("SORTTEST".equals(serviceId)) {
				return sortService;
			} else {
				throw new RuntimeException("Incorrect booking service.");
			}
		}

		@Override
		public Map<String, ServiceDefinition> getService() {

			return null;
		}

	}

}
