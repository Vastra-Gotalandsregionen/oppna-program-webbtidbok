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
package se.vgregion.webbtidbok.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.elvis.BookingResponseLocal;
import se.vgregion.webbtidbok.booking.elvis.BookingService;
import se.vgregion.webbtidbok.booking.elvis.BookingServiceInterface;
import se.vgregion.webbtidbok.booking.elvis.WebServiceHelper;
import se.vgregion.webbtidbok.lang.DateHandler;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;

public class BookingResponseTests {

  WebServiceHelper ws;

  @Before
  public void setUp() throws Exception {
    ws = new WebServiceHelper();
  }

  @After
  public void tearDown() throws Exception {
  }

  /***
	 * 
	 */
  @Ignore
  @Test
  public void testBookingResponseObject() {

    State credentials = new State();
    credentials.setPasswd("4YL7CXnp");
    credentials.setPnr("19121212-1212");

    BookingRequest request = ws.getQueryWSRequest(credentials);
    BookingResponse bookingResponse = ws.getQueryWS(request);
    if (bookingResponse == null) {
      Assert.assertFalse(true);
    }

    if (bookingResponse.getCentralTidbokID().equals(0)) {
      Assert.assertFalse(true);
    } else {
      System.out.println("CentralTidBokId: " + bookingResponse.getCentralTidbokID());
      System.out.println("Adress: " + bookingResponse.getAddress().getValue());
      System.out.println("Antal ombokningar: " + bookingResponse.getAntalOmbok());
      System.out.println("Bokad tid: " + bookingResponse.getBokadTid().toString());
      System.out.println("Get Epost: " + bookingResponse.getEpost().getValue());
      System.out.println("External ID: " + bookingResponse.getExternalID().getValue());
      System.out.println("Get Max Antal bokningar: " + bookingResponse.getMaxAntalOmbok());
      System.out.println("Get Mobil Tel: " + bookingResponse.getMobilTel().getValue());
      System.out.println("Get Mottagning: " + bookingResponse.getMottagning().getValue());
      System.out.println("Get Namn: " + bookingResponse.getNamn().getValue());
      System.out.println("PNR: " + bookingResponse.getPnr().getValue());
      System.out.println("Vårdgivare: " + bookingResponse.getVardgivare().getValue());

      Assert.assertTrue(true);
    }

  }

  /***
	 * 
	 */
  @Ignore
  @Test
  public void testBookingResponseStateGetBooking() {

    BookingServiceInterface service = new BookingService();

    State credentials = new State();
    // credentials.setPasswd("Zs12JzIW");
    // credentials.setPnr("19121212-1212");
    credentials.setPasswd("4YL7CXnp");
    credentials.setPnr("19121212-1212");
    credentials.setLoggedIn(true);

    BookingResponseLocal bookingResponse = service.getBooking(credentials);
    if (bookingResponse == null) {
      Assert.assertFalse(true);
    }

    if (bookingResponse.getCentralTimeBookId() == 0) {
      Assert.assertFalse(true);
    } else {
      System.out.println("CentralTidBokId: " + bookingResponse.getCentralTimeBookId());
      System.out.println("Adress: " + bookingResponse.getAddress());
      System.out.println("Antal ombokningar: " + bookingResponse.getNumberOfBookings());
      System.out.println("Bokad tid: " + DateHandler.setLocaleString(bookingResponse.getTimeBooking()));
      System.out.println("Get Epost: " + bookingResponse.getEmail());
      System.out.println("External ID: " + bookingResponse.getTeam());
      System.out.println("Get Max Antal bokningar: " + bookingResponse.getMaxNbrBookings());
      System.out.println("Get Mobil Tel: " + bookingResponse.getMobilePhone());
      System.out.println("Get Mottagning: " + bookingResponse.getMainSector());
      System.out.println("Get Namn: " + bookingResponse.getName());
      System.out.println("PNR: " + bookingResponse.getPnr());
      System.out.println("Vårdgivare: " + bookingResponse.getLocalDoctor());

      Assert.assertTrue(true);
    }

  }

}
