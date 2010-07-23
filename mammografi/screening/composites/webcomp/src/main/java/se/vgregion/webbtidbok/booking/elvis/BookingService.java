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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import se.vgregion.webbtidbok.Places;
import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.gui.SelectItemConverter;
import se.vgregion.webbtidbok.lang.DateHandler;
import se.vgregion.webbtidbok.ws.ArrayOfBookingPlace;
import se.vgregion.webbtidbok.ws.ArrayOfBookingTime;
import se.vgregion.webbtidbok.ws.BookingPlace;
import se.vgregion.webbtidbok.ws.BookingRequest;
import se.vgregion.webbtidbok.ws.BookingResponse;
import se.vgregion.webbtidbok.ws.BookingTime;
import se.vgregion.webbtidbok.ws.ObjectFactory;

public class BookingService implements BookingServiceInterface {
  private static final Logger sLogger = Logger.getLogger("se.vgregion.webbtidbok.logging");

  int testIndex;
  BookingResponse response;
  BookingRequest request;
  WebServiceHelper helper;

  public void setHelper(WebServiceHelper helper) {
    this.helper = helper;
  }

  private final ObjectFactory objectFactory = new ObjectFactory();

  public boolean isFirstPlaces = true;
  private boolean isTimeListEmpty = true;
  public boolean isUpdated = false;

  // private String orderDate;

  public boolean getIsTimeListEmpty() {
    return isTimeListEmpty;
  }

  public void setTimeListEmpty(boolean isTimeListEmpty) {
    this.isTimeListEmpty = isTimeListEmpty;
  }

  // constructor
  public BookingService() {
  }

  public void setIsUpdated(boolean b) {
    isUpdated = true;
  }

  public boolean isUpdated() {
    return isUpdated;
  }

  public void setFirstPlacesBoolean(boolean b) {

    isFirstPlaces = b;
    // System.out.println("BookingServices.setFirstPlacesBoolean: " + isFirstPlaces);
  }

  public boolean isFirstPlaces() {
    // System.out.println("BookingServices.isFirstBoolean: " + isFirstPlaces);

    return isFirstPlaces;
  }

  public BookingResponseLocal getBooking(State loginCredentials) {

    if (loginCredentials.isLoggedIn()) {

      request = helper.getQueryWSRequest(loginCredentials);
      response = helper.getQueryWS(request);
      BookingResponseLocal responseLocal = new BookingResponseLocal(response);
      loginCredentials.setCentralTidbokID(response.getCentralTidbokID());
      // set the Place for States used in getCalendar method
      loginCredentials.setCentralTidbokID(responseLocal.getCentralTimeBookId());

      return responseLocal;
    }

    return null;
  }

  /**
   * Method cancelBooking returns true if deleted
   * 
   * @param loginCredentials
   * @return
   */
  public boolean cancelBooking(State loginCredentials) {

    request = helper.getQueryWSRequest(loginCredentials);
    boolean cancelledBooking = helper.getQueryWSCancelBooking(request);

    return cancelledBooking;
    // return true;
  }

  public List<BookingPlaceLocal> getBookingPlace(State loginCredentials) {

    // Uncomment below for debug, you'll only have to click login,
    // creds below are hard coded.
    // String pnr = "19960103-2395";
    // String psw = "Y8PBZRUr";
    // loginCredentials.setPnr(pnr);
    // loginCredentials.setPasswd(psw);
    // loginCredentials.setLoggedIn(true);
    List<BookingPlaceLocal> placeListLocal = new ArrayList<BookingPlaceLocal>();

    if (loginCredentials.isLoggedIn()) {

      request = helper.getQueryWSRequest(loginCredentials);
      ArrayOfBookingPlace places = helper.getQueryWSRequestPlaces(request);
      // response = helper.getQueryWS(request);g
      List<BookingPlace> placeList = places.getBookingPlace();
      for (BookingPlace p : placeList) {
        BookingPlaceLocal pl = new BookingPlaceLocal();
        pl.setCentralTimeBookId(p.getCentralTidbokID());
        pl.setAddress(p.getAddress().getValue());
        pl.setClinic(p.getMottagning().getValue());

        placeListLocal.add(pl);

        // System.out.println(pl.toString());

      }

      // System.out.println("size: " + placeListLocal.size());

      return placeListLocal;
    }

    return placeListLocal;
  }

  public List<BookingTimeLocal> getBookingTime(State loginCredentials) {

    // Uncomment below for debug, you'll only have to click login,
    // creds below are hard coded.
    // String pnr = "19960103-2395";
    // String psw = "Y8PBZRUr";
    // loginCredentials.setPnr(pnr);
    // loginCredentials.setPasswd(psw);
    // loginCredentials.setLoggedIn(true);
    List<BookingTimeLocal> timeListLocal = new ArrayList<BookingTimeLocal>();
    boolean theInThePastFlag = loginCredentials.getSelectedDate().before(Calendar.getInstance());

    if (loginCredentials.isLoggedIn() && !theInThePastFlag) {
      Calendar selectedDate = loginCredentials.getSelectedDate();

      // if(selectedDate == null){
      // loginCredentials.setDefaultDate(true);
      // return null;
      // }

      // System.out.println("SELECTED DATE YEAR: " + selectedDate.get(Calendar.YEAR));
      // System.out.println("SELECTED DATE MONTH: " + selectedDate.get(Calendar.MONTH));
      // System.out.println("SELECTED DATE DAY: " + selectedDate.get(Calendar.DAY_OF_MONTH));
      String fromDate = DateHandler.setCalendarDateFormat(selectedDate);
      JAXBElement<String> fromDat = objectFactory.createBookingRequestFromDat(fromDate);
      //      
      System.out.println("fromDate: " + fromDate);
      System.out.println("JAXB-FROMDATE: " + fromDat.getValue());
      //      
      // System.out.println("CentralTidBokID: " + loginCredentials.getCentralTidbokID());

      request = helper.getQueryWSRequest(loginCredentials);
      request.setCentralTidbokID(loginCredentials.getCentralTidbokID());
      request.setFromDat(fromDat);

      ArrayOfBookingTime times = helper.getQueryWSRequestTime(request);

      List<BookingTime> timeList;
      try {
        timeList = times.getBookingTime();
        if (timeList.isEmpty()) {
          isTimeListEmpty = true;

          System.out.println("iiiiiiif    timeList is empty + testIndex " + testIndex++);
        } else {
          isTimeListEmpty = false;
          System.out.println("tryyyyy   isTimeListEmpty(false): " + isTimeListEmpty + ", testIndex " + testIndex++);
        }

        int id = 1;
        for (BookingTime b : timeList) {

          Calendar dateCal = Calendar.getInstance();
          dateCal.set(Calendar.YEAR, b.getDatum().getYear());
          dateCal.set(Calendar.MONTH, b.getDatum().getMonth() - 1);
          dateCal.set(Calendar.DATE, b.getDatum().getDay());

          String day = DateHandler.setCalendarDateFormat(dateCal);

          // String hourTime = DateHandler.setCalendarTimeFormat(dateCal);

          BookingTimeLocal pl = new BookingTimeLocal();
          pl.setNumbers(b.getAntal());
          pl.setDay(day);
          pl.setTime(b.getKlocka().getValue());
          pl.setBookingTimeId(id);
          timeListLocal.add(pl);

          System.out.println(pl.toString());

          id++;
        }
      } catch (Exception e) {
        isTimeListEmpty = true;
      }

      return timeListLocal;
    }

    return timeListLocal;
  }

  /***
   * method set selected item
   * 
   * @param selectedItem
   */

  public void setSelectedItem(int selectedItem) {
    System.out.println("selectitem: " + selectedItem);
  }

  /***
   * 
   * @param places
   */
  public void setSelectedItem(Places places) {
    System.out.println("selectitem: " + places.getPlacesId());
  }

  /***
   * 
   * 
   * @param places
   * @param state
   */
  public void setSelectedItem(Places places, State state) {
    System.out.println("BookingServices.setSelectedItem: " + places.getPlacesId());
    state.setCentralTidbokID(places.getPlacesId());
    System.out.println("BookingServices.state.centraltidbokid: " + state.getCentralTidbokID());
  }

  /*
   * Method setting default value for BookingResponse, CentralTimeBookingId
   */

  public int getSelectedDefaultItem(State loginCredentials) {

    if (this.isFirstPlaces()) {

      request = helper.getQueryWSRequest(loginCredentials);
      response = helper.getQueryWS(request);
      BookingResponseLocal responseLocal = new BookingResponseLocal(response);
      int centralTimeBookingId = responseLocal.getCentralTimeBookId();

      this.setFirstPlacesBoolean(false);
      return centralTimeBookingId;

    } else {
      System.out.println("BookingServices.getSelectedDefaultItem: " + loginCredentials.getCentralTidbokID());

      return loginCredentials.getCentralTidbokID();
    }

  }

  /**
   * Method finding a selected place in the list of chosen bookingplaces return the object to print the selected place
   * 
   * 
   * @param places
   * @param login
   * @return
   */

  public Places getSelectedPlace(Places places, State login) {
    Places place = new Places();
    List<BookingPlaceLocal> bookingPlaces = getBookingPlace(login);
    for (BookingPlaceLocal bl : bookingPlaces) {
      if (bl.getCentralTimeBookId() == places.getPlacesId()) {

        place.setPlacesId(bl.getCentralTimeBookId());
        place.setClinic(bl.getClinic());
        place.setAddress(bl.getAddress());
        place.setRepresentationPlace();

      }
    }

    if (place != null) {
      sLogger.debug("Selected place clinic: " + place.getClinic());
      sLogger.debug("Selected place address: " + place.getAddress());
      sLogger.debug("Selected place id: " + place.getPlacesId());
      sLogger.debug("Selected place representation place: " + place.getRepresentationPlace());
    }
    return place;
  }

  public List<SelectItem> getBookingPlaceSelectItems(State loginCredentials) {

    // Uncomment below for debug, you'll only have to click login,
    // creds below are hard coded.
    // String pnr = "19960103-2395";
    // String psw = "Y8PBZRUr";
    // loginCredentials.setPnr(pnr);
    // loginCredentials.setPasswd(psw);
    // loginCredentials.setLoggedIn(true);
    List<BookingPlaceLocal> placeListLocal = new ArrayList<BookingPlaceLocal>();

    if (loginCredentials.isLoggedIn()) {

      request = helper.getQueryWSRequest(loginCredentials);
      ArrayOfBookingPlace places = helper.getQueryWSRequestPlaces(request);
      // response = helper.getQueryWS(request);
      List<BookingPlace> placeList = places.getBookingPlace();
      for (BookingPlace p : placeList) {
        BookingPlaceLocal pl = new BookingPlaceLocal();
        pl.setCentralTimeBookId(p.getCentralTidbokID());
        pl.setAddress(p.getAddress().getValue());
        pl.setClinic(p.getMottagning().getValue());

        placeListLocal.add(pl);

        System.out.println(pl.toString());

      }

      System.out.println("size: " + placeListLocal.size());
      SelectItemConverter sc = new SelectItemConverter();

      return sc.getSelectItems(placeListLocal);
    }

    return null;
  }

  /****
   * method setting ombokning
   * 
   * @param l
   */
  public void setBookingTime(BookingTimeLocal bookingTime, State credentials) {
    String hour = bookingTime.getTime();
    String[] hourMinute = hour.split(":");

    GregorianCalendar cal = new GregorianCalendar();
    cal.setTimeInMillis(credentials.getSelectedDate().getTime().getTime());

    cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourMinute[0]));
    cal.set(Calendar.MINUTE, Integer.parseInt(hourMinute[1]));
    cal.set(Calendar.SECOND, 0);

    // update booking
    if (credentials.isLoggedIn()) {

      request = helper.getQueryWSRequest(credentials);

      XMLGregorianCalendar xmlCal;
      try {

        xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        request.setBokadTid(xmlCal);
      } catch (Exception e) {
        e.printStackTrace();
      }

      request.setCentralTidbokID(credentials.getCentralTidbokID());

      BookingResponse response = helper.setBookingUpdate(request);

      if (response == null) {
        this.setIsUpdated(false);
      } else {
        this.setIsUpdated(true);
      }

    }

  }

}
