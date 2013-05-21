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

import java.util.Calendar;
import java.util.List;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;

/**
 * The interface towards the underlying booking systems.
 * 
 * When implementing a backend for a new booking system, this is the interface to use.
 * All methods have to be implemented, and all objects crossing this boundary have to 
 * be of the Webbtidbok generic version. The webbtidbok should not use any methods
 * of underlying service implementations.
 *
 */
public interface BookingFacade {

	/**
	 * Login to the booking system.
	 * 
	 * This call has to be implemented even if the system does not require login prior
	 * to accepting other calls. (Such as if authentication is done on all requests.)
	 * 
	 * @param state
	 *            The session state used to store the login credentials.
	 * @return true if login succeeded.
	 */
	boolean login(State state);

	/**
	 * Get information on the user's current booking.
	 * 
	 * @param state The state needs to contain valid login credentials. (I.e. the users
	 *              should have logged in.
	 * @return A {@link Booking} object holding all the information needed by the UI.
	 */
	Booking getBookingInfo(State state);

	/**
	 * Reschedule the user's appointment.
	 * 
	 * @param bookingTime The new time chosen by the user.
     * @param state The state needs to contain valid login credentials. (I.e. the users
     *              should have logged in.)
	 */
	void reschedule(BookingTime bookingTime, State state);

	/**
	 * Lists all available surgeries for the relevant procedure.
	 * Note that this list does not cross web service boundaries, so you only get surgeries
	 * available from the same domain as the user is currently booked at.
	 * 
     * @param state The state needs to contain valid login credentials. (I.e. the users
     *              should have logged in.)
	 * @return A list of all available {@link Surgery}.
	 */
	List<Surgery> getAvailableSurgeries(State state);

	/**
	 * Find all days having available times for the selected surgery in the given time span.
	 * 
     * @param state The state needs to contain valid login credentials. (I.e. the users
     *              should have logged in.)
	 * @param surgeryId The {@link Surgery} to list times for.
	 * @param startDate First date in period, inclusive.
	 * @param endDate Last date in period, inclusive.
	 * @return A list of days where available times were found.
	 */
	List<Calendar> getFreeDays(State state, String surgeryId, Calendar startDate, Calendar endDate);

	/**
	 * Get all available times for a given date and surgery.
	 * 
     * @param state The state needs to contain valid login credentials. (I.e. the users
     *              should have logged in.)
	 * @param sectionId The {@link Surgery} to list times for.
	 * @param selectedDate The date to list times for.
	 * @return A list of available times.
	 */
	List<BookingTime> getBookingTime(State state, String sectionId, Calendar selectedDate);

	/**
	 * Cancel the booking for the user.
	 * 
     * @param state The state needs to contain valid login credentials. (I.e. the users
     *              should have logged in.)
	 * @return true if the booking was properly canceled.
	 */
	boolean cancelBooking(State state);

}
