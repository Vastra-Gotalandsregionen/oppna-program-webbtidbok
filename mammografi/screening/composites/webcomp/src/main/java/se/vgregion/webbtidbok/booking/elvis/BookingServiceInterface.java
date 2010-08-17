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

import java.util.List;

import se.vgregion.webbtidbok.Places;
import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;

public interface BookingServiceInterface {

	public abstract void setIsUpdated(boolean b);

	public abstract boolean isUpdated();

	public abstract void setFirstPlacesBoolean(boolean b);

	public abstract boolean isFirstPlaces();

	public abstract Booking getBooking(State loginCredentials);

	/**
	 * Method cancelBooking returns true if deleted
	 * 
	 * @param loginCredentials
	 * @return
	 */
	public abstract boolean cancelBooking(State loginCredentials);

	public abstract List<BookingTime> getBookingTime(State loginCredentials);

	public abstract List<Surgery> getSurgeries(State state);

	/****
	 * method setting ombokning
	 * 
	 * @param l
	 */
	public abstract void setBookingTime(BookingTimeLocal bookingTime,
			State credentials);

}