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
package se.vgregion.webbtidbok.booking.sectra;

import java.util.Calendar;
import java.util.List;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;
import se.vgregion.webbtidbok.lang.DateHandler;

public class SectraBookingFacadeImpl implements BookingFacade {

	private static final String RESCHEDULE_COMMENT = "Ombokat via webbtidboken";

	private SectraBookingServiceFactory serviceFactory;

	public void setServiceFactory(SectraBookingServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	/**
	 * Use this to get a connection to the proper web service.
	 * 
	 * @param state
	 *            {@link State}
	 * @return {@link SectraBookingServiceInterface}
	 */
	private SectraBookingServiceInterface getService(State state) {
		return serviceFactory.getServiceInstance(state.getService(), state.getPnr(), state.getPasswd());
	}

	@Override
	public boolean login(State state) {
		boolean isLoggedIn = false;
		try {
			// If there's a patientId then set loggedIn to true
			if (!getService(state).getBooking().getPatientId().isEmpty()) {
				isLoggedIn = true;
			}
		} catch (RuntimeException e) {
			// This means we could not log in, probably wrong patient or
			// password.
			// Just stay not logged in.
		}
		state.setLoggedIn(isLoggedIn);
		return isLoggedIn;
	}

	@Override
	public Booking getBookingInfo(State state) {
		Booking booking = null;
		if (state.isLoggedIn()) {
			booking = getService(state).getBooking();
		} else {
			booking = new BookingSectra();
		}
		return booking;
	}

	@Override
	public List<Surgery> getAvailableSurgeries(State state) {
		return getService(state).getSurgeries();
	}

	@Override
	public List<Calendar> getFreeDays(State state, String surgeryId, Calendar startDate, Calendar endDate) {
		return getService(state).getFreeDays(startDate, endDate, surgeryId);
	}

	@Override
	public List<BookingTime> getBookingTime(State state, String sectionId, Calendar selectedDate) {
		Calendar startDate = DateHandler.cloneCalendar(selectedDate);
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		Calendar endDate = DateHandler.cloneCalendar(selectedDate);
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		return getService(state).getFreeTimes(startDate, endDate, sectionId);
	}

	@Override
	public void reschedule(BookingTime bookingTime, State state) {
		getService(state).reschedule(state.getPasswd(), bookingTime.getBookingTimeId(),
				DateHandler.xmlCalendarFromDate(bookingTime.getDateTime()), true, RESCHEDULE_COMMENT);
	}

	@Override
	public boolean cancelBooking(State state) {
		// Call some mail client code here which will mail the NU or SU administration that the patient is canceling the
		// appointment
		// return true or false depending on what the mail client returned
		return true;
	}
}
