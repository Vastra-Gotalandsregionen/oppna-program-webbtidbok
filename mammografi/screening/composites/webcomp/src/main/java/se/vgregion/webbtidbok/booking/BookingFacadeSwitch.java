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

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import se.vgregion.webbtidbok.Places;
import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;

/**
 * This class is used as a switch to choose between different
 * {@link BookingFacade} depending on witch service that is used by the user.
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
	public List<SelectItem> getBookingPlaceSelectItems(State state) {
		return getBookingFacadeForCurrentRequest(state)
				.getBookingPlaceSelectItems(state);
	}

	@Override
	public boolean login(State state) {
		return false;
	}

	@Override
	public int getSelectedDefaultItem(State state) {
		return getBookingFacadeForCurrentRequest(state).getSelectedDefaultItem(
				state);
	}

	@Override
	public Places getSelectedPlace(Places places, State state) {
		return getBookingFacadeForCurrentRequest(state).getSelectedPlace(
				places, state);
	}

	@Override
	public List<Date> getFreeDays(State state, Date startDate, Date endDate) {
		return getBookingFacadeForCurrentRequest(state).getFreeDays(state,
				startDate, endDate);
	}

	@Override
	public void setSelectedItem(Places places, State state) {
		state.setCentralTidbokID(places.getPlacesId());
	}

	@Override
	public List<BookingTime> getBookingTime(State state) {
		return getBookingFacadeForCurrentRequest(state).getBookingTime(state);
	}

	@Override
	public boolean getIsTimeListEmpty(State state) {

		return getBookingFacadeForCurrentRequest(state).getIsTimeListEmpty(
				state);
	}

}