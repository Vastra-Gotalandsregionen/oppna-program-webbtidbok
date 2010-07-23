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

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.sectra.BookingInfoLocal;

public interface BookingFacade {

    /**
     * Login to the booking system.
     * 
     * @param state The session state used to store the login credentials.
     * @return true if login succeeded.
     */
    public boolean login(State state);
    
//    public BookingInfoLocal getBooking(State state);
//    
//    public BookingResponseLocal getBooking(State state);
    
    public BookingWrapper getBookingInfo(State state);
}
