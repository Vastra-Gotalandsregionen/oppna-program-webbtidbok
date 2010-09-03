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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfSection;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfTimeBlock;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfdateTime;
import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.IRisReschedule;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleRescheduleErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.Section;
import se.vgregion.webbtidbok.ws.sectra.TimeBlock;

public class SectraBookingServiceImpl implements SectraBookingServiceInterface {

  private final Log log = LogFactory.getLog(SectraBookingServiceImpl.class);
  private IRisReschedule thePort;
  private BookingMapperSectra bookingMapperSectra;

  private String patientNr;
  private String examinationNr;

  public void setPatientNr(String patientNr) {
    this.patientNr = patientNr;
  }

  public void setExaminationNr(String examinationNr) {
    this.examinationNr = examinationNr;
  }

  public void setThePort(IRisReschedule thePort) {
    this.thePort = thePort;
  }

  public void setBookingMapperSectra(BookingMapperSectra bookingMapperSectra) {
    this.bookingMapperSectra = bookingMapperSectra;
  }

  @Override
  public Booking getBooking() {
    Booking booking;

    try {
      BookingInfo bi = thePort.getBookingInfo(patientNr, examinationNr);
      booking = bookingMapperSectra.bookingMapping(bi);
    } catch (IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage e) {
      log.error("Error response from web service when getting booking.", e);
      throw new RuntimeException("Error response from web service when getting booking.", e);
    }
    return booking;
  }

  @Override
  public List<Surgery> getSurgeries() {
    List<Surgery> surgeries = new ArrayList<Surgery>();
    try {
      ArrayOfSection sections = thePort.listSections(examinationNr);
      List<Section> sectionList = sections.getSection();
      for (Section section : sectionList) {
        Surgery surgery = bookingMapperSectra.surgeryMapping(section);
        if (!"".equals(surgery.getSurgeryName())) {
            surgeries.add(surgery);
        }
      }
    } catch (IRisRescheduleListSectionsErrorInfoFaultFaultMessage e) {
      log.error("Error response from web service when getting surgeries.", e);
      throw new RuntimeException("Error response from web service when getting surgeries.", e);
    }
    return surgeries;
  }

  @Override
  public List<Calendar> getFreeDays(Calendar startDate, Calendar endDate, String sectionId) {
    List<Calendar> dates = new ArrayList<Calendar>();

    ArrayOfdateTime times;
    try {
      times = thePort.listFreeDays(bookingMapperSectra.dateToXmlCalendar(startDate),
              bookingMapperSectra.dateToXmlCalendar(endDate), examinationNr, sectionId);
      List<XMLGregorianCalendar> timesList = times.getDateTime();
      for (XMLGregorianCalendar time : timesList) {
        Calendar date = bookingMapperSectra.daysMapping(time);
        dates.add(date);
      }
      return dates;
    } catch (IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage e) {
      log.error("Error response from web service when getting free days.", e);
      throw new RuntimeException("Error response from web service when getting free days.", e);
    }
  }

  @Override
  public List<BookingTime> getFreeTimes(Calendar startDate, Calendar endDate, String sectionId) {
    List<BookingTime> bookingTimes = new ArrayList<BookingTime>();

    try {
      ArrayOfTimeBlock times = thePort.listFreeTimes(bookingMapperSectra.dateToXmlCalendar(startDate),
              bookingMapperSectra.dateToXmlCalendar(endDate), examinationNr, sectionId);
      List<TimeBlock> timesList = times.getTimeBlock();
      for (TimeBlock time : timesList) {
        BookingTime bookingTime = bookingMapperSectra.bookingTimeMapping(time);
        bookingTimes.add(bookingTime);
      }
      return bookingTimes;
    } catch (IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage e) {
      log.error("Error response from web service when getting free times.", e);
      throw new RuntimeException("Error response from web service when getting free times.", e);
    }
  }

  @Override
  public Booking reschedule(String examinationNr, String newTimeId,
          XMLGregorianCalendar startTime, Boolean printNewNotice,
          String rescheduleComment) {
    Booking booking;

    try {
      BookingInfo bi = thePort.reschedule(examinationNr, newTimeId, startTime,
              printNewNotice, rescheduleComment);
      booking = bookingMapperSectra.bookingMapping(bi);
      return booking;
    } catch (IRisRescheduleRescheduleErrorInfoFaultFaultMessage e) {
      log.error("Error response from web service when rescheduling.", e);
      throw new RuntimeException("Error response from web service when rescheduling.", e);
    }
  }

}
