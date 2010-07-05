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

import se.vgregion.webbtidbok.booking.elvis.WebServiceHelper;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;

@Service
public class Login {
  private WebServiceHelper webServiceHelper;
  BookingResponse bookingRequest;
  BookingRequest b;
  BookingResponse debugResponse = new BookingResponse();
  BookingResponse response;

  public void setWebServiceHelper(WebServiceHelper webServiceHelper) {
    this.webServiceHelper = webServiceHelper;
  }

  public String message = "";

  public String getPnr() {
    return response.getPnr().toString();
  }

  public String getBesDat() {
    return response.getBokadTid().toString();
  }

  public void logout(State loginCredentials) {
    loginCredentials.setPnr("");
    loginCredentials.setPasswd("");
    loginCredentials.setLoggedIn(false);
  }

  public boolean login(State loginCredentials) throws Exception {

    BookingRequest request = webServiceHelper.getQueryWSRequest(loginCredentials);
    response = webServiceHelper.getQueryWS(request);

    loginCredentials.setBookingResponse(response);

    if (response != null) {
      loginCredentials.setLoggedIn(true);
      return true;
    } else {
      loginCredentials.setLoggedIn(false);
      return false;
    }
  }
}
