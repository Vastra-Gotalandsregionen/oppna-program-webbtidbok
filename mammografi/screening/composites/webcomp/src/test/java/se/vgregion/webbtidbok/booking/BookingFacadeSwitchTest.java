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
package se.vgregion.webbtidbok.booking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.Places;
import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.elvis.BookingElvis;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;

public class BookingFacadeSwitchTest {

	private BookingFacadeSwitch bookingFacadeSwitch;
	private State state;
	private BookingFactoryMock bookingFactoryMock;
	private BookingFacadeSectraMock bookingFacadeSectraMock;
	private BookingFacadeElvisMock bookingFacadeElvisMock;

	@Before
	public void setUp() throws Exception {
		bookingFacadeSwitch = new BookingFacadeSwitch();
		state = new State();
		state.setService("MAMMO_SU");
		bookingFacadeSectraMock = new BookingFacadeSectraMock();
		bookingFacadeElvisMock = new BookingFacadeElvisMock();
		bookingFactoryMock = new BookingFactoryMock(bookingFacadeSectraMock,
				bookingFacadeElvisMock);
		bookingFacadeSwitch.setBookingFactory(bookingFactoryMock);
	}

	@Test
	public void testGetBookingInfo() throws Exception {
		// Test sectra facade.
		bookingFacadeSwitch.getBookingInfo(state);
		assertFalse(bookingFacadeElvisMock.wasCalled);
		assertTrue(bookingFacadeSectraMock.wasCalled);
		setUp();
		// Test elvis facade
		state.setService("BUKAORTA");
		bookingFacadeSwitch.getBookingInfo(state);
		assertTrue(bookingFacadeElvisMock.wasCalled);
		assertFalse(bookingFacadeSectraMock.wasCalled);
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
		public List<Date> getFreeDays(State state, Date startDate, Date endDate) {
			wasCalled = true;
			return null;
		}

		@Override
		public List<BookingTime> getBookingTime(State state) {
            wasCalled = true;
			return null;
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
		public List<Date> getFreeDays(State state, Date startDate, Date endDate) {
			wasCalled = true;
			return null;
		}

		@Override
		public List<BookingTime> getBookingTime(State state) {
            wasCalled = true;
			return null;
		}

	}

	class BookingFactoryMock implements BookingFactory {

		private BookingFacade sectraService;
		private BookingFacade elvisService;

		public BookingFactoryMock(BookingFacade sectraMock,
				BookingFacade elvisMock) {
			sectraService = sectraMock;
			elvisService = elvisMock;
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
			} else {
				throw new RuntimeException("Incorrect booking service.");
			}
		}

	}

}
