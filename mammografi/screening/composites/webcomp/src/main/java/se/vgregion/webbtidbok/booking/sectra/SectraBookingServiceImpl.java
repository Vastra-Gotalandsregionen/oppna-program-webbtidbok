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
package se.vgregion.webbtidbok.booking.sectra;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import se.vgregion.webbtidbok.Places;
import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.elvis.BookingServiceInterface;
import se.vgregion.webbtidbok.booking.elvis.BookingTimeLocal;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingPlace;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfSection;
import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.IRisReschedule;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.Section;
import se.vgregion.webbtidbok.ws.sectra.TimeBlock;

public class SectraBookingServiceImpl implements BookingServiceInterface {

  private SectraWebServiceHelperInterface webServiceHelper;
  private IRisReschedule thePortSU;
  private IRisReschedule thePortNU;
  private BookingMapperSectra bookingMapperSectra;

  public void setThePortSU(IRisReschedule thePortSU) {
    this.thePortSU = thePortSU;
  }

  public void setThePortNU(IRisReschedule thePortNU) {
    this.thePortNU = thePortNU;
  }

  public void setBookingMapperSectra(BookingMapperSectra bookingMapperSectra) {
    this.bookingMapperSectra = bookingMapperSectra;
  }

  public void setHelper(SectraWebServiceHelperInterface helper) {
    this.webServiceHelper = helper;
  }

  @Override
  public Booking getBooking(State state) {

    Booking booking;
    try {
      BookingInfo bi = thePortSU.getBookingInfo(state.getPnr(), state.getPasswd());
      booking = bookingMapperSectra.bookingMapping(bi);
    } catch (IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage e) {
      e.printStackTrace();
      booking = new BookingSectra();
    }
    return booking;

    //    
    // String patientId = state.getPnr();
    // String examinationNo = state.getPasswd();
    // Booking biL = null;
    //
    // try {
    // biL = webServiceHelper.getBookingInfo(patientId, examinationNo);
    // } catch (IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage e) {
    // e.printStackTrace();
    // }
    // return biL;
  }

  // public boolean login(String patientId, String password) {
  //
  //    
  // //boolean isLoggedIn = helper.login(patientId, password);
  // return true;//isLoggedIn;
  // }

  public ArrayOfSectionLocal listSections(String examinationNr) {

    ArrayOfSectionLocal sectionArrayL = null;
    try {

      sectionArrayL = webServiceHelper.listSections(examinationNr);

    } catch (IRisRescheduleListSectionsErrorInfoFaultFaultMessage e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return sectionArrayL;
  }

  @Override
  public boolean cancelBooking(State loginCredentials) {
    // TODO Auto-generated method stub
    return false;
  }


  public List<Surgery> getBookingPlace(State loginCredentials) {
    // TODO Auto-generated method stub
    return null;
  }

  // @Override
  // public List<SelectItem> getBookingPlaceSelectItems(State loginCredentials) {
  // List<se.vgregion.webbtidbok.domain.BookingPlace> placeListLocal = new ArrayList<se.vgregion.webbtidbok.domain.BookingPlace>();
  // helper.
  // return null;
  // }

  @Override
  public List<BookingTimeLocal> getBookingTime(State loginCredentials) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean getIsTimeListEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int getSelectedDefaultItem(State state) {
    ArrayOfSectionLocal listSections = this.listSections(state.getPasswd());
    return listSections.getSectionLocalList().size();
  }

  @Override
  public Places getSelectedPlace(Places places, State login) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isFirstPlaces() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isUpdated() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setBookingTime(BookingTimeLocal bookingTime, State credentials) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setFirstPlacesBoolean(boolean b) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setIsUpdated(boolean b) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setSelectedItem(int selectedItem) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setSelectedItem(Places places) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setSelectedItem(Places places, State state) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setTimeListEmpty(boolean isTimeListEmpty) {
    // TODO Auto-generated method stub

  }

  @Override
  public List<Surgery> getSurgeries(State state) {
    List<Surgery> surgeries = new ArrayList<Surgery>();
    try {
      ArrayOfSection sections = thePortSU.listSections(state.getPasswd());
      List<Section> sectionList = sections.getSection();
      for (Section section : sectionList) {
        Surgery surgery = bookingMapperSectra.surgeryMapping(section);
        surgeries.add(surgery);
      }
    } catch (IRisRescheduleListSectionsErrorInfoFaultFaultMessage e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return surgeries;
  }

  // @Override
  // public Booking reschedule(String examinationNr, String newTimeId,
  // XMLGregorianCalendar startTime, Boolean printNewNotice,
  // String rescheduleComment) {
  // Booking biL = null;
  // try {
  // biL = helper.reschedule(examinationNr, newTimeId, startTime, printNewNotice, rescheduleComment);
  // } catch (IRisRescheduleRescheduleErrorInfoFaultFaultMessage e) {
  // e.printStackTrace();
  // }
  //		
  // return biL;
  // }

}
