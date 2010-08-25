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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;

public class ElvisBookingFacadeImpl implements BookingFacade {

	private final Log log = LogFactory.getLog(ElvisBookingFacadeImpl.class);

	private WebServiceHelper helper;
	private BookingServiceInterface bookingService;

	public void setBookingService(BookingServiceInterface bookingService) {
		this.bookingService = bookingService;
	}

	public void setHelper(WebServiceHelper webServiceHelper) {
		this.helper = webServiceHelper;
	}

	@Override
	public boolean login(State state) {
		BookingRequest request = helper.getQueryWSRequest(state);
		BookingResponse response = helper.getQueryWS(request);
		state.setBookingResponse(response);
		return response != null;
	}

	@Override
	public Booking getBookingInfo(State state) {
		return bookingService.getBooking(state);
	}

	@Override
	public List<Surgery> getAvailableSurgeries(State state) {
		return bookingService.getSurgeries(state);
	}

	@Override
	public List<Calendar> getFreeDays(State state, String surgeryId, Calendar startDate, Calendar endDate) {
		try {
			int tidbokId = Integer.parseInt(surgeryId, 10);
			return bookingService.getFreeDays(state, tidbokId, startDate, endDate);
		} catch (NumberFormatException e) {
			log.error("Non-numeric surgeryId in Elvis session. Must be an integer.", e);
			throw new RuntimeException("Non-numeric surgeryId in Elvis session. Must be an integer.", e);
		}
	}

	@Override
	public List<BookingTime> getBookingTime(State state, String sectionId, Calendar selectedDate) {
		return bookingService.getBookingTime(state, sectionId, selectedDate);
	}

	@Override
	public void reschedule(BookingTime bookingTime, State state) {
		bookingService.reschedule(bookingTime, state);
	}

	@Override
	public boolean cancelBooking(State state) {
		return bookingService.cancelBooking(state);
	}

}
