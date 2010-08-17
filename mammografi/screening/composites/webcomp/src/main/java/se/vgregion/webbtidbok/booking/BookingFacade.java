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

public interface BookingFacade {

	/**
	 * Login to the booking system.
	 * 
	 * @param state
	 *            The session state used to store the login credentials.
	 * @return true if login succeeded.
	 */
	public boolean login(State state);

	public Booking getBookingInfo(State state);

	List<SelectItem> getBookingPlaceSelectItems(State state);

	int getSelectedDefaultItem(State state);

	Places getSelectedPlace(Places places, State state);

	List<Date> getFreeDays(State state, Date startDate, Date endDate);

	void setSelectedItem(Places places, State state);

	List<BookingTime> getBookingTime(State state);

	boolean getIsTimeListEmpty(State state);

}
