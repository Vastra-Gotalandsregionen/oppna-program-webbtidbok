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


import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.ObjectFactory;

public class BookingMapperElvisTest {

  private static final String ADDRESS = "Vårgatan 2";
  private static final String NAME = "David Bennehult";
  private static final String PNR = "19770220-2222";
  private BookingMapperElvis bookingMapperElvis;
  private BookingResponse bookingResponse;

  @Before
  public void setUp() throws Exception {
    bookingMapperElvis = new BookingMapperElvis();
    createBookingResponseObject();
  }

  private void createBookingResponseObject() throws DatatypeConfigurationException {
    ObjectFactory objectFactory = new ObjectFactory();
    bookingResponse = new BookingResponse();
    bookingResponse.setPnr(objectFactory.createString(PNR));
    bookingResponse.setNamn(objectFactory.createString(NAME));
    bookingResponse.setVardgivare(objectFactory.createString("Sahlgrenska"));
    bookingResponse.setAddress(objectFactory.createString(ADDRESS));
    bookingResponse.setVardgivare(objectFactory.createString("VGR"));
    bookingResponse.setMottagning(objectFactory.createString("Akuten"));
    bookingResponse.setMobilTel(objectFactory.createString("50505050"));
    bookingResponse.setEpost(objectFactory.createString("test@gmail.com"));
    bookingResponse.setExternalID(objectFactory.createString("43453532"));
    bookingResponse.setAntalOmbok(1);
    bookingResponse.setMaxAntalOmbok(1);
    bookingResponse.setCentralTidbokID(3);
    bookingResponse.setBokadTid(DatatypeFactory.newInstance().newXMLGregorianCalendar());
  }
  
  @Test
  public void testBookingMapping(){
    Booking bookingMapping = bookingMapperElvis.bookingMapping(bookingResponse);
    assertEquals(NAME, bookingMapping.getPatientName());
    assertEquals(ADDRESS, bookingMapping.getSurgeryAddress());
  }

}
