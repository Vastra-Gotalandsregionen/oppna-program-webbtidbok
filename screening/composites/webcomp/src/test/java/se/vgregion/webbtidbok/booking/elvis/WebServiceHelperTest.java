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

package se.vgregion.webbtidbok.booking.elvis;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import java.util.Calendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.crypto.StringEncrypter;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.ArrayOfCalendar;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.ICentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWSCancelBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSConfirmBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetCalandarICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ObjectFactory;

public class WebServiceHelperTest {

  private WebServiceHelper webServiceHelper;
  private StringEncrypterMock stringEncrypterMock;
  private State state;

  @Before
  public void setUp() throws Exception {
    webServiceHelper = new WebServiceHelper();
    stringEncrypterMock = new StringEncrypterMock();
    webServiceHelper.setEncrypter(stringEncrypterMock);
    ElvisWebserviceMock elvisWebserviceMock = new ElvisWebserviceMock();
    webServiceHelper.setElvisWebService(elvisWebserviceMock);
    state = new State();
    state.setPnr("123456");
    state.setPasswd("pwd123");
  }

  @Test
  public void testGetQueryWSRequestState() {

    BookingRequest queryWSRequest = webServiceHelper.getQueryWSRequest(state);
    assertEquals("123456", queryWSRequest.getPnr().getValue());
    assertEquals("pwd123", queryWSRequest.getPin().getValue());
    assertEquals("pwd123", queryWSRequest.getKey().getValue());
    assertEquals(stringEncrypterMock.encodeToBase64("String signed:pwd123".getBytes()), queryWSRequest.getCryptedKey().getValue());
    assertEquals("YES", queryWSRequest.getCert().getValue());
  }

  @Test
  public void testGetQueryWSRequestStateIntStringString() {
    Calendar calendar = Calendar.getInstance();
    String dateFrom = calendar.getTime().toString();
    String dateTo = calendar.getTime().toString();

    BookingRequest queryWSRequest = webServiceHelper.getQueryWSRequest(state, 1, dateFrom, dateTo);
    assertEquals("123456", queryWSRequest.getPnr().getValue());
    assertEquals("pwd123", queryWSRequest.getPin().getValue());
    assertEquals("pwd123", queryWSRequest.getKey().getValue());
    assertEquals(stringEncrypterMock.encodeToBase64("String signed:pwd123".getBytes()), queryWSRequest.getCryptedKey().getValue());
    assertEquals("YES", queryWSRequest.getCert().getValue());
    assertEquals(1, queryWSRequest.getCentralTidbokID().intValue());
    assertEquals(dateFrom, queryWSRequest.getFromDat().getValue());
    assertEquals(dateTo, queryWSRequest.getToDat().getValue());
  }

  @Test
  public void testGetQueryWSRequestBookingTime() {
    BookingRequest bookingRequest = new BookingRequest();
    ArrayOfBookingTime queryWSRequestBookingTime = webServiceHelper.getQueryWSRequestBookingTime(bookingRequest);
    assertNotNull(queryWSRequestBookingTime);
  }

  @Test
  public void testGetQueryWSRequestPlaces() {
    BookingRequest bookingRequest = new BookingRequest();
    ArrayOfBookingPlace queryWSRequestPlaces = webServiceHelper.getQueryWSRequestPlaces(bookingRequest);
    assertNotNull(queryWSRequestPlaces);
  }

  @Test
  public void testGetQueryWSRequestCalendar() {
    BookingRequest bookingRequest = new BookingRequest();
    ArrayOfCalendar queryWSRequestCalendar = webServiceHelper.getQueryWSRequestCalendar(bookingRequest);
    assertNotNull(queryWSRequestCalendar);
  }

  @Test
  public void testGetQueryWSRequestTime() {
    BookingRequest bookingRequest = new BookingRequest();
    ArrayOfBookingTime queryWSRequestTime = webServiceHelper.getQueryWSRequestTime(bookingRequest);
    assertNotNull(queryWSRequestTime);
  }

  @Test
  public void testGetBookingPlaceFromWS() {
    BookingRequest bookingRequest = new BookingRequest();
    ArrayOfBookingPlace bookingPlaceFromWS = webServiceHelper.getBookingPlaceFromWS(bookingRequest);
    assertNotNull(bookingPlaceFromWS);
  }

  @Test
  public void testGetQueryWS() {
    BookingRequest bookingRequest = new BookingRequest();
    BookingResponse queryWS = webServiceHelper.getQueryWS(bookingRequest);
    assertNotNull(queryWS);
  }

  @Test
  public void testSetBookingUpdate() throws DatatypeConfigurationException {
    ObjectFactory objectFactory = new ObjectFactory();
    BookingRequest bookingRequest = new BookingRequest();
    XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    bookingRequest.setBokadTid(xmlGregorianCalendar);
    bookingRequest.setCentralTidbokID(1);
    bookingRequest.setPin(objectFactory.createString("pinCode"));
    bookingRequest.setPnr(objectFactory.createString("pnr"));
    
    BookingResponse setBookingUpdate = webServiceHelper.setBookingUpdate(bookingRequest);
    assertNotNull(setBookingUpdate);
  }

  @Test
  public void testGetQueryWSCancelBooking() {
    BookingRequest bookingRequest = new BookingRequest();
    boolean queryWSCancelBooking = webServiceHelper.getQueryWSCancelBooking(bookingRequest);
    assertTrue(queryWSCancelBooking);
  }

  class StringEncrypterMock extends StringEncrypter {

    @Override
    public byte[] encrypt(String stringToEncrypt) throws Exception {
      return ("String encoded:" + stringToEncrypt).getBytes();
    }

    @Override
    public byte[] signString(String value) {
      return ("String signed:" + value).getBytes();
    }
  }

  class ElvisWebserviceMock implements ICentralBookingWS {

    @Override
    public Boolean cancelBooking(BookingRequest request) throws ICentralBookingWSCancelBookingICFaultFaultFaultMessage {
      return true;
    }

    @Override
    public BookingResponse confirmBooking(BookingRequest request) throws ICentralBookingWSConfirmBookingICFaultFaultFaultMessage {
      return new BookingResponse();
    }

    @Override
    public BookingResponse getBooking(BookingRequest request) throws ICentralBookingWSGetBookingICFaultFaultFaultMessage {
      return new BookingResponse();
    }

    @Override
    public ArrayOfBookingPlace getBookingPlace(BookingRequest request) throws ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage {
      return new ArrayOfBookingPlace();
    }

    @Override
    public ArrayOfBookingTime getBookingTime(BookingRequest request) throws ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage {
      return new ArrayOfBookingTime();
    }

    @Override
    public ArrayOfCalendar getCalandar(BookingRequest request) throws ICentralBookingWSGetCalandarICFaultFaultFaultMessage {
      return new ArrayOfCalendar();
    }

  }
}
