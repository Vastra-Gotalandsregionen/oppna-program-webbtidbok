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
package se.vgregion.webbtidbok;

import org.springframework.stereotype.Service;

import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.booking.BookingFactory;

@Service
public class Login {
  private BookingFactory bookingFactory;

  public void setBookingFactory(BookingFactory bookingFactory) {
    this.bookingFactory = bookingFactory;
  }

  public void logout(State loginCredentials) {
    loginCredentials.setPnr("");
    loginCredentials.setPasswd("");
    loginCredentials.setLoggedIn(false);
  }

  public boolean login(State loginCredentials) throws Exception {
    BookingFacade bookingService = bookingFactory.getService(loginCredentials);
    if (bookingService.login(loginCredentials)) {
      loginCredentials.setLoggedIn(true);
      return true;
    } else {
      loginCredentials.setLoggedIn(false);
      return false;
    }
  }
}
