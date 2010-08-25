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
package se.vgregion.webbtidbok.gui;

import java.util.List;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.booking.elvis.BookingService;
import se.vgregion.webbtidbok.booking.elvis.WebServiceHelper;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.lang.DateHandler;

/**
 * This class is a temporary home for methods which previously were part of the booking service, but don't belong there. In order
 * not to pollute the BookingFacade they have been moved here. If you find a better home for a method, please move it.
 */
public class GUIUtilities {

	/*
	 * Please refrain from adding private variables to this class unless strictly necessary.
	 */

	private BookingFacade bookingFacade;
	private BookingService bookingService;

	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	public void setBookingFacade(BookingFacade bookingFacade) {
		this.bookingFacade = bookingFacade;
	}

	public LocationHolder setupLocations(Booking booking, State state) {
		LocationHolder holder = new LocationHolder();
		holder.setAvailableLocations(bookingFacade.getAvailableSurgeries(state));

		if (booking != null && booking.getSurgery() != null && booking.getSurgery().getSurgeryId() != null) {
			holder.setLocationId(booking.getSurgery().getSurgeryId());
		}
		return holder;
	}

	public boolean getIsTimeListEmpty(List<? extends Object> timeList) {
		if (timeList == null) {
			return true;
		} else {
			return timeList.isEmpty();
		}
	}

	// TODO: This method is just to make Elvis calls still work. We should remove this.
	public void setCentralTidbokIDfromLocation(State state, String locationId) {
		try {
			int tidboksId = Integer.parseInt(locationId, 10);
			state.setCentralTidbokID(tidboksId);
		} catch (NumberFormatException e) {
			// Don't do anything if we can't get an int. We probably have a Sectra call,
			// and then it won't be needed anyway.
		}
	}

	public boolean cancelBooking(State state) {
		bookingService = new BookingService();
		bookingService.setHelper(new WebServiceHelper());
		boolean value = bookingService.cancelBooking(state);
		return value;
	}

}
