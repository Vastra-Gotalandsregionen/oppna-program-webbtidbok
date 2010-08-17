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

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static se.vgregion.webbtidbok.lang.DateHandler.calendarFromDate;
import static se.vgregion.webbtidbok.lang.DateHandler.dateFor;
import static se.vgregion.webbtidbok.lang.DateHandler.xmlCalendarFor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.opensymphony.oscache.base.NeedsRefreshException;

import se.vgregion.webbtidbok.LogFactoryMock;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfSection;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfdateTime;
import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;

public class SectraBookingServiceImplTest {

  private SectraBookingServiceImpl service;

  private static final String examNr = "TEST";
  private static final String patientNr = "19121212-1212";
  private static final String sectionId = "TESTSECTION";

  private TestMock testMock;

  private static LogFactoryMock logFactory;

  @BeforeClass
  public static void before() {
    logFactory = LogFactoryMock.createInstance();
  }

  @AfterClass
  public static void after() {
    LogFactoryMock.resetInstance();
  }

  @Before
  public void setUp() throws Exception {
    service = new SectraBookingServiceImpl();
    testMock = new TestMock();
    service.setThePort(testMock);
    service.setBookingMapperSectra(new BookingMapperSectra());

    service.setExaminationNr(examNr);
    service.setPatientNr(patientNr);
  }

  @Test
  public void testGetBooking() {
    Booking booking = service.getBooking();
    assertNotNull(booking);
    assertEquals(patientNr, testMock.patientId);
    assertEquals(examNr, testMock.examinationNr);
  }

  @Test
  public void testGetBookingException() {
    testMock.throwException = true;
    try {
      service.getBooking();
      fail("Should throw exception");
    } catch (RuntimeException e) {
      assertEquals("Error response from web service when getting booking.", logFactory.getError(true).replace("\n", ""));
    }
  }

  @Test
  public void testGetSurgeries() {
    List<Surgery> surgeries = service.getSurgeries();
    assertNotNull(surgeries);
    assertEquals(examNr, testMock.examinationNr);
  }
  
  @Test
  public void testGetSurgeriesException(){
    testMock.throwException = true;
    try {
      service.getSurgeries();
      fail("Should throw exception");
    } catch (Exception e) {
      assertEquals("Error response from web service when getting surgeries.", logFactory.getError(true).replace("\n", ""));
    }
    
  }

  @Test
  public void testGetFreeDays() {
    List<Date> freeDays = service.getFreeDays(dateFor(2010, 8, 1), dateFor(2010, 8, 31), sectionId);

    Calendar cal = calendarFromDate(freeDays.get(0));
    assertEquals(2010, cal.get(Calendar.YEAR));
    assertEquals(7, cal.get(Calendar.MONTH));
    assertEquals(2, cal.get(Calendar.DAY_OF_MONTH));

    cal = calendarFromDate(freeDays.get(1));
    assertEquals(2010, cal.get(Calendar.YEAR));
    assertEquals(7, cal.get(Calendar.MONTH));
    assertEquals(4, cal.get(Calendar.DAY_OF_MONTH));

    cal = calendarFromDate(freeDays.get(2));
    assertEquals(2010, cal.get(Calendar.YEAR));
    assertEquals(7, cal.get(Calendar.MONTH));
    assertEquals(8, cal.get(Calendar.DAY_OF_MONTH));

    cal = calendarFromDate(freeDays.get(3));
    assertEquals(2010, cal.get(Calendar.YEAR));
    assertEquals(7, cal.get(Calendar.MONTH));
    assertEquals(16, cal.get(Calendar.DAY_OF_MONTH));
  }

  class TestMock extends SectraEmptyWSMock {

    boolean throwException;
    String patientId;
    String examinationNr;

    @Override
    public BookingInfo getBookingInfo(String patientId, String examinationNr) throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
      if (throwException) {
        throw new IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage("test exception", null);
      }
      this.patientId = patientId;
      this.examinationNr = examinationNr;
      return new BookingInfo();
    }

    @Override
    public ArrayOfSection listSections(String examinationNr) throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {
      if(throwException){
        throw new IRisRescheduleListSectionsErrorInfoFaultFaultMessage("test exception", null);
      }
      this.examinationNr = examinationNr;
      return new ArrayOfSection();
    }

    @Override
    public ArrayOfdateTime listFreeDays(XMLGregorianCalendar startDate, XMLGregorianCalendar endDate, String examinationNr, String sectionId)
        throws IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage {
      List<XMLGregorianCalendar> resList = new ArrayList<XMLGregorianCalendar>();
      resList.add(xmlCalendarFor(2010, 8, 2));
      resList.add(xmlCalendarFor(2010, 8, 4));
      resList.add(xmlCalendarFor(2010, 8, 8));
      resList.add(xmlCalendarFor(2010, 8, 16));

      return new ArrayOfdateTimeMock(resList);
    }

  }

}
