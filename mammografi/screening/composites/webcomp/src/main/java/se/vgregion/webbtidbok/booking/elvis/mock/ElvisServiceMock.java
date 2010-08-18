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
package se.vgregion.webbtidbok.booking.elvis.mock;

import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.ArrayOfCalendar;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.BookingTime;
import se.vgregion.webbtidbok.ws.ICentralBookingWS;
import se.vgregion.webbtidbok.ws.ICentralBookingWSCancelBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSConfirmBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ICentralBookingWSGetCalandarICFaultFaultFaultMessage;
import se.vgregion.webbtidbok.ws.ObjectFactory;

public class ElvisServiceMock implements ICentralBookingWS {

  private final Log log = LogFactory.getLog(ElvisServiceMock.class);
  private Map<String, Object> elvisMockData;
  private Resource elvisMockDataResourse;
  private ObjectFactory objectFactory = new ObjectFactory();
  private int centralTidbokID;

  public void setElvisMockDataResourse(Resource elvisMockDataResourse) {
    this.elvisMockDataResourse = elvisMockDataResourse;
  }

  public static void main(String[] args) throws DatatypeConfigurationException {
    // Create Bookings objects
    ElvisServiceMock elvisServiceMock = new ElvisServiceMock();
    elvisServiceMock.createMockData();
  }

  public void createMockData() throws DatatypeConfigurationException {
    elvisMockData = new HashMap<String, Object>();
    // Creagte bookings.
    Map<String, BookingResponse> bookings = new HashMap<String, BookingResponse>();
    XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    String pNR1 = "20101012-2222";
    String pNR2 = "20100312-2222";
    bookings.put(pNR1, createBookings(pNR1, "KALLE 1", "ADRESS 1", "MOTAGNING 1", xmlGregorianCalendar));
    bookings.put(pNR2, createBookings(pNR2, "KALLE 2", "ADRESS 2", "MOTAGNING 2", xmlGregorianCalendar));
    elvisMockData.put("bookings", bookings);

    // Create bookingPlaces.
    Map<String, ArrayOfBookingPlace> bookingPlaces = new HashMap<String, ArrayOfBookingPlace>();
    ArrayOfBookingPlace arrayOfBookingPlace = new ArrayOfBookingPlace();
    arrayOfBookingPlace.getBookingPlace().add(createBookingPlaces("Address 1", "Mottagning 1"));
    arrayOfBookingPlace.getBookingPlace().add(createBookingPlaces("Address 2", "Mottagning 2"));
    bookingPlaces.put(pNR1, arrayOfBookingPlace);
    bookingPlaces.put(pNR2, arrayOfBookingPlace);
    elvisMockData.put("bookingPlaces", bookingPlaces);

    // Create bookingTimes
    Map<String, ArrayOfBookingTime> bookingTimes = new HashMap<String, ArrayOfBookingTime>();
    ArrayOfBookingTime arrayOfBookingTime = new ArrayOfBookingTime();
    arrayOfBookingTime.getBookingTime().add(createBookingTime(xmlGregorianCalendar, "07:00"));
    arrayOfBookingTime.getBookingTime().add(createBookingTime(xmlGregorianCalendar, "12:00"));
    arrayOfBookingTime.getBookingTime().add(createBookingTime(xmlGregorianCalendar, "16:30"));
    arrayOfBookingTime.getBookingTime().add(createBookingTime(xmlGregorianCalendar, "19:00"));
    bookingTimes.put(pNR1, arrayOfBookingTime);
    bookingTimes.put(pNR2, arrayOfBookingTime);
    elvisMockData.put("bookingTimes", bookingTimes);
    // createXmlFromObjet("src/main/resources/elvisMockData.xml", elvisMockData);

  }

  private BookingResponse createBookings(String PNR, String NAME, String ADDRESS, String MOTTAGNING, XMLGregorianCalendar xmlGregorianCalendar) {
    BookingResponse bookingResponse = new BookingResponse();
    bookingResponse.setPnr(objectFactory.createString(PNR));
    bookingResponse.setNamn(objectFactory.createString(NAME));
    bookingResponse.setAddress(objectFactory.createString(ADDRESS));
    bookingResponse.setMottagning(objectFactory.createString(MOTTAGNING));
    bookingResponse.setAntalOmbok(0);
    bookingResponse.setMaxAntalOmbok(4);
    bookingResponse.setCentralTidbokID(++centralTidbokID);
    bookingResponse.setBokadTid(xmlGregorianCalendar);
    return bookingResponse;
  }

  private BookingTime createBookingTime(XMLGregorianCalendar date, String time) {
    BookingTime bookingTime = new BookingTime();
    bookingTime.setAntal(2);
    bookingTime.setDatum(date);
    bookingTime.setKlocka(objectFactory.createString(time));
    return bookingTime;
  }

  private BookingPlace createBookingPlaces(String address, String mottagning) {
    BookingPlace bookingPlace = new BookingPlace();
    bookingPlace.setAddress(objectFactory.createString(address));
    bookingPlace.setCentralTidbokID(++centralTidbokID);
    bookingPlace.setMottagning(objectFactory.createString(mottagning));
    return bookingPlace;
  }

  private BookingResponse getBookingResponseForBooking(String pnr) {
    Map<String, BookingResponse> bookings = (Map<String, BookingResponse>) elvisMockData.get("bookings");
    return bookings.get(pnr);
  }

  private ArrayOfBookingPlace getBookingPlaces(String pnr) {
    Map<String, ArrayOfBookingPlace> bookingPlaces = (Map<String, ArrayOfBookingPlace>) elvisMockData.get("bookingPlaces");
    return bookingPlaces.get(pnr);
  }

  private ArrayOfBookingTime getBookingTimes(String pnr) {
    Map<String, ArrayOfBookingTime> bookingTimes = (Map<String, ArrayOfBookingTime>) elvisMockData.get("bookingTimes");
    return bookingTimes.get(pnr);
  }

  @Override
  public Boolean cancelBooking(BookingRequest request) throws ICentralBookingWSCancelBookingICFaultFaultFaultMessage {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BookingResponse confirmBooking(BookingRequest request) throws ICentralBookingWSConfirmBookingICFaultFaultFaultMessage {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BookingResponse getBooking(BookingRequest request) throws ICentralBookingWSGetBookingICFaultFaultFaultMessage {
    return getBookingResponseForBooking(request.getPnr().getValue());
  }

  @Override
  public ArrayOfBookingPlace getBookingPlace(BookingRequest request) throws ICentralBookingWSGetBookingPlaceICFaultFaultFaultMessage {
    return getBookingPlaces(request.getPnr().getValue());
  }

  @Override
  public ArrayOfBookingTime getBookingTime(BookingRequest request) throws ICentralBookingWSGetBookingTimeICFaultFaultFaultMessage {
    return getBookingTimes(request.getPnr().getValue());
  }

  @Override
  public ArrayOfCalendar getCalandar(BookingRequest request) throws ICentralBookingWSGetCalandarICFaultFaultFaultMessage {
    // TODO Auto-generated method stub
    return null;
  }

}
