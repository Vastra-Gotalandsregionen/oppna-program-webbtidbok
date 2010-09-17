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

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.lang.DateHandler;

/**
 * This class is used as a switch to choose between different {@link BookingFacade} depending on witch service that is used by the
 * user.
 */
public class BookingFacadeSwitch implements BookingFacade {

	private BookingFactory bookingFactory;

	public void setBookingFactory(BookingFactory factory) {
		bookingFactory = factory;
	}

	private BookingFacade getBookingFacadeForCurrentRequest(State state) {
		return bookingFactory.getService(state);
	}

	@Override
	public Booking getBookingInfo(State state) {
		return getBookingFacadeForCurrentRequest(state).getBookingInfo(state);
	}

	@Override
	public List<Surgery> getAvailableSurgeries(State state) {
		return getBookingFacadeForCurrentRequest(state).getAvailableSurgeries(state);
	}

	@Override
	public boolean login(State state) {
		return false;
	}

	@Override
	public List<Calendar> getFreeDays(State state, String surgeryId, Calendar startDate, Calendar endDate) {
		return getBookingFacadeForCurrentRequest(state).getFreeDays(state, surgeryId, startDate, endDate);
	}

	@Override
	public List<BookingTime> getBookingTime(State state, String sectionId, Calendar selectedDate) {
	    List<BookingTime> times = getBookingFacadeForCurrentRequest(state).getBookingTime(state, sectionId, selectedDate);
	    Collections.sort(times, new CompareBookingTime());
	    return times;
	}

	@Override
	public void reschedule(BookingTime bookingTime, State state) {
		getBookingFacadeForCurrentRequest(state).reschedule(bookingTime, state);
	}

	@Override
	public boolean cancelBooking(State state) {
		return getBookingFacadeForCurrentRequest(state).cancelBooking(state);
	}

	
	private static class CompareBookingTime implements Comparator<BookingTime> {

        @Override
        public int compare(BookingTime arg0, BookingTime arg1) {
            Calendar c0 = DateHandler.calendarFromDate(arg0.getDateTime());
            Calendar c1 = DateHandler.calendarFromDate(arg1.getDateTime());
            return Integer.signum(100 * (c0.get(Calendar.HOUR_OF_DAY) - c1.get(Calendar.HOUR_OF_DAY)) +
                    (c0.get(Calendar.MINUTE) - c1.get(Calendar.MINUTE)));
        }
	    
	}
	
}