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

package se.vgregion.webbtidbok.booking.sectra;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static se.vgregion.webbtidbok.lang.DateHandler.calendarFor;
import static se.vgregion.webbtidbok.lang.DateHandler.xmlCalendarFor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jws.WebParam;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.vgregion.webbtidbok.LogFactoryMock;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfSection;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfTimeBlock;
import se.vgregion.webbtidbok.ws.sectra.ArrayOfdateTime;
import se.vgregion.webbtidbok.ws.sectra.BookingInfo;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.IRisRescheduleListSectionsErrorInfoFaultFaultMessage;
import se.vgregion.webbtidbok.ws.sectra.TimeBlock;

public class SectraBookingServiceImplTest {

	private SectraBookingServiceImpl service;

	private static final String examNr = "TEST";
	private static final String patientNr = "191212121212";
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
	public void testGetSurgeriesException() {
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
		List<Calendar> freeDays = service.getFreeDays(calendarFor(2010, 8, 1), calendarFor(2010, 8, 31), sectionId);

		Calendar cal = freeDays.get(0);
		assertEquals(2010, cal.get(Calendar.YEAR));
		assertEquals(7, cal.get(Calendar.MONTH));
		assertEquals(2, cal.get(Calendar.DAY_OF_MONTH));

		cal = freeDays.get(1);
		assertEquals(2010, cal.get(Calendar.YEAR));
		assertEquals(7, cal.get(Calendar.MONTH));
		assertEquals(4, cal.get(Calendar.DAY_OF_MONTH));

		cal = freeDays.get(2);
		assertEquals(2010, cal.get(Calendar.YEAR));
		assertEquals(7, cal.get(Calendar.MONTH));
		assertEquals(8, cal.get(Calendar.DAY_OF_MONTH));

		cal = freeDays.get(3);
		assertEquals(2010, cal.get(Calendar.YEAR));
		assertEquals(7, cal.get(Calendar.MONTH));
		assertEquals(16, cal.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testGetFreeTimes() {
		List<BookingTime> times = service.getFreeTimes(calendarFor(2010, 8, 1), calendarFor(2010, 8, 31), sectionId);
		assertNotNull(times);
		assertEquals(examNr, testMock.examinationNr);
		assertEquals(sectionId, testMock.sectionId);

	}

	class TestMock extends SectraEmptyWSMock {

		boolean throwException;
		String patientId;
		String examinationNr;
		String sectionId;

		@Override
		public BookingInfo getBookingInfo(@WebParam(name = "patientId", targetNamespace = "http://tempuri.org/") String patientId, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage {
			if (throwException) {
				throw new IRisRescheduleGetBookingInfoErrorInfoFaultFaultMessage("test exception", null);
			}
			this.patientId = patientId;
			this.examinationNr = examinationNr;
			return new BookingInfo();
		}

		@Override
		public ArrayOfSection listSections(@WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleListSectionsErrorInfoFaultFaultMessage {
			if (throwException) {
				throw new IRisRescheduleListSectionsErrorInfoFaultFaultMessage("test exception", null);
			}
			this.examinationNr = examinationNr;
			return new ArrayOfSection();
		}

		@Override
		public ArrayOfdateTime listFreeDays(@WebParam(name = "startDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startDate, @WebParam(name = "endDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar endDate, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "sectionId", targetNamespace = "http://tempuri.org/") String sectionId, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleListFreeDaysErrorInfoFaultFaultMessage {
			List<XMLGregorianCalendar> resList = new ArrayList<XMLGregorianCalendar>();
			resList.add(xmlCalendarFor(2010, 8, 2));
			resList.add(xmlCalendarFor(2010, 8, 4));
			resList.add(xmlCalendarFor(2010, 8, 8));
			resList.add(xmlCalendarFor(2010, 8, 16));

			return new ArrayOfdateTimeMock(resList);
		}

		@Override
		public ArrayOfTimeBlock listFreeTimes(@WebParam(name = "startDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar startDate, @WebParam(name = "endDate", targetNamespace = "http://tempuri.org/") XMLGregorianCalendar endDate, @WebParam(name = "examinationNr", targetNamespace = "http://tempuri.org/") String examinationNr, @WebParam(name = "sectionId", targetNamespace = "http://tempuri.org/") String sectionId, @WebParam(name = "allowMultiExamination", targetNamespace = "http://tempuri.org/") Boolean allowMultiExamination, @WebParam(name = "allowUrgent", targetNamespace = "http://tempuri.org/") Boolean allowUrgent) throws IRisRescheduleListFreeTimesErrorInfoFaultFaultMessage {
			List<TimeBlock> resList = new ArrayList<TimeBlock>();
			this.examinationNr = examinationNr;
			this.sectionId = sectionId;

			return new ArrayOfTimeBlockMock(resList);
		}

	}

}
